package com.monopoly.model.player;


import com.monopoly.model.tile.PropertyTile;

public class HumanPlayer extends Player {
    public HumanPlayer(String name, int initialBalance) {
        super(name, initialBalance);
    }

    @Override
    public void takeTurn(int dice1, int dice2) {
        move(dice1 + dice2);
    }

    @Override
    public void onLandDecision(PropertyTile property, boolean wantToBuy) {
        if (wantToBuy && deductBalance(property.getPrice())) {
            property.setOwner(this);
            addProperty(property);
        }
    }
}
