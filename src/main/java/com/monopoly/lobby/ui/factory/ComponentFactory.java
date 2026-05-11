package com.monopoly.lobby.ui.factory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static com.monopoly.lobby.ui.theme.UIConstants.*;

public final class ComponentFactory {

    private ComponentFactory() {}

    public static ImageView buildAvatar() {
        ImageView av = new ImageView();
        try {
            av.setImage(new Image(ComponentFactory.class.getResourceAsStream("/avatar.png"), 60, 60, true, true));
        } catch (Exception ignored) {}
        av.setFitWidth(60);
        av.setFitHeight(60);
        av.setClip(new Circle(30, 30, 30));
        return av;
    }

    public static HBox buildHeader() {
        Label lbl = new Label("MONOPOLY");
        lbl.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        lbl.setTextFill(Color.web(TEXT_DARK));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox h = new HBox(lbl, spacer, buildAvatar());
        h.setAlignment(Pos.CENTER_LEFT);
        h.setPadding(new Insets(10, 20, 10, 20));
        h.setStyle("-fx-background-color:" + HEADER_BG + ";-fx-border-color:" + HEADER_BORDER + ";-fx-border-width:0 0 1 0;");
        return h;
    }

    public static Label buildMainTitle() {
        Label lbl = new Label("MONOPOLY");
        lbl.setFont(Font.font("Courier New", FontWeight.BOLD, 110));
        lbl.setTextFill(Color.web(TEXT_DARK));
        lbl.setEffect(dropShadow(0.35, 4, 4));
        return lbl;
    }

    public static Label buildSmallTitle() {
        Label lbl = new Label("MONOPOLY");
        lbl.setFont(Font.font("Courier New", FontWeight.BOLD, 80));
        lbl.setTextFill(Color.web(TEXT_DARK));
        lbl.setEffect(dropShadow(0.30, 3, 3));
        return lbl;
    }

