package com.monopoly.lobby.app;

import com.monopoly.lobby.controller.SceneManager;
import com.monopoly.lobby.ui.view.StartView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MonopolyApp extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.getInstance().init(stage);
        SceneManager.getInstance().switchScene(new StartView().build());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
