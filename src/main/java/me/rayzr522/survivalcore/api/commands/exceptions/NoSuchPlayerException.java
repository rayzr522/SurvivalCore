package me.rayzr522.survivalcore.api.commands.exceptions;

public class NoSuchPlayerException extends RuntimeException {
    private final String playerName;

    public NoSuchPlayerException(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
