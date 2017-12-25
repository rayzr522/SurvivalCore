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
        return Arrays.asList(CommandTarget.PLAYER);
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
//        if (args.length < 1) {
//            return CommandResult.SHOW_USAGE;
//        }
//
//        Player player = Bukkit.getPlayer(args[0]);
//        if (player == null){
//            tell(sender, "command.fail.no-player", args[0]);
//            return CommandResult.FAIL;
//        }
//


        return CommandResult.SUCCESS;
    }
}
