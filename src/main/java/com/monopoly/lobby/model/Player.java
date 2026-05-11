package com.monopoly.lobby.model;

public class Player {

    private final String name;
    private final String token;
    private final int playerIndex;
    private final boolean bot;

    public Player(String name, String token, int playerIndex) {
        this(name, token, playerIndex, false);
    }

    public Player(String name, String token, int playerIndex, boolean bot) {
        this.name = name;
        this.token = token;
        this.playerIndex = playerIndex;
        this.bot = bot;
    }

    public String getName() { return name; }
    public String getToken() { return token; }
    public int getPlayerIndex() { return playerIndex; }
    public boolean isBot() { return bot; }
}
