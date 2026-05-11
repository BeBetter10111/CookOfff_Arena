package com.monopoly.action;

import com.monopoly.context.GameContext;
import com.monopoly.interfaces.ITileAction;
import com.monopoly.model.player.Player;

public class PayRentAction implements ITileAction {
    @Override
    public void onLand(Player player, GameContext context) {
        context.getActionHistory().recordAction(player.getName() + " landed and paid rent.");
    }
}