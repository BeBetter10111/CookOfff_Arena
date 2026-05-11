package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.interfaces.ITileAction;
import com.monopoly.model.player.Player;
import com.monopoly.model.trap.Trap;

public abstract class Tile implements ITileAction {

    protected final int position;
    protected final String name;
    private Trap trap;

    protected Tile(int position, String name) {
        this.position = position;
        this.name = name;
        this.trap = null;
    }

    @Override
    public abstract void onLand(Player player, GameContext context);

    public void placeTrap(Trap trap) {
        this.trap = trap;
    }

    public void removeTrap() {
        this.trap = null;
    }

    public boolean hasTrap() {
        return trap != null && trap.isActive();
    }

    public Trap getTrap() {
        return trap;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + position + "] " + name;
    }
}
