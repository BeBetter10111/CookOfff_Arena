package com.monopoly.model.trap;


import com.monopoly.model.GameDifficulty;
import com.monopoly.model.player.Player;


public class SpringTrap extends Trap {


    private static final int PUSH_STEPS = 2;
    private final Direction direction;


    public enum Direction {
        FORWARD, BACKWARD
    }


    public SpringTrap(Player owner, int tilePosition, Direction direction) {
        super(owner, tilePosition);
        this.direction = direction;
    }


    @Override
    public TrapEffect trigger(Player victim, GameDifficulty difficulty) {
        int steps = direction == Direction.FORWARD ? PUSH_STEPS : -PUSH_STEPS;
        int newPosition = victim.getPosition() + steps;


        if (newPosition < 0) {
            newPosition = 0;
        }


        victim.setPosition(newPosition);
        deactivate();

        int extraTurnsPenalty = 0;
        if (difficulty == GameDifficulty.HARD && victim.isInDebt()) {
            extraTurnsPenalty = 2;
        }
        return new TrapEffect(2, extraTurnsPenalty, false);
    }


    public Direction getDirection() {
        return direction;
    }

    @Override
    public String getDescription() {
        return "Spring trap";
    }
}
