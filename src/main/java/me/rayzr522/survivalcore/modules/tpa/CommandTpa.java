package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class CommandTpa extends ManagerCommand<TpaManager> {
    public CommandTpa(TpaManager manager) {
        super(manager);
    }

    @Override
    public String getCommandName() {
        return "tpa";
    }

    @Override
    public String getPermission() {
        return "tpa";
    }

    @Override
    public List<CommandTarget> getTargets() {
        return CommandTarget.PLAYER.only();
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        if (!ctx.hasArgs()) {
            return CommandResult.SHOW_USAGE;
        }

        Player requester = ctx.getPlayer();
        Player target = ctx.shiftPlayer();

        getManager().requestTeleport(new TpaManager.TpaRequest(requester.getUniqueId(), target.getUniqueId(), TpaDirection.TO_TARGET));
        ctx.tell("command.tpa.requested", target.getDisplayName());
        ctx.tell(target, "command.tpa.target-notification", requester.getDisplayName());

        return CommandResult.SUCCESS;
    }
}
