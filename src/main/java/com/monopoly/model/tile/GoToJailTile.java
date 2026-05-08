public class GoToJailTile extends Title {
    public GoToJailTile(String name, int position) {
        super(name, position, false);
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " landed on Go To Jail and is sent to Jail!");
        player.setPosition(context.getBoard().getJailPosition());
        player.setInJail(true);
    }
}