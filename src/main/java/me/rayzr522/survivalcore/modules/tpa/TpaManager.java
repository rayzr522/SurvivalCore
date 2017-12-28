package me.rayzr522.survivalcore.modules.tpa;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalNotification;
import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import me.rayzr522.survivalcore.api.managers.IManager;
import me.rayzr522.survivalcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    private CommandTpaHere commandTpaHere;
    private CommandTpAccept commandTpAccept;
    private CommandTpDeny commandTpDeny;

    @Override
    public void onLoad(SurvivalCore core) {
        commandTpa = new CommandTpa(this);
        commandTpaHere = new CommandTpaHere(this);
        commandTpAccept = new CommandTpAccept(this);
        commandTpDeny = new CommandTpDeny(this);
    }

    @Override
    public String getName() {
        return "TPA";
    }

    @Override
    public List<ManagerCommand> getCommands() {
        return Arrays.asList(commandTpa, commandTpaHere, commandTpAccept, commandTpDeny);
    }

    void requestTeleport(TpaRequest request) {
        requestCache.put(request.getTarget(), request);
    }

    boolean cancelRequest(UUID target) {
        TpaRequest request = requestCache.asMap().remove(target);
        if (request == null || !request.isValid()) {
            return false;
        } else {
            return true;
        }
    }

    Optional<TpaRequest> getCurrentRequest(UUID target) {
        TpaRequest request = requestCache.getIfPresent(target);

        if (request == null || !request.isValid()) {
            if (request != null) {
                requestCache.invalidate(request);
            }
            return Optional.empty();
        }

        return Optional.of(request);
    }

    public static class TpaRequest {
        private UUID requester;
        private UUID target;
        private TpaDirection direction;

        public TpaRequest(UUID requester, UUID target, TpaDirection direction) {
            this.requester = requester;
            this.target = target;
            this.direction = direction;
        }

        public UUID getRequester() {
            return requester;
        }

        public UUID getTarget() {
            return target;
        }

        public TpaDirection getDirection() {
            return direction;
        }

        public Optional<Player> getRequesterPlayer() {
            return Utils.getPlayer(requester);
        }

        public Optional<Player> getTargetPlayer() {
            return Utils.getPlayer(target);
        }

        public boolean isValid() {
            return getRequesterPlayer().isPresent() && getTargetPlayer().isPresent();
        }
    }
}

