package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public class PropertyTile extends Tile {

    private static final int MAX_HOUSES   = 4;
    private static final int HOTEL_MARKER = 5;
    private static final int MORTGAGE_RATE        = 50;
    private static final int REDEEM_RATE          = 55;
    private static final int MORTGAGE_TURN_LIMIT  = 3;

    private final int   price;
    private final int[] rentTiers;
    private final String colorGroup;
    private final int   houseCost;

    private Player  owner;
    private int     houses;
    private boolean mortgaged;
    private int     mortgageTurnsLeft;

    public PropertyTile(int position, String name,
                        int price, int[] rentTiers,
                        String colorGroup, int houseCost) {
        super(position, name);
        this.price            = price;
        this.rentTiers        = rentTiers.clone();
        this.colorGroup       = colorGroup;
        this.houseCost        = houseCost;
        this.owner            = null;
        this.houses           = 0;
        this.mortgaged        = false;
        this.mortgageTurnsLeft = 0;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        if (owner == null) {
            player.notifyLandedOnUnowned(this);
            return;
        }

        if (mortgaged) {
            return;
        }

        if (owner.equals(player)) {
            return;
        }

        int rent = calcRent(player, context);
        boolean paid = player.pay(rent);

        if (!paid) {
            context.getBank().handleDebt(player, owner, rent);
            return;
        }

        owner.receive(rent);
        context.getBank().logTransaction(player, owner, rent);
    }

    public int calcRent(Player lander, GameContext context) {
        if (houses == 0) {
            boolean monopoly = hasMonopoly();
            return monopoly ? rentTiers[0] * 2 : rentTiers[0];
        }

        int tier = Math.min(houses, HOTEL_MARKER);
        return rentTiers[tier];
    }

    public boolean buildHouse(GameContext context) {
        if (owner == null || mortgaged)          return false;
        if (!hasMonopoly())                       return false;
        if (houses >= HOTEL_MARKER)               return false;

        boolean paid = owner.pay(houseCost);
        if (!paid) return false;

        if (houses < MAX_HOUSES) {
            context.getBank().useHouse();
        } else {
            context.getBank().returnHouses(MAX_HOUSES);
            context.getBank().useHotel();
        }

        houses++;
        return true;
    }

    public boolean sellHouse(GameContext context) {
        if (houses == 0) return false;

        if (houses == HOTEL_MARKER) {
            context.getBank().returnHotel();
            context.getBank().useHouses(MAX_HOUSES);
        } else {
            context.getBank().returnHouse();
        }

        houses--;
        owner.receive(houseCost / 2);
        return true;
    }

    public boolean mortgage(GameContext context) {
        if (mortgaged || owner == null) return false;
        if (houses > 0)                 return false;

        int mortgageValue = price * MORTGAGE_RATE / 100;
        owner.receive(mortgageValue);
        mortgaged = true;
        mortgageTurnsLeft = MORTGAGE_TURN_LIMIT;
        return true;
    }

    public boolean redeem(GameContext context) {
        if (!mortgaged || owner == null) return false;

        int redeemCost = price * REDEEM_RATE / 100;
        boolean paid = owner.pay(redeemCost);
        if (!paid) return false;

        mortgaged = false;
        mortgageTurnsLeft = 0;
        return true;
    }

    public void decrementMortgageTurn() {
        if (mortgaged && mortgageTurnsLeft > 0) {
            mortgageTurnsLeft--;
        }
    }

    public boolean isMortgageExpired() {
        return mortgaged && mortgageTurnsLeft == 0;
    }

    public boolean hasMonopoly() {
        if (owner == null) return false;
        return owner.getProperties().stream()
                .filter(p -> p.getColorGroup().equals(this.colorGroup))
                .count() == getGroupSize();
    }

    private long getGroupSize() {
        return switch (colorGroup) {
            case "Brown", "DarkBlue" -> 2;
            default -> 3;
        };
    }

    public void setOwner(Player owner) { this.owner = owner; }
    public Player getOwner()           { return owner; }
    public int getPrice()              { return price; }
    public int[] getRentTiers()        { return rentTiers.clone(); }
    public String getColorGroup()      { return colorGroup; }
    public int getHouseCost()          { return houseCost; }
    public int getHouses()             { return houses; }
    public boolean isMortgaged()       { return mortgaged; }
    public int getMortgageTurnsLeft()  { return mortgageTurnsLeft; }
    public boolean hasHotel()          { return houses == HOTEL_MARKER; }
    public int getMortgageValue()      { return price * MORTGAGE_RATE / 100; }
    public int getRedeemCost()         { return price * REDEEM_RATE / 100; }
}
