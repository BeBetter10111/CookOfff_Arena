package com.monopoly.model.trap;


import com.monopoly.model.GameDifficulty;
import com.monopoly.model.player.Player;


public abstract class Trap {


    private final Player owner;
    private final int tilePosition;
    private boolean active;


    public Trap(Player owner, int tilePosition) {
        this.owner = owner;
        this.tilePosition = tilePosition;
        this.active = true;
    }


    public abstract TrapEffect trigger(Player victim, GameDifficulty difficulty);


    public abstract String getDescription();


    public Player getOwner() {
        return owner;
    }


    public int getTilePosition() {
        return tilePosition;
    }


    public boolean isActive() {
        return active;
    }


    public void deactivate() {
        this.active = false;
    }
}
