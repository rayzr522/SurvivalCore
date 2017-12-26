package me.rayzr522.survivalcore.api.commands;

import me.rayzr522.survivalcore.api.commands.exceptions.GenericCommandException;
import me.rayzr522.survivalcore.api.commands.exceptions.NoSuchPlayerException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

class InternalCommandExecutor implements CommandExecutor {
    private final ICommandHandler command;

    public InternalCommandExecutor(ICommandHandler command) {
        this.command = command;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CommandContext commandContext = new CommandContext(command, sender, args);

        if (!command.getTargets().stream().anyMatch(target -> target.isApplicable(sender))) {
            // TODO: Changed based on what the allowed targets are
            commandContext.tell("command.fail.only-players");
            return true;
        }

        if (!command.getPlugin().checkPermission(sender, command.getPermission(), true)) {
            return true;
        }

        CommandResult result;

        try {
            result = command.onCommand(commandContext);
        } catch (NoSuchPlayerException e) {
            commandContext.tell("command.fail.no-player", e.getPlayerName());
            return true;
        } catch (NumberFormatException e) {
            commandContext.tell("command.fail.not-number");
            return true;
        } catch (GenericCommandException e) {
            commandContext.tell("command.fail.generic", e.getMessage());
            return true;
        }

        switch (result) {
            case SHOW_USAGE:
                String usage = command.getPlugin().trRaw(String.format("command.%s.usage", command.getCommandName()));
                commandContext.tell(sender, "command.fail.invalid-usage", command.getCommandName(), usage);
        }

        return true;
    }
}
