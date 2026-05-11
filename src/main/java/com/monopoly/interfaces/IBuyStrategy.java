package com.monopoly.interfaces;

import com.monopoly.model.player.Player;
import com.monopoly.model.tile.PropertyTile;
import com.monopoly.model.tile.Tile;

public interface IBuyStrategy {
    boolean shouldBuy(Tile tile, Player player);
    boolean shouldBuildHouse(PropertyTile tile, Player player);
}
