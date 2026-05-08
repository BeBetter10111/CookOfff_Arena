package com.monopoly.dice;


import java.util.Random;


public class Dice {


    private static final int SIDES = 6;
    private final Random random = new Random();
    private int[] lastRoll = new int[2];
    private int consecutiveDoubles = 0;


    public int[] roll() {
        lastRoll[0] = random.nextInt(SIDES) + 1;
        lastRoll[1] = random.nextInt(SIDES) + 1;


        if (isDouble()) {
            consecutiveDoubles++;
        } else {
            consecutiveDoubles = 0;
        }


        return lastRoll.clone();
    }


    public int getTotal() {
        return lastRoll[0] + lastRoll[1];
    }


    public boolean isDouble() {
        return lastRoll[0] == lastRoll[1];
    }


    public boolean isTripleDouble() {
        return consecutiveDoubles >= 3;
    }


    public int getConsecutiveDoubles() {
        return consecutiveDoubles;
    }


    public void resetConsecutiveDoubles() {
        consecutiveDoubles = 0;
    }


    public int[] getLastRoll() {
        return lastRoll.clone();
    }
}
