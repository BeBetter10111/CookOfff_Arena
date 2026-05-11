package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.card.Card;
import com.monopoly.model.card.CardDeck;
import com.monopoly.model.player.Player;

public class CommunityChestTile extends Tile {

    private final CardDeck communityDeck;

    public CommunityChestTile(int position, CardDeck communityDeck) {
        super(position, "Community Chest");
        this.communityDeck = communityDeck;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        Card card = communityDeck.draw();
        if (card != null) {
            card.apply(player, context);
        }
    }
}
