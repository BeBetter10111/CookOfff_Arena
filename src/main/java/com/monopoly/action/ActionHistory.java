package main.java.com.monopoly.action;
import java.util.ArrayList;
import java.util.List;

public class ActionHistory {
    private final List<String> actions;

    public ActionHistory() {
        this.actions = new ArrayList<>();
    }

    public void recordAction(String action) {
        actions.add(action);
    }

    public List<String> getActions() {
        return new ArrayList<>(actions);
    }

    public void clearHistory() {
        actions.clear();
    }
}