package com.monopoly.model.trap;


import com.monopoly.model.player.Player;


public class IceTrap extends Trap {


    private static final int FREEZE_TURNS = 1;


    public IceTrap(Player owner, int tilePosition) {
        super(owner, tilePosition);
    }


    @Override
    public void trigger(Player victim) {
        victim.setFrozenTurns(FREEZE_TURNS);
        deactivate();
    }


}