    public static Label buildLabel(String text, double size, String color) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Courier New", FontWeight.BOLD, size));
        lbl.setTextFill(Color.web(color));
        return lbl;
    }

    public static TextField buildTextField(double w, double h) {
        TextField tf = new TextField();
        tf.setPromptText("...");
        tf.setPrefWidth(w);
        tf.setPrefHeight(h);
        tf.setFont(Font.font("Courier New", 20));
        tf.setStyle("-fx-background-color:" + FIELD_BG + ";-fx-border-color:" + FIELD_BORDER
            + ";-fx-border-radius:8;-fx-background-radius:8;-fx-padding:0 16 0 16;");
        return tf;
    }

    public static Button buildNavButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(BTN_W);
        btn.setPrefHeight(BTN_H);
        btn.setFont(Font.font("Courier New", FontWeight.BOLD, 36));
        btn.setTextFill(Color.web(TEXT_DARK));
        applyFill(btn, BTN_COLOR, 30);
        addHoverEffect(btn, BTN_COLOR, BTN_HOVER, BTN_PRESSED, 30);
        return btn;
    }

    public static Button buildActionButton(String text, double w, double h) {
        Button btn = new Button(text);
        btn.setPrefWidth(w);
        btn.setPrefHeight(h);
        btn.setFont(Font.font("Courier New", FontWeight.BOLD, 22));
        btn.setTextFill(Color.web(TEXT_DARK));
        applyFill(btn, BTN_COLOR, 14);
        addHoverEffect(btn, BTN_COLOR, BTN_HOVER, BTN_PRESSED, 14);
        return btn;
    }

    public static Button buildDiffButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(BTN_EASY_W);
        btn.setPrefHeight(BTN_EASY_H);
        btn.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        btn.setTextFill(Color.web(TEXT_DARK));
        setDiffSelected(btn, false);
        return btn;
    }

    public static void setDiffSelected(Button btn, boolean selected) {
        applyFill(btn, selected ? BTN_SELECTED : BTN_COLOR, 18);
        btn.setUserData(selected);
        if (!selected) {
            btn.setOnMouseEntered(e -> { if (!isSelected(btn)) applyFill(btn, BTN_HOVER, 18); });
            btn.setOnMouseExited(e -> { if (!isSelected(btn)) applyFill(btn, BTN_COLOR, 18); });
        }
    }

    public static Button buildNumberButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(52);
        btn.setPrefHeight(52);
        btn.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        btn.setTextFill(Color.web(TEXT_DARK));
        setNumberSelected(btn, false);
        return btn;
    }

    public static void setNumberSelected(Button btn, boolean selected) {
        applyOutline(btn, selected ? BTN_COLOR : "transparent", selected ? BTN_COLOR : TEXT_DARK, 10);
        btn.setUserData(selected);
    }

    public static Button buildBotModeButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(BTN_BOT_W);
        btn.setPrefHeight(BTN_BOT_H);
        btn.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        btn.setTextFill(Color.web(TEXT_DARK));
        setBotModeSelected(btn, false);
        btn.setOnMouseEntered(e -> { if (!isSelected(btn)) applyFill(btn, BTN_HOVER, 12); });
        btn.setOnMouseExited(e -> { if (!isSelected(btn)) setBotModeSelected(btn, false); });
        return btn;
    }

    public static void setBotModeSelected(Button btn, boolean selected) {
        if (selected) applyFill(btn, BTN_COLOR, 12);
        else applyOutline(btn, "transparent", TEXT_DARK, 12);
        btn.setUserData(selected);
    }

    public static Button buildTokenButton(String emoji) {
        Button btn = new Button(emoji);
        btn.setPrefWidth(72);
        btn.setPrefHeight(72);
        btn.setFont(Font.font(32));
        setTokenSelected(btn, false);
        return btn;
    }

    public static void setTokenSelected(Button btn, boolean selected) {
        btn.setStyle("-fx-background-color:" + (selected ? "#E8DFC0" : FIELD_BG) + ";"
            + "-fx-border-color:" + (selected ? BTN_SELECTED : FIELD_BORDER) + ";"
            + "-fx-border-width:" + (selected ? 3 : 2) + ";"
            + "-fx-border-radius:12;-fx-background-radius:12;-fx-cursor:hand;");
    }

    public static Button buildBackButton(Runnable onBack) {
        Button btn = new Button("← BACK");
        btn.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        btn.setTextFill(Color.web(LABEL_COLOR));
        String base = "-fx-background-color:transparent;-fx-cursor:hand;";
        btn.setStyle(base);
        btn.setOnAction(e -> onBack.run());
        btn.setOnMouseEntered(e -> btn.setStyle(base + "-fx-underline:true;"));
        btn.setOnMouseExited(e -> btn.setStyle(base));
        return btn;
    }

    public static void applyFill(Button btn, String color, int radius) {
        btn.setStyle("-fx-background-color:" + color + ";-fx-background-radius:" + radius
            + ";-fx-border-radius:" + radius + ";-fx-cursor:hand;");
    }

    public static void applyOutline(Button btn, String bg, String border, int radius) {
        btn.setStyle("-fx-background-color:" + bg + ";-fx-border-color:" + border
            + ";-fx-background-radius:" + radius + ";-fx-border-radius:" + radius + ";-fx-cursor:hand;");
    }

    public static boolean isSelected(Button btn) {
        return btn.getUserData() instanceof Boolean && (Boolean) btn.getUserData();
    }

    private static void addHoverEffect(Button btn, String normal, String hover, String pressed, int radius) {
        btn.setOnMouseEntered(e -> { if (!btn.isDisabled()) applyFill(btn, hover, radius); });
        btn.setOnMouseExited(e -> { if (!btn.isDisabled()) applyFill(btn, normal, radius); });
        btn.setOnMousePressed(e -> applyFill(btn, pressed, radius));
        btn.setOnMouseReleased(e -> applyFill(btn, hover, radius));
    }

    private static DropShadow dropShadow(double opacity, double ox, double oy) {
        DropShadow ds = new DropShadow();
        ds.setColor(Color.web("#3A2D0A", opacity));
        ds.setOffsetX(ox);
        ds.setOffsetY(oy);
        ds.setRadius(0);
        return ds;
    }
}
