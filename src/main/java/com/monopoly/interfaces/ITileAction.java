package com.monopoly.interfaces;

import com.monopoly.model.player.Player;
import com.monopoly.model.GameContext;

public interface ITileAction {
    void onLand(Player player, GameContext context);
}