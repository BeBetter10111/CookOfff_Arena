public class UtilityTile extends Title {
    public UtilityTile(String name, int position) {
        super(name, position, true);
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " landed on " + getName() + " and can choose to buy it or pay rent!");
    }
}