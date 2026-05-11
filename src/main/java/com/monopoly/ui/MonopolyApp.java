import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.DropShadow;

public class MonopolyApp extends Application {

    private static final double SCREEN_W  = 1280;
    private static final double SCREEN_H  = 832;
    private static final double BTN_W     = 579;
    private static final double BTN_H     = 171;
    private static final double FIELD_W   = 1098;
    private static final double FIELD_H   = 71;

    private static final String BG_COLOR        = "#F5F0E8";
    private static final String BTN_COLOR       = "#B8A882";   // tan/khaki
    private static final String BTN_HOVER       = "#A09070";   // darker tan on hover
    private static final String TEXT_DARK       = "#5C4A1E";   // dark brown
    private static final String LABEL_COLOR     = "#7A6535";   // medium brown
    private static final String FIELD_BG        = "#FAF3EC";   // very light cream
    private static final String FIELD_BORDER    = "#D4C4A0";   // light tan border
    private static final String HEADER_BG       = "#FFFFFF";   // white header
    private static final String HEADER_BORDER   = "#CCCCCC";   // subtle border

    private Stage primaryStage;
    private Font pixelFont;

    private ImageView buildAvatar() {
        ImageView avatar = new ImageView();
        try {
            // Try to load from resources; fall back to placeholder circle
            Image img = new Image(getClass().getResourceAsStream("/avatar.png"), 60, 60, true, true);
            avatar.setImage(img);
        } catch (Exception e) {
            // No image found — draw a brown circle placeholder via CSS
        }
        avatar.setFitWidth(60);
        avatar.setFitHeight(60);
        avatar.setStyle(
            "-fx-background-color: " + BTN_COLOR + ";" +
            "-fx-background-radius: 30;"
        );
        javafx.scene.shape.Circle clip = new javafx.scene.shape.Circle(30, 30, 30);
        avatar.setClip(clip);
        return avatar;
    }

    // ── HEADER BAR ──────────────────────────────────────────────────────────────
    private HBox buildHeader() {
        Label title = new Label("MONOPOLY");
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        title.setTextFill(Color.web(TEXT_DARK));

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setStyle(
            "-fx-background-color: " + HEADER_BG + ";" +
            "-fx-border-color: " + HEADER_BORDER + ";" +
            "-fx-border-width: 0 0 1 0;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ImageView avatar = buildAvatar();

        header.getChildren().addAll(title, spacer, avatar);
        return header;
    }

    // PIXEL TITLE "MONOPOLY"
    private Label buildTitle() {
        Label lbl = new Label("MONOPOLY");
        lbl.setFont(Font.font("Courier New", FontWeight.BOLD, 120));
        lbl.setTextFill(Color.web(TEXT_DARK));

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#3A2D0A", 0.35));
        shadow.setOffsetX(4);
        shadow.setOffsetY(4);
        shadow.setRadius(0);
        lbl.setEffect(shadow);
        return lbl;
    }

    // STYLED BUTTON
    private Button buildButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(BTN_W);
        btn.setPrefHeight(BTN_H);
        btn.setFont(Font.font("Courier New", FontWeight.BOLD, 36));
        btn.setTextFill(Color.web(TEXT_DARK));
        btn.setStyle(styleButton(BTN_COLOR));

        btn.setOnMouseEntered(e -> btn.setStyle(styleButton(BTN_HOVER)));
        btn.setOnMouseExited(e  -> btn.setStyle(styleButton(BTN_COLOR)));
        btn.setOnMousePressed(e -> btn.setStyle(styleButton("#8A7850")));
        btn.setOnMouseReleased(e -> btn.setStyle(styleButton(BTN_HOVER)));
        return btn;
    }

    private String styleButton(String color) {
        return "-fx-background-color: " + color + ";" +
               "-fx-background-radius: 30;" +
               "-fx-border-radius: 30;" +
               "-fx-cursor: hand;";
    }

