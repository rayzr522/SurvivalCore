package me.rayzr522.survivalcore.api.commands.exceptions;

/**
 * Meant to be used when something tries to retrieve a player by name, and that player could not be found.
 * <p>
 * Automatically handled in the {@link me.rayzr522.survivalcore.api.commands.InternalCommandExecutor interal command executor} and shown to the player.
 */
public class NoSuchPlayerException extends RuntimeException {
    private final String playerName;

    public NoSuchPlayerException(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
