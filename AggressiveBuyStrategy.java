package main.java.com.strategy;

import main.java.com.Player;
import main.java.com.interfaces.IBuyStrategy;
import main.java.com.model.player.Player;
import main.java.com.model.tile.PropertyTile;


public class AggressiveBuyStrategy implements IBuyStrategy{
    private final int minBalance = 100;

    @Override
    public boolean shouldBuy(PropertyTile property, Player player) {
        int BalanceAfterBuy = player.getBalance() - property.getPrice();
        return BalanceAfterBuy >= minBalance;
    }
}
