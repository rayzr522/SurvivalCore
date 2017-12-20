package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
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
    public List<CommandTarget> getTargets() {
        return Arrays.asList(CommandTarget.PLAYER);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }
}
