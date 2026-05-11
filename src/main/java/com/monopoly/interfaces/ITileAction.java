package com.monopoly.interfaces;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public interface ITileAction {
    void onLand(Player player, GameContext context);
}
