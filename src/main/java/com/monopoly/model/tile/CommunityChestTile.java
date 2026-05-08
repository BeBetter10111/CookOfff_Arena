public class CommunityChestTile extends Title {
    public CommunityChestTile(String name, int position) {
        super(name, position, false);
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " landed on Community Chest and draws a card!");
        // Here you would implement the logic to draw a card and apply its effect
    }
}