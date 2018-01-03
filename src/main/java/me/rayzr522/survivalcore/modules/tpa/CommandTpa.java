package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ModuleCommand;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class CommandTpa extends ModuleCommand<TpaModule> {
    public CommandTpa(TpaModule module) {
        super(module);
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

        getModule().requestTeleport(new TpaModule.TpaRequest(requester.getUniqueId(), target.getUniqueId(), TpaDirection.TO_TARGET));
        ctx.tell("command.tpa.requested", target.getDisplayName());
        ctx.tell(target, "command.tpa.target-notification", requester.getDisplayName());

        return CommandResult.SUCCESS;
    }
}
