package com.monopoly.lobby.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRoom {

    public enum Difficulty { EASY, HARD }
    public enum BotMode { AGGRESSIVE, CONSERVATIVE, NONE }
    public static final int TOTAL_PLAYERS = 4;
    private static final String[] DEFAULT_TOKENS = {"🚗", "🎩", "🐱", "⚓", "👟", "🚙", "🤖"};

    private final String roomId;
    private final String roomName;
    private final Difficulty difficulty;
    private final int humanPlayers;
    private final int botPlayers;
    private final BotMode botMode;
    private final int initialCapital;
    private final List<Player> players = new ArrayList<>();

    private boolean open = true;

    public GameRoom(String roomId, String roomName, Difficulty difficulty, int humanPlayers, BotMode botMode) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.difficulty = difficulty;
        this.humanPlayers = Math.max(1, Math.min(TOTAL_PLAYERS, humanPlayers));
        this.botPlayers = TOTAL_PLAYERS - this.humanPlayers;
        this.botMode = this.botPlayers > 0 ? botMode : BotMode.NONE;
        this.initialCapital = (difficulty == Difficulty.EASY) ? 200 : 500;
    }

    public synchronized boolean addPlayer(Player player) {
        if (!open || players.size() >= humanPlayers) return false;
        players.add(player);
        if (players.size() == humanPlayers) {
            addBotsIfNeeded();
            open = false;
        }
        return true;
    }

    private void addBotsIfNeeded() {
        if (botPlayers <= 0) return;
        int startSeat = players.size() + 1;
        for (int i = 0; i < botPlayers; i++) {
            int seat = startSeat + i;
            String token = seat - 1 < DEFAULT_TOKENS.length ? DEFAULT_TOKENS[seat - 1] : "🤖";
            String name = botMode == BotMode.AGGRESSIVE ? "AggBot-" + seat : "ConBot-" + seat;
            players.add(new Player(name, token, seat, true));
        }
    }

    public synchronized List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public synchronized int getPlayerCount() { return players.size(); }
    public synchronized boolean isOpen() { return open; }

    public String getRoomId() { return roomId; }
    public String getRoomName() { return roomName; }
    public Difficulty getDifficulty() { return difficulty; }
    public int getMaxPlayers() { return TOTAL_PLAYERS; }
    public int getHumanPlayers() { return humanPlayers; }
    public int getBotPlayers() { return botPlayers; }
    public BotMode getBotMode() { return botMode; }
    public int getInitialCapital() { return initialCapital; }
}
