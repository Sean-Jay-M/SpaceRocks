package com.spacerocks;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// This class is responsible for handling the GUI elements of the game
public class Screen extends Application {

    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 500;

    // Main method for debugging. To be deleted once the Java project has been built
    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

    // Start method is the method that will launch the JavaFX application
    public void start(Stage mainStage) {

        // Set title of the game window
        mainStage.setTitle("SpaceRocks");

        // Creating pane and setting the preferred size
        Pane mainWindow = new Pane();
        mainWindow.setPrefSize(SCREEN_HEIGHT, SCREEN_WIDTH);

        // Creating scene and setting background color to black
        Scene mainScene = new Scene(mainWindow);
        mainScene.setFill(Color.BLACK);

        // Activating the scene on the stage when the application launches
        mainStage.setScene(mainScene);

        // Displaying the application
        mainStage.show();
    }

}
