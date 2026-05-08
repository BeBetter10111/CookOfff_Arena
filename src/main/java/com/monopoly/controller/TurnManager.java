public class TurnManager {
    private int currentPlayerIndex;
    private List<Player> players;
    private ActionHistory actionHistory;

    public TurnManager(List<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.actionHistory = new ActionHistory();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void endTurn() {
        actionHistory.recordAction(getCurrentPlayer().getName() + " ended their turn.");
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        actionHistory.recordAction(getCurrentPlayer().getName() + " starts their turn.");
    }

    public ActionHistory getActionHistory() {
        return actionHistory;
    }
}