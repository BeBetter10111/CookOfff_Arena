package com.monopoly.lobby.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds all shared state for one game room.
 */
public class GameRoom {

    public enum Difficulty { EASY, HARD }
    public enum BotMode { AGGRESSIVE, CONSERVATIVE, NONE }

    private final String roomId;
    private final String roomName;
    private final Difficulty difficulty;
    private final int maxPlayers;
    private final BotMode botMode;
    private final int initialCapital;
    private final List<Player> players = new ArrayList<>();

    private boolean open = true;

    public GameRoom(String roomId, String roomName, Difficulty difficulty, int maxPlayers, BotMode botMode) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.botMode = botMode;
        this.initialCapital = (difficulty == Difficulty.EASY) ? 200 : 500;
    }

    public synchronized boolean addPlayer(Player player) {
        if (!open || players.size() >= maxPlayers) return false;
        players.add(player);
        if (players.size() == maxPlayers) open = false;
        return true;
    }

    public synchronized List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public synchronized int getPlayerCount() { return players.size(); }
    public synchronized boolean isOpen() { return open; }

    public String getRoomId() { return roomId; }
    public String getRoomName() { return roomName; }
    public Difficulty getDifficulty() { return difficulty; }
    public int getMaxPlayers() { return maxPlayers; }
    public BotMode getBotMode() { return botMode; }
    public int getInitialCapital() { return initialCapital; }
}
