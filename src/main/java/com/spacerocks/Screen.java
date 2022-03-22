package com.spacerocks;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// This class is responsible for handling the GUI elements of the game
public class Screen {

    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 500;

    // I added "final" to these because IntelliJ suggested it. If we ended up
    // using more JavaFX elements we can remove this.
    private final Stage gameStage;
    private final Pane gamePane;
    private final Scene gameScene;

    public Screen(Stage gameStage) {
        // Constructor creates new instances of these objects. The reason why we need
        // the Stage as an argument is because the Main class automatically creates a
        // Stage as part of the JavaFX "Start" method, so to avoid doubling up we can
        // just use the Stage that that method created.
        this.gameStage = gameStage;
        gamePane = new Pane();
        gameScene = new Scene(gamePane);
    }

    public void createMainWindow() {
        // Set title of the game window
        gameStage.setTitle("SpaceRocks");

        // Setting the size of the game pane
        gamePane.setPrefSize(SCREEN_HEIGHT, SCREEN_WIDTH);

        // Setting background color to black
        gameScene.setFill(Color.BLACK);

        // Activating the scene on the stage when the application launches
        gameStage.setScene(gameScene);

        // Displaying the application
        gameStage.show();
    }

    // Placeholder for drawing objects, this will change depending on how we
    // decide to implement this:

    // public void drawGameObject(GameObject gameObject) {
    //     gameWindow.getChildren().add(gameObject);
    // }
}
