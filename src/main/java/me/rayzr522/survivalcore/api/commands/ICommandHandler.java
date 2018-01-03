package me.rayzr522.survivalcore.api.commands;

import me.rayzr522.survivalcore.SurvivalCore;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a command, which is dynamically registered into Bukkit's command map.
 * <p>
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
     * @param ctx The {@link CommandContext context} for this command, containing the arguments, sender, etc.
     * @return The {@link CommandResult result} of the command.
     */
    CommandResult onCommand(CommandContext ctx);
}
