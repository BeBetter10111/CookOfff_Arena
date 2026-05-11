package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public class GoToJailTile extends Tile {

    public GoToJailTile(int position) {
        super(position, "Go To Jail");
    }

    @Override
    public void onLand(Player player, GameContext context) {
        JailTile.sendToJail(player);
        context.getBank().notifyEvent("PLAYER_JAILED", player);
    }
}
