package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import org.bukkit.command.CommandSender;

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
    public List<CommandTarget> getTargets() {
        return Arrays.asList(CommandTarget.PLAYER);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }
}
