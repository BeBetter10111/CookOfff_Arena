package com.monopoly.lobby.model;

/**
 * Represents one player in the game room lobby.
 */
public class Player {

    private final String name;
    private final String token;
    private final int playerIndex;

    public Player(String name, String token, int playerIndex) {
        this.name = name;
        this.token = token;
        this.playerIndex = playerIndex;
    }

    public String getName() { return name; }
    public String getToken() { return token; }
    public int getPlayerIndex() { return playerIndex; }
}
