package com.monopoly.lobby.ui.view;

import com.monopoly.lobby.controller.SceneManager;
import com.monopoly.lobby.model.GameRoom;
import com.monopoly.lobby.model.RoomRegistry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.monopoly.lobby.ui.factory.ComponentFactory.*;
import static com.monopoly.lobby.ui.theme.UIConstants.*;

public class RoomSettingsView {

    private final String[] selDiff = {null};
    private final int[] selCount = {0};
    private final String[] selMode = {null};

    public Scene build() {
        Button easyBtn = buildDiffButton("Easy");
        Button hardBtn = buildDiffButton("Hard");

        Button[] numBtns = {buildNumberButton("1"), buildNumberButton("2"), buildNumberButton("3"), buildNumberButton("4")};

        Button aggBtn = buildBotModeButton("Aggressive");
        Button conBtn = buildBotModeButton("Conservative");
        HBox modeRow = new HBox(16, aggBtn, conBtn);
        modeRow.setAlignment(Pos.CENTER_LEFT);

        Button createBtn = buildActionButton("CREATE", BTN_CREATE_W, BTN_CREATE_H);
        createBtn.setDisable(true);
        createBtn.setOpacity(0.5);

        Runnable validate = () -> {
            boolean modeNeeded = selCount[0] > 0 && selCount[0] < 4;
            boolean ok = selDiff[0] != null && selCount[0] > 0 && (!modeNeeded || selMode[0] != null);
            createBtn.setDisable(!ok);
            createBtn.setOpacity(ok ? 1.0 : 0.5);
        };

        easyBtn.setOnAction(e -> {
            selDiff[0] = "Easy";
            setDiffSelected(easyBtn, true);
            setDiffSelected(hardBtn, false);
            validate.run();
        });
        hardBtn.setOnAction(e -> {
            selDiff[0] = "Hard";
            setDiffSelected(hardBtn, true);
            setDiffSelected(easyBtn, false);
            validate.run();
        });

        for (int i = 0; i < 4; i++) {
            final int n = i + 1;
            final Button b = numBtns[i];
            b.setOnAction(e -> {
                selCount[0] = n;
                for (Button nb : numBtns) setNumberSelected(nb, false);
                setNumberSelected(b, true);

                boolean show = n < 4;
                modeRow.setVisible(show);
                modeRow.setManaged(show);
                if (!show) {
                    selMode[0] = null;
                    setBotModeSelected(aggBtn, false);
                    setBotModeSelected(conBtn, false);
                }
                validate.run();
            });
        }

        aggBtn.setOnAction(e -> {
            selMode[0] = "Aggressive";
            setBotModeSelected(aggBtn, true);
            setBotModeSelected(conBtn, false);
            validate.run();
        });
        conBtn.setOnAction(e -> {
            selMode[0] = "Conservative";
            setBotModeSelected(conBtn, true);
            setBotModeSelected(aggBtn, false);
            validate.run();
        });

        createBtn.setOnAction(e -> {
            GameRoom.Difficulty diff = "Easy".equals(selDiff[0]) ? GameRoom.Difficulty.EASY : GameRoom.Difficulty.HARD;
            GameRoom.BotMode mode = GameRoom.BotMode.NONE;
            if ("Aggressive".equals(selMode[0])) mode = GameRoom.BotMode.AGGRESSIVE;
            if ("Conservative".equals(selMode[0])) mode = GameRoom.BotMode.CONSERVATIVE;

            String roomId = RoomRegistry.getInstance().createRoom("Monopoly Room", diff, selCount[0], mode);
            SceneManager.getInstance().switchScene(new GameSetupView(roomId, true).build());
        });

        HBox diffRow = new HBox(40, easyBtn, hardBtn);
        diffRow.setAlignment(Pos.CENTER);

        HBox numBtnRow = new HBox(16, numBtns[0], numBtns[1], numBtns[2], numBtns[3]);
        numBtnRow.setAlignment(Pos.CENTER_LEFT);

        HBox numLine = new HBox(24, buildLabel("Real players (total = 4)", 22, TEXT_DARK), numBtnRow);
        numLine.setAlignment(Pos.CENTER_LEFT);

        HBox modeLine = new HBox(24, buildLabel("Bot mode", 22, TEXT_DARK), modeRow);
        modeLine.setAlignment(Pos.CENTER_LEFT);

        VBox formBox = new VBox(20, numLine, modeLine);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setMaxWidth(720);

        Button backBtn = buildBackButton(() -> SceneManager.getInstance().switchScene(new LobbyView().build()));
        VBox center = new VBox(26, buildSmallTitle(), diffRow, formBox, createBtn, backBtn);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(30, 60, 40, 60));

        BorderPane root = new BorderPane();
        root.setTop(buildHeader());
        root.setCenter(center);
        root.setStyle("-fx-background-color:" + BG_COLOR + ";");

        return new Scene(root, SCREEN_W, SCREEN_H);
    }
}
