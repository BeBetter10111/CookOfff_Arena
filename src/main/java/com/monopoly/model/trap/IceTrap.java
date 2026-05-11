package com.monopoly.model.trap;


import com.monopoly.model.GameDifficulty;
import com.monopoly.model.player.Player;


public class IceTrap extends Trap {


    public IceTrap(Player owner, int tilePosition) {
        super(owner, tilePosition);
    }


    @Override
    public TrapEffect trigger(Player victim, GameDifficulty difficulty) {
        int freezeTurns = difficulty == GameDifficulty.HARD ? 2 : 1;
        int rentMultiplier = difficulty == GameDifficulty.HARD ? 3 : 2;
        victim.setFrozenTurns(freezeTurns);
        deactivate();
        return new TrapEffect(rentMultiplier, 0, false);
    }


    @Override
    public String getDescription() {
        return "Ice trap";
    }
}
