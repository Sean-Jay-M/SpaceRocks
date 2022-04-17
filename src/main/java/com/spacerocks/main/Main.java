// Class is part of main package
package com.spacerocks.main;

// Importing necessary JavaFX elements
import javafx.application.Application;
import javafx.stage.Stage;

// This class is responsible for starting the application.
public class Main extends Application {

    // Main method for debugging. This will be deleted if this game were ever to go into production.
    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

    // Start method is the method that will launch the JavaFX application.
    // When starting the application, we should create the screen, initialise the default
    // screen, and start with the main menu.
    public void start(Stage gameStage) {
        Screen screen = Screen.getScreenInstance();
        screen.initScreen(gameStage);
        screen.setMenuScreen();
    }
}
