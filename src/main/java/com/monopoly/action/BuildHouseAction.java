package main.java.com.monopoly.action;

public class BuildHouseAction implements ITileAction {
    private int houseCost;

    public BuildHouseAction(int houseCost) {
        this.houseCost = houseCost;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        if (player.getBalance() >= houseCost) {
            player.deductMoney(houseCost);
            context.getBank().addMoney(houseCost);
            context.getActionHistory().recordAction(player.getName() + " built a house for $" + houseCost);
        } else {
            context.getActionHistory().recordAction(player.getName() + " attempted to build a house but couldn't afford it.");
        }
    }
}