package com.monopoly.model.tile;

import com.monopoly.interfaces.ITileAction;
import com.monopoly.model.player.Player;


public class Title implements ITileAction {
    private String name;
    private int position;
    private boolean hasTrap;

    public Title(String name, int position, boolean hasTrap) {
        this.name = name;
        this.position = position;
        this.hasTrap = hasTrap;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        // Implement the action that occurs when a player lands on this tile
        System.out.println(player.getName() + " has landed on " + name);
        if (hasTrap) {
        }
        // Additional logic can be added here based on the type of tile
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    void  placeTrap() {
        hasTrap = true;
    }
}