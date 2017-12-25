package me.rayzr522.survivalcore.modules.dm;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import me.rayzr522.survivalcore.api.managers.IManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DMManager implements IManager {
    private CommandDM commandDM;
    private CommandReply commandReply;

    private Cache<UUID, UUID> conversations = CacheBuilder.newBuilder()
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
    public List<ManagerCommand> getCommands() {
        return Arrays.asList(commandDM, commandReply);
    }

    void sendDM(Player from, Player to, String message) {
        SurvivalCore plugin = SurvivalCore.getInstance();
        String self = plugin.trRaw("command.dm.self");

        System.out.println(from.getDisplayName());
        System.out.println(to.getDisplayName());
        System.out.println(message);
        System.out.println(self);

        from.sendMessage(plugin.trRaw("command.dm.message-format", self, to.getDisplayName(), message));
        to.sendMessage(plugin.trRaw("command.dm.message-format", from.getDisplayName(), self, message));

        conversations.put(from.getUniqueId(), to.getUniqueId());
    }

    Optional<Player> getLastTarget(Player player) {
        UUID last = conversations.getIfPresent(player.getUniqueId());
        return last == null ? Optional.empty() : Optional.ofNullable(Bukkit.getPlayer(last));
    }
}
