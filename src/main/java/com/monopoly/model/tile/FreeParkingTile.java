public class FreeParkingTile extends Title {
    public FreeParkingTile(String name, int position) {
        super(name, position, false);
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " landed on Free Parking. Take a break!");
    }
}