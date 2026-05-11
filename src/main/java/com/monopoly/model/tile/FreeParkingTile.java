package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public class FreeParkingTile extends Tile {

    public FreeParkingTile(int position) {
        super(position, "Free Parking");
    }

    @Override
    public void onLand(Player player, GameContext context) {
        // Không có hiệu ứng theo luật chuẩn
    }
}
