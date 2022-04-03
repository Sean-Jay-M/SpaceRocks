package com.spacerocks;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
// This class is responsible for handling the GUI elements of the game
public class Screen {
    // We may decide that these can be modifiable - to be discussed
    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 500;
    // I added "final" to these because IntelliJ suggested it. If we ended up
    // using more JavaFX elements we can remove this.
    private final Stage gameStage;

    public Pane getPane() {
        return pane;
    }

    //Pane Creation
    private Pane pane;
    private Scene scene;

    private UI ui;
    private Spawner spawner;
    //Getter for the screen Width
    public static int getScreenWidth(){
        return SCREEN_WIDTH;
    }
    //Getter for the screen Height
    public static int getScreenHeight(){
        return SCREEN_HEIGHT;
    }

    public Scene getScene() { return scene; }

    public Screen(Stage gameStage) {
        // Constructor creates new instances of these objects. The reason why we need
        // the Stage as an argument is because the Main class automatically creates a
        // Stage as part of the JavaFX "Start" method, so to avoid doubling up we can
        // just use the Stage that that method created.
        this.gameStage = gameStage;

        pane = new Pane();
        pane.setStyle("-fx-background-color: transparent ;");
        scene = new Scene(pane);
        ui = new UI(pane, this);
        gameStage.setTitle("SpaceRocks");
        setDefaultScreenProperties();
        loadNewContent();


//        gamePane = new Pane();
//        gameScene = new Scene(gamePane);
//        ui = new UI(gamePane);
//        ui.initScoreUI();
//        spawner = new Spawner(gamePane);
    }

    //Getter for the spawner
    public Spawner getSpawner(){return spawner;}

    public UI getUI() { return ui; }

    public void setDefaultScreenProperties() {
        pane.setPrefSize(SCREEN_HEIGHT, SCREEN_WIDTH);
        scene.setFill(Color.BLACK);
    }

    public void setMenuScreen() {
        resetScreen();
        ui.initMenuUI();
    }

    public void setGameScreen() {
        resetScreen();
        ui.initScoreUI();
        Game game = new Game(gameStage,this);
        game.play();
    }

    public void resetScreen() {
        pane = new Pane();
        scene = new Scene(pane);
    }

    public void loadNewContent() {
        gameStage.setScene(scene);
        gameStage.show();
    }

    // Placeholder for drawing objects, this will change depending on how we
    // decide to implement this:
    public void drawGameObject(GameObject gameObject) {
        pane.getChildren().add(gameObject.getPolygon());
    }

    // remove object from the pane
    public void removeGameObject(GameObject gameObject){
        pane.getChildren().remove(gameObject.getPolygon());
    }

}