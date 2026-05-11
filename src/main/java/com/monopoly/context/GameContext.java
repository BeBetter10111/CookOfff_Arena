package com.monopoly.context;

import com.monopoly.action.ActionHistory;
import com.monopoly.controller.GameMode;
import com.monopoly.model.GameDifficulty;
import com.monopoly.model.board.Board;
import com.monopoly.model.finance.Bank;
import com.monopoly.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameContext {
    private final Board board;
    private final Bank bank;
    private final ActionHistory actionHistory;
    private final List<Player> players;
    private final GameMode gameMode;
    private int trapRentMultiplier = 1;

    public GameContext(Board board, Bank bank, List<Player> players, GameMode gameMode) {
        this.board = board;
        this.bank = bank;
        this.players = new ArrayList<>(players);
        this.gameMode = gameMode;
        this.actionHistory = new ActionHistory();
    }

    public Board getBoard() {
        return board;
    }

    public Bank getBank() {
        return bank;
    }

    public ActionHistory getActionHistory() {
        return actionHistory;
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public List<Player> getOtherPlayers(Player current) {
        return players.stream().filter(p -> p != current).toList();
    }

    public int getHousesOwnedBy(Player player) {
        return (int) player.getProperties().stream().mapToInt(p -> p.getHouses()).sum();
    }

    public int getHotelsOwnedBy(Player player) {
        return (int) player.getProperties().stream().filter(p -> p.hasHotel()).count();
    }

    public void triggerAirplaneChoice(Player player) {
        // UI integration point
    }

    public void triggerTargetPlayerChoice(Player player) {
        // UI integration point
    }

    public GameDifficulty getDifficulty() {
        return gameMode == GameMode.HARD ? GameDifficulty.HARD : GameDifficulty.EASY;
    }

    public void setTrapRentMultiplier(int trapRentMultiplier) {
        this.trapRentMultiplier = Math.max(1, trapRentMultiplier);
    }

    public int getTrapRentMultiplier() {
        return trapRentMultiplier;
    }

    public void resetTrapRentMultiplier() {
        this.trapRentMultiplier = 1;
    }
}
