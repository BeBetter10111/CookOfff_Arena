package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public class UtilityTile extends Tile {

    private static final int PRICE              = 150;
    private static final int SINGLE_MULTIPLIER  = 4;
    private static final int BOTH_MULTIPLIER    = 10;
    private static final int MORTGAGE_RATE      = 50;
    private static final int REDEEM_RATE        = 55;

    private Player  owner;
    private boolean mortgaged;

    public UtilityTile(int position, String name) {
        super(position, name);
        this.owner     = null;
        this.mortgaged = false;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        if (owner == null) {
            player.notifyLandedOnUnowned(this);
            return;
        }

        if (mortgaged || owner.equals(player)) {
            return;
        }

        int diceTotal = context.getBank().getLastDiceTotal();
        int rent      = calcRent(diceTotal, owner);
        boolean paid  = player.pay(rent);

        if (!paid) {
            context.getBank().handleDebt(player, owner, rent);
            return;
        }

        owner.receive(rent);
        context.getBank().logTransaction(player, owner, rent);
    }

    public int calcRent(int diceTotal, Player utilityOwner) {
        long count = utilityOwner.getAllProperties().stream()
                .filter(p -> p instanceof UtilityTile)
                .count();
        int multiplier = (count >= 2) ? BOTH_MULTIPLIER : SINGLE_MULTIPLIER;
        return diceTotal * multiplier;
    }

    public boolean mortgage() {
        if (mortgaged || owner == null) return false;
        owner.receive(getMortgageValue());
        mortgaged = true;
        return true;
    }

    public boolean redeem() {
        if (!mortgaged || owner == null) return false;
        boolean paid = owner.pay(getRedeemCost());
        if (!paid) return false;
        mortgaged = false;
        return true;
    }

    public void setOwner(Player owner) { this.owner = owner; }
    public Player getOwner()           { return owner; }
    public boolean isMortgaged()       { return mortgaged; }
    public int getPrice()              { return PRICE; }
    public int getMortgageValue()      { return PRICE * MORTGAGE_RATE / 100; }
    public int getRedeemCost()         { return PRICE * REDEEM_RATE / 100; }
}
