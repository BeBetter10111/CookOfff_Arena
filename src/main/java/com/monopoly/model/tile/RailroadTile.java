public class RailroadTile extends Title {
    public RailroadTile(String name, int position) {
        super(name, position, true);
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " landed on " + getName() + " and must pay rent!");
    }
}