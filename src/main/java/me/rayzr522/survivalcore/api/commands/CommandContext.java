package me.rayzr522.survivalcore.api.commands;

import me.rayzr522.survivalcore.api.commands.exceptions.GenericCommandException;
import me.rayzr522.survivalcore.api.commands.exceptions.NoSuchPlayerException;
import me.rayzr522.survivalcore.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class CommandContext {
    private final ICommandHandler command;
    private final CommandSender sender;
    private final List<String> args;
    private int currentArg = 0;

    public CommandContext(ICommandHandler command, CommandSender sender, String[] args) {
        this.command = command;
        this.sender = sender;
        this.args = Arrays.asList(args);
    }

    public CommandSender getSender() {
        return sender;
    }

    public Player getPlayer() {
        return (Player) sender;
    }

    public void tell(CommandSender sender, String key, Object... objects) {
        sender.sendMessage(command.getPlugin().tr(key, objects));
    }

    public void tell(String key, Object... objects) {
        tell(sender, key, objects);
    }

    public Supplier<GenericCommandException> fail(String key, Object... objects) {
        return () -> new GenericCommandException(command.getPlugin().trRaw(key, objects));
    }

    public boolean hasArgs(int amount) {
        return args.size() >= amount;
    }

    public boolean hasArgs() {
        return hasArgs(1);
    }

    public List<String> getArgs() {
        return args.subList(currentArg, args.size());
    }

    public String remainder(String joiner) {
        return Utils.join(getArgs(), joiner);
    }

    public String remainder() {
        return remainder(" ");
    }

    public String first() {
        return currentArg >= args.size() ? null : args.get(currentArg);
    }

    public String shift() {
        if (currentArg == args.size()) {
            return null;
        }

        return args.get(currentArg++);
    }

    public Player shiftPlayer() {
        String name = shift();

        return Utils.findPlayer(name)
                .orElseThrow(() -> new NoSuchPlayerException(name));
    }
}
