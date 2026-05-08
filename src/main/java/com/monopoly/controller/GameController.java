public class GameController {
    private List<Player> players;
    private Bank bank;
    private ActionHistory actionHistory;

    public GameController(List<Player> players, Bank bank) {
        this.players = players;
        this.bank = bank;
        this.actionHistory = new ActionHistory();
    }

    public void startGame() {
        // Logic to start the game
        System.out.println("Game started with " + players.size() + " players.");
    }

    public void endGame() {
        // Logic to end the game
        System.out.println("Game ended.");
    }

    public void takeTurn(Player player, int dice1, int dice2) {
        // Logic for a player taking a turn
        player.takeTurn(dice1, dice2);
        actionHistory.recordAction(player.getName() + " took a turn with dice: " + dice1 + ", " + dice2);
    }
}