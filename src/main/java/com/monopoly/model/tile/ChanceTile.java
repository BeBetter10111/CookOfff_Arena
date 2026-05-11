package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.card.Card;
import com.monopoly.model.card.CardDeck;
import com.monopoly.model.player.Player;

public class ChanceTile extends Tile {

    private final CardDeck chanceDeck;

    public ChanceTile(int position, CardDeck chanceDeck) {
        super(position, "Chance");
        this.chanceDeck = chanceDeck;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        Card card = chanceDeck.draw();
        if (card != null) {
            card.apply(player, context);
        }
    }
}
