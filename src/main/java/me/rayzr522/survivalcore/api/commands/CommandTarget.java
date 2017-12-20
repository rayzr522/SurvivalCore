package me.rayzr522.survivalcore.api.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public enum CommandTarget {
    PLAYER(sender -> sender instanceof Player),
    CONSOLE(sender -> sender instanceof ConsoleCommandSender);

    private final Predicate<CommandSender> filter;

    CommandTarget(Predicate<CommandSender> filter) {
        this.filter = filter;
    }

    public boolean isApplicable(CommandSender sender) {
        return filter.test(sender);
    }
}
