public class MortgageAction implements ITileAction {
    @Override
    public void onLand(Player player, GameContext context) {
        // Logic for mortgaging a property
        System.out.println(player.getName() + " has landed on a mortgage tile.");
        // Implement mortgage logic here
    }
}