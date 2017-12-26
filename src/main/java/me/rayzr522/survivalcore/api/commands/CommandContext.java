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

    /**
     * Creates a new command context object.
     *
     * @param command The command which the sender executed.
     * @param sender  The command sender who ran the command.
     * @param args    The arguments that the sender passed to the command.
     */
    public CommandContext(ICommandHandler command, CommandSender sender, String[] args) {
        this.command = command;
        this.sender = sender;
        this.args = Arrays.asList(args);
    }

    /**
     * @return The {@link CommandSender} that executed the command.
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * @return The {@link CommandSender} that executed the command, cast to a {@link Player}. Warning: be careful doing this, only call this if you know for a fact that it was a player who ran the command.
     */
    public Player getPlayer() {
        return (Player) sender;
    }

    /**
     * Sends a message with the given key to the provided {@link CommandSender}, translating it with the provided objects.
     *
     * @param sender  The {@link CommandSender} to send the message to.
     * @param key     The key of the message to send.
     * @param objects The objects to translate the message with.
     */
    public void tell(CommandSender sender, String key, Object... objects) {
        sender.sendMessage(command.getPlugin().tr(key, objects));
    }

    /**
     * Calls {@link #tell(CommandSender, String, Object...)} with the command executor as the first parameter.
     *
     * @param key     The key of the message to send.
     * @param objects The objects to translate the message with.
     */
    public void tell(String key, Object... objects) {
        tell(sender, key, objects);
    }

    /**
     * Returns a {@link Supplier} of a {@link GenericCommandException}, for use with {@link java.util.Optional#orElseThrow(Supplier)}.
     *
     * @param key     The key of the message for the error.
     * @param objects The objects to translate the message with.
     * @return A {@link Supplier} for a {@link GenericCommandException} with the given message.
     */
    public Supplier<GenericCommandException> fail(String key, Object... objects) {
        return () -> new GenericCommandException(command.getPlugin().trRaw(key, objects));
    }

    /**
     * @param amount The amount of arguments to check.
     * @return Whether or not at least that number of arguments was passed to the command.
     */
    public boolean hasArgs(int amount) {
        return args.size() >= amount;
    }

    /**
     * Calls {@link #hasArgs(int)} with <code>1</code> as the parameter.
     *
     * @return Whether or not at least 1 argument was passed to the command.
     */
    public boolean hasArgs() {
        return hasArgs(1);
    }

    /**
     * @return The list of arguments that were passed to the command, removing any arguments that were {@link #shift() shift}ed off.
     */
    public List<String> getArgs() {
        return args.subList(currentArg, args.size());
    }

    /**
     * @param joiner The string to join the arguments with.
     * @return The remaining arguments, concatenated with the given joiner.
     */
    public String remainder(String joiner) {
        return Utils.join(getArgs(), joiner);
    }

    /**
     * Calls {@link #remainder(String)} with a space (<code>" "</code>) as the joiner.
     *
     * @return The remaining arguments, concatenated with a space.
     */
    public String remainder() {
        return remainder(" ");
    }

    /**
     * @return The first (unshifted) argument from the args list.
     */
    public String first() {
        return currentArg >= args.size() ? null : args.get(currentArg);
    }

    /**
     * Pops an argument off the start of the argument list and returns it.
     *
     * @return The shifted argument, or <code>null</code> if there are no arguments left.
     */
    public String shift() {
        if (currentArg == args.size()) {
            return null;
        }

        return args.get(currentArg++);
    }

    /**
     * {@link #shift() Shifts} the first argument off of the argument list and finds a matching {@link Player}.
     *
     * @return The player whose name most closely matches the shifted argument.
     * @throws NoSuchPlayerException If the player could not be found.
     */
    public Player shiftPlayer() {
        String name = shift();

        return Utils.findPlayer(name)
                .orElseThrow(() -> new NoSuchPlayerException(name));
    }
}
