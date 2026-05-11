package com.monopoly.action;

import com.monopoly.context.GameContext;
import com.monopoly.interfaces.IBuyStrategy;
import com.monopoly.interfaces.ITileAction;
import com.monopoly.model.player.Player;
import com.monopoly.model.tile.PropertyTile;
import com.monopoly.model.tile.Tile;

public class BuyPropertyAction implements ITileAction {
    private final IBuyStrategy buyStrategy;

    public BuyPropertyAction(IBuyStrategy buyStrategy) {
        this.buyStrategy = buyStrategy;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        Tile tile = context.getBoard().getTile(player.getPosition());
        if (!(tile instanceof PropertyTile property) || property.getOwner() != null) return;

        if (buyStrategy.shouldBuy(property, player) && player.pay(property.getPrice())) {
            property.setOwner(player);
            player.addProperty(property);
            context.getActionHistory().recordAction(player.getName() + " bought " + property.getName());
        }
    }
}