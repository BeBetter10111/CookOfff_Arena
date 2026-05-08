public class ChanceTile extends Title {
    public ChanceTile(String name, int position) {
        super(name, position, false);
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " landed on Chance and draws a card!");
        // Logic to draw a Chance card and execute its effect
    }
}