package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public class GoTile extends Tile {

    private static final int PASS_GO_REWARD = 200;

    public GoTile(int position) {
        super(position, "GO");
    }

    @Override
    public void onLand(Player player, GameContext context) {
        context.getBank().pay(player, PASS_GO_REWARD);
        player.receive(PASS_GO_REWARD);
    }

    public int getReward() {
        return PASS_GO_REWARD;
    }
}
