package com.monopoly.lobby.ui.view;

import com.monopoly.lobby.controller.SceneManager;
import com.monopoly.lobby.model.GameRoom;
import com.monopoly.lobby.model.Player;
import com.monopoly.lobby.model.RoomRegistry;
import com.monopoly.lobby.ui.factory.ComponentFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static com.monopoly.lobby.ui.factory.ComponentFactory.*;
import static com.monopoly.lobby.ui.theme.UIConstants.*;

public class GameSetupView {

    private final String roomId;
    private final boolean isHost;

    public GameSetupView(String roomId, boolean isHost) {
        this.roomId = roomId;
        this.isHost = isHost;
    }

    public Scene build() {
        GameRoom room = RoomRegistry.getInstance().findRoom(roomId);
        if (room == null) {
            SceneManager.getInstance().switchScene(new LobbyView().build());
            return new Scene(new BorderPane(), SCREEN_W, SCREEN_H);
        }

        Label titleLbl = new Label("Game set up");
        titleLbl.setFont(Font.font("Courier New", FontWeight.BOLD, 52));
        titleLbl.setTextFill(Color.web(TEXT_DARK));
        DropShadow sd = new DropShadow();
        sd.setColor(Color.web("#3A2D0A", 0.3));
        sd.setOffsetX(3);
        sd.setOffsetY(3);
        sd.setRadius(0);
        titleLbl.setEffect(sd);

        Label capLabel = buildLabel("Initial Capital", 24, TEXT_DARK);
        Label capValue = buildLabel("$" + room.getInitialCapital(), 32, GREEN_CAPITAL);
        HBox capRow = new HBox(16, capLabel, capValue);
        capRow.setAlignment(Pos.CENTER_LEFT);

        Label nameLbl = buildLabel("NAME", 22, LABEL_COLOR);
        TextField nameFld = ComponentFactory.buildTextField(FIELD_NAME_W, FIELD_NAME_H);
        nameFld.setPromptText("Enter your name…");

        Label tokenLbl = buildLabel("Token", 24, LABEL_COLOR);
        final int[] selectedToken = {-1};
        Button[] tBtns = new Button[TOKENS.length];
        HBox tokenRow = new HBox(16);
        tokenRow.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < TOKENS.length; i++) {
            final int idx = i;
            Button tb = buildTokenButton(TOKENS[i]);
            tb.setOnAction(e -> {
                selectedToken[0] = idx;
                for (Button b : tBtns) setTokenSelected(b, false);
                setTokenSelected(tb, true);
            });
            tBtns[i] = tb;
            tokenRow.getChildren().add(tb);
        }

        Label statusLbl = buildLabel("", 18, "#C0392B");

        Button joinBtn = buildActionButton("JOIN", BTN_CREATE_W + 60, BTN_CREATE_H);
        joinBtn.setOnAction(e -> {
            String name = nameFld.getText().trim();
            if (name.isEmpty()) {
                statusLbl.setText("Please enter your name.");
                return;
            }
            if (selectedToken[0] < 0) {
                statusLbl.setText("Please choose a token.");
                return;
            }
            boolean tokenUsed = room.getPlayers().stream()
                .anyMatch(p -> p.getToken().equals(TOKENS[selectedToken[0]]));
            if (tokenUsed) {
                statusLbl.setText("Token already selected in this room.");
                return;
            }

            int seatNumber = room.getPlayerCount() + 1;
            Player player = new Player(name, TOKENS[selectedToken[0]], seatNumber);

            boolean added = room.addPlayer(player);
            if (!added) {
                statusLbl.setText("Room has enough human players.");
                return;
            }

            SceneManager.getInstance().switchScene(new WaitingRoomView(roomId).build());
        });

        Button backBtn = buildBackButton(() -> {
            Scene prev = isHost ? new RoomSettingsView().build() : new JoinRoomView().build();
            SceneManager.getInstance().switchScene(prev);
        });

        Region g1 = new Region();
        g1.setPrefHeight(6);
        Region g2 = new Region();
        g2.setPrefHeight(6);

        VBox content = new VBox(16, titleLbl, capRow, nameLbl, nameFld, g1, tokenLbl, tokenRow, g2, statusLbl, joinBtn, backBtn);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(36, 60, 36, 60));
        content.setMaxWidth(FIELD_NAME_W + 120);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color:" + BG_COLOR + ";-fx-background:" + BG_COLOR + ";");

        BorderPane root = new BorderPane();
        root.setTop(buildHeader());
        root.setCenter(scroll);
        root.setStyle("-fx-background-color:" + BG_COLOR + ";");

        return new Scene(root, SCREEN_W, SCREEN_H);
    }
}