    //FIELD LABEL
    private Label buildFieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Courier New", FontWeight.BOLD, 22));
        lbl.setTextFill(Color.web(LABEL_COLOR));
        return lbl;
    }

    // TYLED TEXT FIELD
    private TextField buildTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(FIELD_W);
        tf.setPrefHeight(FIELD_H);
        tf.setFont(Font.font("Courier New", 20));
        tf.setStyle(
            "-fx-background-color: " + FIELD_BG + ";" +
            "-fx-border-color: " + FIELD_BORDER + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 0 16 0 16;"
        );
        return tf;
    }

    //  SCENE 1 — START SCREEN
    private Scene buildStartScene() {
        HBox header = buildHeader();
        Label title  = buildTitle();
        Button startBtn = buildButton("START GAME");

        startBtn.setOnAction(e -> primaryStage.setScene(buildLobbyScene()));

        VBox center = new VBox(60, title, startBtn);
        center.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(center);
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        return new Scene(root, SCREEN_W, SCREEN_H);
    }

    //  SCENE 2 — LOBBY (Create Room / Join Room)
    private Scene buildLobbyScene() {
        HBox header = buildHeader();
        Label title = buildTitle();

        Button createBtn = buildButton("CREATE ROOM");
        Button joinBtn   = buildButton("JOIN ROOM");

        createBtn.setOnAction(e -> primaryStage.setScene(buildCreateRoomScene()));
        joinBtn.setOnAction(e   -> primaryStage.setScene(buildJoinRoomScene()));

        VBox center = new VBox(40, title, createBtn, joinBtn);
        center.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(center);
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        return new Scene(root, SCREEN_W, SCREEN_H);
    }

    //  SCENE 3a — CREATE ROOM
    private Scene buildCreateRoomScene() {
        HBox header = buildHeader();
        Label title = buildTitle();

        // Room Name field
        Label roomNameLbl = buildFieldLabel("ROOM NAME");
        TextField roomNameField = buildTextField("...");

        // ID Room field
        Label idRoomLbl = buildFieldLabel("ID ROOM");
        TextField idRoomField = buildTextField("...");

        Button createBtn = buildButton("CREATE");
        createBtn.setPrefWidth(400);
        createBtn.setPrefHeight(100);
        createBtn.setFont(Font.font("Courier New", FontWeight.BOLD, 28));

        // Back button (subtle)
        Button backBtn = new Button("← BACK");
        backBtn.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        backBtn.setTextFill(Color.web(LABEL_COLOR));
        backBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        backBtn.setOnAction(e -> primaryStage.setScene(buildLobbyScene()));
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: transparent; -fx-underline: true; -fx-cursor: hand;"));
        backBtn.setOnMouseExited(e  -> backBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;"));

        VBox fieldBox = new VBox(12,
            roomNameLbl, roomNameField,
            new Region(),          // small gap
            idRoomLbl, idRoomField
        );
        fieldBox.setMaxWidth(FIELD_W);
        ((Region) fieldBox.getChildren().get(2)).setPrefHeight(10);

        VBox center = new VBox(30, title, fieldBox, createBtn, backBtn);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(20, 0, 40, 0));

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(center);
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        return new Scene(root, SCREEN_W, SCREEN_H);
    }

    //  SCENE 3b — JOIN ROOM
    private Scene buildJoinRoomScene() {
        HBox header = buildHeader();
        Label title = buildTitle();

        Label joinLbl = buildFieldLabel("JOIN");
        TextField joinField = buildTextField("...");
        joinField.setPrefWidth(FIELD_W);

        Button joinBtn = buildButton("JOIN");
        joinBtn.setPrefWidth(400);
        joinBtn.setPrefHeight(100);
        joinBtn.setFont(Font.font("Courier New", FontWeight.BOLD, 28));

        // Back button
        Button backBtn = new Button("← BACK");
        backBtn.setFont(Font.font("Courier New", FontWeight.BOLD, 18));
        backBtn.setTextFill(Color.web(LABEL_COLOR));
        backBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        backBtn.setOnAction(e -> primaryStage.setScene(buildLobbyScene()));
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: transparent; -fx-underline: true; -fx-cursor: hand;"));
        backBtn.setOnMouseExited(e  -> backBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;"));

        VBox fieldBox = new VBox(12, joinLbl, joinField);
        fieldBox.setMaxWidth(FIELD_W);

        VBox center = new VBox(40, title, fieldBox, joinBtn, backBtn);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(20, 0, 40, 0));

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(center);
        root.setStyle("-fx-background-color: " + BG_COLOR + ";");

        return new Scene(root, SCREEN_W, SCREEN_H);
    }

    //  ENTRY POINT
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Monopoly");
        stage.setResizable(false);
        stage.setScene(buildStartScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
