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
        Ship ship = new Ship(screen.getScreenWidth()/2,screen.getScreenHeight()/2);

        // Creating the ship controller, passing in the Ship that we have created and the Scene property of the Screen.
        Controller shipController = new Controller(ship, screen.getScene());

        screen.createMainWindow();
        screen.drawGameObject(ship);

        // Starting new animation timer and setting up the controls inside to read user input continuously.
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                shipController.initControls();
                shipController.control();
                // default speed of ship is 0, so the ship is moving all the time.
                ship.move();

                // if the ship is thrusting, the ship will accelerate. otherwise, it will slow down.
                if (ship.isThrusting()){
                    ship.accelerate();
                } else{
                    ship.slowDown();
                }
            }
        }.start();
    }
}
