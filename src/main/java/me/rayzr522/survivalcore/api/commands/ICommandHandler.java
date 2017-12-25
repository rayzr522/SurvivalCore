package me.rayzr522.survivalcore.api.commands;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.exceptions.GenericCommandException;
import me.rayzr522.survivalcore.api.commands.exceptions.NoSuchPlayerException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public interface ICommandHandler {
    /**
     * @return The {@link SurvivalCore} instance.
     */
    default SurvivalCore getPlugin() {
        // Maybe a better way to do this? Probably not. Good enough for now.
        return SurvivalCore.getInstance();
    }

    /**
     * @return The name of the command this {@link ICommandHandler} is associated with.
     */
    String getCommandName();

    /**
     * @return A list of aliases for this command, empty by default
     */
    default List<String> getAliases() {
        return Collections.emptyList();
    }

    /**
     * @return The permission required to use this command (or null)
     */
    String getPermission();

    /**
     * @return The applicable targets that can use this command (e.g. console or player)
     */
    default List<CommandTarget> getTargets() {
        return Arrays.asList(CommandTarget.CONSOLE, CommandTarget.PLAYER);
    }

    /**
     * @param sender The {@link CommandSender} that used this command.
     * @param args   The arguments passed to the command.
     * @return The {@link CommandResult} of the command.
     */
    CommandResult onCommand(CommandContext ctx);
}
