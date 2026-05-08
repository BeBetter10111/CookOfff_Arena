public class PayRentAction implements ITileAction {
    @Override
    public void onLand(Player player, GameContext context) {
        // Logic for paying rent
        System.out.println(player.getName() + " has landed on a property tile and needs to pay rent.");
        // Implement rent payment logic here
    }
}