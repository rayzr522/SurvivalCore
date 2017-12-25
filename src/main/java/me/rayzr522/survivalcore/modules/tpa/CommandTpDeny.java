package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class CommandTpDeny extends ManagerCommand<TpaManager> {
    public CommandTpDeny(TpaManager manager) {
        super(manager);
    }

    @Override
    public String getCommandName() {
        return "tpdeny";
    }

    @Override
    public String getPermission() {
        return "tpa.deny";
    }

    @Override
    public List<CommandTarget> getTargets() {
        return Arrays.asList(CommandTarget.PLAYER);
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        return CommandResult.SUCCESS;
    }
}
