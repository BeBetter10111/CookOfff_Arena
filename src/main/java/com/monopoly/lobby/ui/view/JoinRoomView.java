package com.monopoly.lobby.ui.view;

import com.monopoly.lobby.controller.SceneManager;
import com.monopoly.lobby.model.GameRoom;
import com.monopoly.lobby.model.RoomRegistry;
import com.monopoly.lobby.ui.factory.ComponentFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import static com.monopoly.lobby.ui.factory.ComponentFactory.*;
import static com.monopoly.lobby.ui.theme.UIConstants.*;

public class JoinRoomView {

    public Scene build() {
        Label joinLbl = buildLabel("JOIN", 22, LABEL_COLOR);
        TextField idField = ComponentFactory.buildTextField(FIELD_W, FIELD_H);
        idField.setPromptText("Enter Room ID…");

        Label statusLbl = buildLabel("", 18, "#C0392B");

        Button joinBtn = buildActionButton("JOIN", BTN_CREATE_W, BTN_CREATE_H);
        joinBtn.setDisable(true);
        joinBtn.setOpacity(0.5);

        idField.textProperty().addListener((obs, old, text) -> {
            if (text.isBlank()) {
                joinBtn.setDisable(true);
                joinBtn.setOpacity(0.5);
                statusLbl.setText("");
                return;
            }
            GameRoom room = RoomRegistry.getInstance().findRoom(text.trim());
            if (room == null) {
                joinBtn.setDisable(true);
                joinBtn.setOpacity(0.5);
                statusLbl.setText("Room not found. Ask the host to create it first.");
                statusLbl.setStyle("-fx-text-fill:#C0392B;");
            } else if (!room.isOpen()) {
                joinBtn.setDisable(true);
                joinBtn.setOpacity(0.5);
                statusLbl.setText("Room is full.");
                statusLbl.setStyle("-fx-text-fill:#C0392B;");
            } else {
                joinBtn.setDisable(false);
                joinBtn.setOpacity(1.0);
                statusLbl.setText("Room found: " + room.getRoomName() + "  ✓");
                statusLbl.setStyle("-fx-text-fill:#2E7D32;");
            }
        });

        joinBtn.setOnAction(e -> {
            String roomId = idField.getText().trim();
            SceneManager.getInstance().switchScene(new GameSetupView(roomId, false).build());
        });

        Button backBtn = buildBackButton(() -> SceneManager.getInstance().switchScene(new LobbyView().build()));

        VBox fieldBox = new VBox(10, joinLbl, idField, statusLbl);
        fieldBox.setMaxWidth(FIELD_W);

        VBox center = new VBox(40, ComponentFactory.buildMainTitle(), fieldBox, joinBtn, backBtn);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(20, 0, 40, 0));

        BorderPane root = new BorderPane();
        root.setTop(buildHeader());
        root.setCenter(center);
        root.setStyle("-fx-background-color:" + BG_COLOR + ";");

        return new Scene(root, SCREEN_W, SCREEN_H);
    }
}
