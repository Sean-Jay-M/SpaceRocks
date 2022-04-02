package com.spacerocks;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class Game {
    // Create the screen object and generate the main window
    Screen screen;
    Ship ship;
    // Creating the ship controller, passing in the Ship that we have created and the Scene property of the Screen.
    Controller shipController;

    public Game(Stage gameStage) {
        this.screen = new Screen(gameStage);
        this.ship = new Ship(Screen.getScreenWidth()/2.0,Screen.getScreenHeight()/2.0);
        this.shipController = new Controller(ship, screen);

        this.screen.createMainWindow();
        this.screen.getSpawner().drawGameObject(ship);
    }

    public void play(){
        // Set add listener to spawner to check if bullets need to be deleted
        ship.setDespawnListener(screen.getSpawner());
        // Starting new animation timer and setting up the controls inside to read user input continuously.
        new AnimationTimer() {
            @Override
            public void handle(long l) {

                // Read keyboard keys from the user.
                shipController.readUserInput();

                // default speed of ship is 0, so the ship is moving all the time.
                ship.move();
                ship.thrust();
                ship.shoot();

            }
        }.start();
    }
}