package me.rayzr522.survivalcore.modules.dm;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.ModuleCommand;
import me.rayzr522.survivalcore.api.modules.AbstractModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DMModule extends AbstractModule {
    private CommandDM commandDM;
    private CommandReply commandReply;

    private final Cache<UUID, UUID> conversations = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    @Override
    public void onLoad(SurvivalCore core) {
        commandDM = new CommandDM(this);
        commandReply = new CommandReply(this);
    }

    @Override
    public String getName() {
        return "DM";
    }

    @Override
    public List<ModuleCommand> getCommands() {
        return Arrays.asList(commandDM, commandReply);
    }

    void sendDM(Player from, Player to, String message) {
        SurvivalCore plugin = SurvivalCore.getInstance();
        String self = plugin.trRaw("command.dm.self");

        from.sendMessage(plugin.trRaw("command.dm.message-format", self, to.getDisplayName(), message));
        to.sendMessage(plugin.trRaw("command.dm.message-format", from.getDisplayName(), self, message));

        conversations.put(from.getUniqueId(), to.getUniqueId());
        // If the receiver isn't in a conversation already, you might as well make it easy for them to reply.
        // This is so that, if they're already in a conversation, they won't suddenly have their /reply going to another user, but if they aren't already in a conversation then they can easily reply to the new one that was just started.
        conversations.asMap().putIfAbsent(to.getUniqueId(), from.getUniqueId());
    }

    Optional<Player> getLastTarget(Player player) {
        UUID last = conversations.getIfPresent(player.getUniqueId());
        return last == null ? Optional.empty() : Optional.ofNullable(Bukkit.getPlayer(last));
    }
}
