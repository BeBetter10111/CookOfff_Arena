package main.java.com.monopoly.action;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public class BuyPropertyAction implements ITileAction {
    private IBuyStrategy buyStrategy;

    public BuyPropertyAction(IBuyStrategy buyStrategy) {
        this.buyStrategy = buyStrategy;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        if (buyStrategy.shouldBuy(player, context)) {
            // Logic to buy the property
            System.out.println(player.getName() + " decides to buy the property.");
        } else {
            System.out.println(player.getName() + " decides not to buy the property.");
        }
    }
}