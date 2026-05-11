package com.monopoly.lobby.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneManager {

    private static SceneManager instance;
    private Stage primaryStage;

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) instance = new SceneManager();
        return instance;
    }

    public void init(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Monopoly");
    }

    public void switchScene(Scene scene) {
        primaryStage.setScene(scene);
        if (!primaryStage.isShowing()) primaryStage.show();
    }
}
