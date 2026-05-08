public class GoTile extends Title {
    public GoTile(String name, int position) {
        super(name, position, false);
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " landed on Go and receives $200!");
    }
}