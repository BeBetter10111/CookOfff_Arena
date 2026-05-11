package com.monopoly.action;

import com.monopoly.context.GameContext;
import com.monopoly.interfaces.ITileAction;
import com.monopoly.model.player.Player;

public class BuildHouseAction implements ITileAction {
    private final int houseCost;

    public BuildHouseAction(int houseCost) {
        this.houseCost = houseCost;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        if (player.pay(houseCost)) {
            context.getBank().addMoney(houseCost);
            context.getActionHistory().recordAction(player.getName() + " built a house for $" + houseCost);
        } else {
            context.getActionHistory().recordAction(player.getName() + " failed to build a house.");
        }
    }
}