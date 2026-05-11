package com.monopoly.lobby.ui.view;

import com.monopoly.lobby.controller.SceneManager;
import com.monopoly.lobby.model.GameRoom;
import com.monopoly.lobby.model.Player;
import com.monopoly.lobby.model.RoomRegistry;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.List;

import static com.monopoly.lobby.ui.factory.ComponentFactory.*;
import static com.monopoly.lobby.ui.theme.UIConstants.*;

public class WaitingRoomView {

    private final String roomId;

    public WaitingRoomView(String roomId) {
        this.roomId = roomId;
    }

    public Scene build() {
        GameRoom room = RoomRegistry.getInstance().findRoom(roomId);
        if (room == null) {
            SceneManager.getInstance().switchScene(new LobbyView().build());
            return new Scene(new BorderPane(), SCREEN_W, SCREEN_H);
        }

        Label roomNameLbl = new Label(room.getRoomName().toUpperCase());
        roomNameLbl.setFont(Font.font("Courier New", FontWeight.BOLD, 42));
        roomNameLbl.setTextFill(Color.web(TEXT_DARK));

        Label capLbl = buildLabel("Initial Capital", 22, TEXT_DARK);
        Label capVal = buildLabel("$" + room.getInitialCapital(), 26, GREEN_CAPITAL);
        HBox capRow = new HBox(12, capLbl, capVal);
        capRow.setAlignment(Pos.CENTER_LEFT);

        GridPane grid = buildPlayerGrid(room);

        Button exitBtn = buildActionButton("EXIT", BTN_CREATE_W, BTN_CREATE_H);
        exitBtn.setOnAction(e -> SceneManager.getInstance().switchScene(new StartView().build()));

        VBox center = new VBox(24, roomNameLbl, capRow, grid, exitBtn);
        center.setAlignment(Pos.CENTER_LEFT);
        center.setPadding(new Insets(36, 80, 36, 80));

        BorderPane root = new BorderPane();
        root.setTop(buildHeader());
        root.setCenter(center);
        root.setStyle("-fx-background-color:" + BG_COLOR + ";");

        Scene scene = new Scene(root, SCREEN_W, SCREEN_H);

        Timeline poller = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            GridPane updated = buildPlayerGrid(room);
            center.getChildren().set(2, updated);
        }));
        poller.setCycleCount(Timeline.INDEFINITE);
        poller.play();

        scene.windowProperty().addListener((obs, old, win) -> {
            if (win == null) poller.stop();
        });

        return scene;
    }

    private GridPane buildPlayerGrid(GameRoom room) {
        GridPane grid = new GridPane();
        grid.setHgap(60);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER_LEFT);

        List<Player> players = room.getPlayers();
        int maxPlayers = room.getMaxPlayers();

        for (int seat = 1; seat <= maxPlayers; seat++) {
            final int s = seat;
            Player p = players.stream().filter(pl -> pl.getPlayerIndex() == s).findFirst().orElse(null);
            VBox card = buildPlayerCard(seat, p);

            int col = (seat - 1) % 2;
            int row = (seat - 1) / 2;
            grid.add(card, col, row);
        }
        return grid;
    }

    private VBox buildPlayerCard(int seat, Player player) {
        Label seatLbl = buildLabel("Player " + seat, 20, TEXT_DARK);

        Label tokenLbl = new Label(player != null ? player.getToken() : "?");
        tokenLbl.setFont(Font.font(36));
        tokenLbl.setStyle("-fx-background-color:" + FIELD_BG + ";-fx-border-color:" + FIELD_BORDER
            + ";-fx-border-width:2;-fx-border-radius:12;-fx-background-radius:12;-fx-padding:8 14 8 14;");

        Label nameLbl = (player != null)
            ? buildLabel(player.getName(), 16, LABEL_COLOR)
            : buildLabel("Waiting…", 16, "#AAAAAA");

        VBox card = new VBox(6, seatLbl, tokenLbl, nameLbl);
        card.setAlignment(Pos.CENTER_LEFT);
        return card;
    }
}
