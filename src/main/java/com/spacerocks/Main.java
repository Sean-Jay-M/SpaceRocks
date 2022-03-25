package com.spacerocks;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    // Main method for debugging. To be deleted once the Java project has been built
    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

    // Start method is the method that will launch the JavaFX application
    public void start(Stage gameStage) {

        // Create the screen object and generate the main window
        Screen screen = new Screen(gameStage);
        screen.createMainWindow();
        Ship ship = new Ship(1,screen.getScreenWidth()/2,screen.getScreenHeight()/2);
        screen.drawGameObject(ship);

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                // movement test
//                ship.move();
//                ship.turn(5);
//                ship.turn(-5);
            }
        }.start();
    }
}
