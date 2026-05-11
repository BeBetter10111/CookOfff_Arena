package com.monopoly.controller;

import com.monopoly.action.ActionHistory;
import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;
import com.monopoly.model.tile.Tile;
import com.monopoly.model.trap.Trap;
import com.monopoly.model.trap.TrapEffect;

import java.util.List;

public class TurnManager {
    private final List<Player> players;
    private final ActionHistory actionHistory;
    private final HardModeService hardModeService;
    private int currentPlayerIndex;

    public TurnManager(List<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.actionHistory = new ActionHistory();
        this.hardModeService = new HardModeService();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void playTurn(GameContext context, int dice1, int dice2) {
        Player player = getCurrentPlayer();

        if (player.isFrozen()) {
            player.decrementFrozenTurns();
            actionHistory.recordAction(player.getName() + " is frozen and skips this turn.");
            endTurn();
            return;
        }

        if (player.isInJail()) {
            if (context.getDifficulty() == com.monopoly.model.GameDifficulty.HARD
                && player.getHardModeSwapCards() > 0) {
                Player target = context.getOtherPlayers(player).stream()
                    .filter(p -> !p.isInJail())
                    .findFirst()
                    .orElse(null);
                if (target != null && hardModeService.tryUseSwapCardFromJail(player, target, context)) {
                    endTurn();
                    return;
                }
            }
            if (player.useJailFreeCard()) {
                player.setInJail(false);
                actionHistory.recordAction(player.getName() + " used a Jail Free card.");
            } else {
                actionHistory.recordAction(player.getName() + " is in jail and skips this turn.");
                endTurn();
                return;
            }
        }

        int steps = dice1 + dice2;
        context.getBank().setLastDiceTotal(steps);
        player.move(steps);

        Tile tile = context.getBoard().getTile(player.getPosition());
        context.resetTrapRentMultiplier();
        if (tile.hasTrap()) {
            Trap trap = tile.getTrap();
            if (trap.getOwner() != null && trap.getOwner() != player) {
                TrapEffect effect = trap.trigger(player, context.getDifficulty());
                context.setTrapRentMultiplier(effect.getRentMultiplier());
                if (effect.getExtraTurnsPenalty() > 0) {
                    player.markDebt(effect.getExtraTurnsPenalty());
                }
            }
        }
        tile.onLand(player, context);
        context.resetTrapRentMultiplier();
        endTurn();
    }

    public void endTurn() {
        Player ending = getCurrentPlayer();
        if (ending.isInDebt()) ending.decreaseDebtTurn();
        actionHistory.recordAction(ending.getName() + " ended their turn.");
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        actionHistory.recordAction(getCurrentPlayer().getName() + " starts their turn.");
    }

    public ActionHistory getActionHistory() {
        return actionHistory;
    }
}