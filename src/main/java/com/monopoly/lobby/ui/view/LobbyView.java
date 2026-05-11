package com.monopoly.lobby.ui.view;

import com.monopoly.lobby.controller.SceneManager;
import com.monopoly.lobby.ui.factory.ComponentFactory;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import static com.monopoly.lobby.ui.theme.UIConstants.BG_COLOR;
import static com.monopoly.lobby.ui.theme.UIConstants.SCREEN_H;
import static com.monopoly.lobby.ui.theme.UIConstants.SCREEN_W;

public class LobbyView {

    public Scene build() {
        Button createBtn = ComponentFactory.buildNavButton("CREATE ROOM");
        Button joinBtn = ComponentFactory.buildNavButton("JOIN ROOM");

        createBtn.setOnAction(e -> SceneManager.getInstance().switchScene(new RoomSettingsView().build()));
        joinBtn.setOnAction(e -> SceneManager.getInstance().switchScene(new JoinRoomView().build()));

        VBox center = new VBox(40, ComponentFactory.buildMainTitle(), createBtn, joinBtn);
        center.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(ComponentFactory.buildHeader());
        root.setCenter(center);
        root.setStyle("-fx-background-color:" + BG_COLOR + ";");

        return new Scene(root, SCREEN_W, SCREEN_H);
    }
}
