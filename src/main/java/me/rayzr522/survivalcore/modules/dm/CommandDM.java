package me.rayzr522.survivalcore.modules.dm;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;

import java.util.Arrays;
import java.util.List;

public class CommandDM extends ManagerCommand<DMManager> {
    public CommandDM(DMManager manager) {
        super(manager);
    }

    @Override
    public String getCommandName() {
        return "dm";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("msg", "whisper", "message");
    }

    @Override
    public String getPermission() {
        return "dm";
    }

    @Override
    public List<CommandTarget> getTargets() {
        return CommandTarget.PLAYER.only();
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        if (!ctx.hasArgs(2)) {
            return CommandResult.SHOW_USAGE;
        }

        getManager().sendDM(ctx.getPlayer(), ctx.shiftPlayer(), ctx.remainder());

        return CommandResult.SUCCESS;
    }
}
