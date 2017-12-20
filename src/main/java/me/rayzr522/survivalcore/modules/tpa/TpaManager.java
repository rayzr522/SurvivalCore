package me.rayzr522.survivalcore.modules.tpa;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import me.rayzr522.survivalcore.api.managers.IManager;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class TpaManager implements IManager {
    private final Cache<UUID, TpaRequest> requestCache = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.MINUTES)
            .build();

    private CommandTpa commandTpa;
    private CommandTpAccept commandTpAccept;
    private CommandTpDeny commandTpDeny;

    @Override
    public void onLoad(SurvivalCore core) {
        commandTpa = new CommandTpa(this);
        commandTpAccept = new CommandTpAccept(this);
        commandTpDeny = new CommandTpDeny(this);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<ManagerCommand> getCommands() {
        return Arrays.asList(commandTpa, commandTpAccept, commandTpDeny);
    }

    public class TpaRequest {
        private UUID requester;
        private Location target;

        public TpaRequest(UUID requester, Location target) {
            this.requester = requester;
            this.target = target;
        }

        public UUID getRequester() {
            return requester;
        }

        public Location getTarget() {
            return target;
        }
    }
}
