package com.monopoly.model.tile;


public class JailTile extends Title {
    public JailTile(String name, int position) {
        super(name, position, false);
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " is just visiting Jail.");
    }
}