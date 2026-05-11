package com.monopoly.model.trap;

public class TrapEffect {
    private final int rentMultiplier;
    private final int extraTurnsPenalty;
    private final boolean swappedToJail;

    public TrapEffect(int rentMultiplier, int extraTurnsPenalty, boolean swappedToJail) {
        this.rentMultiplier = rentMultiplier;
        this.extraTurnsPenalty = extraTurnsPenalty;
        this.swappedToJail = swappedToJail;
    }

    public int getRentMultiplier() {
        return rentMultiplier;
    }

    public int getExtraTurnsPenalty() {
        return extraTurnsPenalty;
    }

    public boolean isSwappedToJail() {
        return swappedToJail;
    }

    public static TrapEffect none() {
        return new TrapEffect(1, 0, false);
    }
}
