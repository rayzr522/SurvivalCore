package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class CommandTpaHere extends ManagerCommand<TpaManager> {
    public CommandTpaHere(TpaManager manager) {
        super(manager);
    }

    @Override
    public String getCommandName() {
        return "tpahere";
    }

    @Override
    public String getPermission() {
        return "tpahere";
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

        getManager().requestTeleport(new TpaManager.TpaRequest(requester.getUniqueId(), target.getUniqueId(), TpaDirection.TO_REQUSTER));
        ctx.tell("command.tpahere.requested", target.getDisplayName());
        ctx.tell(target, "command.tpahere.target-notification", requester.getDisplayName());

        return CommandResult.SUCCESS;
    }
}
