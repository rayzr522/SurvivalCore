package me.rayzr522.survivalcore.api.commands;

/**
 * The result of a command, which is used in the {@link InternalCommandExecutor} to supply the user with more information when needed.
 */
public enum CommandResult {
    /**
     * Indicates that the command ran
     */
    SUCCESS,
    /**
     * Indicates that the command had invalid input, and that the usage message for the command should be shown.
     */
    SHOW_USAGE,
    /**
     * Indicates that the command failed for whatever reason. Currently does not affect any command logic.
     */
    FAIL
}
