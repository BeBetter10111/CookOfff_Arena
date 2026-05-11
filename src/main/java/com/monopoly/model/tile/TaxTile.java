package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public abstract class TaxTile extends Tile {

    protected final int taxAmount;

    protected TaxTile(int position, String name, int taxAmount) {
        super(position, name);
        this.taxAmount = taxAmount;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        boolean paid = player.pay(taxAmount);
        if (!paid) {
            context.getBank().handleDebt(player, null, taxAmount);
            return;
        }
        context.getBank().collect(taxAmount);
    }

    public int getTaxAmount() {
        return taxAmount;
    }
}
