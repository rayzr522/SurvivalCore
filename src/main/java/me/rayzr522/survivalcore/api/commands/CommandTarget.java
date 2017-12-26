package me.rayzr522.survivalcore.api.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Controls which kinds of {@link CommandSender command senders} are allowed.
 * <p>
 * Created by Rayzr522 on 5/27/17.
 */
public enum CommandTarget {
    /**
     * Allows only users who are <code>instanceof {@link Player}</code> to use the command.
     */
    PLAYER(sender -> sender instanceof Player),
    /**
     * Allows only users who are <code>instanceof {@link ConsoleCommandSender}</code> to use the command.
     */
    CONSOLE(sender -> sender instanceof ConsoleCommandSender);

    private final Predicate<CommandSender> filter;

    CommandTarget(Predicate<CommandSender> filter) {
        this.filter = filter;
    }

    /**
     * @param sender The {@link CommandSender} to check.
     * @return Whether or not the given {@link CommandSender} fits within the requirements of this {@link CommandTarget}.
     */
    public boolean isApplicable(CommandSender sender) {
        return filter.test(sender);
    }

    /**
     * A utility method, for use in {@link ICommandHandler#getTargets()}.
     *
     * @return A list with this {@link CommandTarget} as the only element.
     */
    public List<CommandTarget> only() {
        return Collections.singletonList(this);
    }
}
