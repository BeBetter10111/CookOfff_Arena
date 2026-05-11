package com.monopoly.controller;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public class HardModeService {
    public boolean tryUseSwapCardFromJail(Player jailedPlayer, Player target, GameContext context) {
        if (jailedPlayer == null || target == null) return false;
        if (!jailedPlayer.isInJail()) return false;
        if (!jailedPlayer.useHardModeSwapCard()) return false;

        int jailedPos = jailedPlayer.getPosition();
        jailedPlayer.setPosition(target.getPosition());
        jailedPlayer.setInJail(false);

        target.setPosition(jailedPos);
        target.setInJail(true);

        context.getActionHistory().recordAction(
            jailedPlayer.getName() + " used hard swap card on " + target.getName() + "."
        );
        return true;
    }
}
