package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class CommandTpAccept extends ManagerCommand<TpaManager> {
    public CommandTpAccept(TpaManager manager) {
        super(manager);
    }

    @Override
    public String getCommandName() {
        return "tpaccept";
    }

    @Override
    public String getPermission() {
        return "tpaccept";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("tpyes");
    }

    @Override
    public List<CommandTarget> getTargets() {
        return Arrays.asList(CommandTarget.PLAYER);
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        TpaManager.TpaRequest request = getManager().getCurrentRequest(ctx.getPlayer().getUniqueId())
                .orElseThrow(ctx.fail("command.tpaccept.no-request"));

        // Already handled by TpaRequest#isValid() (which is called in TpaManager#getCurrentRequest())
        // This is just to get IntelliJ to shut up.
        assert request.getTargetPlayer().isPresent();
        assert request.getRequesterPlayer().isPresent();

        Player target = request.getTargetPlayer().get();
        Player requester = request.getRequesterPlayer().get();
        TpaDirection direction = request.getDirection();

        direction.apply(target, requester);
        target.sendMessage(getSuccessMessage(direction.getTargetMessageKey(), requester.getDisplayName()));
        requester.sendMessage(getSuccessMessage(direction.getRequesterMessageKey(), target.getDisplayName()));

        if (!getManager().cancelRequest(request.getTarget())) {
            ctx.getPlayer().sendMessage("Failed to cancel request. What the frack?");
            return CommandResult.FAIL;
        }

        return CommandResult.SUCCESS;
    }

    private String getSuccessMessage(String key, String username) {
        return getPlugin().tr(String.format("command.tpa.success.%s", key), username);
    }
}
