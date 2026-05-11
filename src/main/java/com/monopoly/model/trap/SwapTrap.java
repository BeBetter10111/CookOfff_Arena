package com.monopoly.model.trap;


import com.monopoly.model.GameDifficulty;
import com.monopoly.model.player.Player;


public class SwapTrap extends Trap {


    public SwapTrap(Player owner, int tilePosition) {
        super(owner, tilePosition);
    }


    @Override
    public TrapEffect trigger(Player victim, GameDifficulty difficulty) {
        Player owner = getOwner();


        int ownerPosition = owner.getPosition();
        int victimPosition = victim.getPosition();


        owner.setPosition(victimPosition);
        victim.setPosition(ownerPosition);

        deactivate();
        return TrapEffect.none();
    }


    @Override
    public String getDescription() {
        return "Swap trap";
    }
}

