package me.rayzr522.survivalcore.api.commands.exceptions;


/**
 * Meant to be used for any problem that happened while executing a command that needs to be shown to the user.
 * <p>
 * Automatically handled in the {@link me.rayzr522.survivalcore.api.commands.InternalCommandExecutor interal command executor} and shown to the player.
 */
public class GenericCommandException extends RuntimeException {
    public GenericCommandException(String message) {
        super(message);
    }
}
