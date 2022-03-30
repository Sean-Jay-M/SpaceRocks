package com.spacerocks;

import javafx.scene.Scene;

public class Controller {

    // I have decided to keep the object to be controlled broad (i.e. not just ship). We may wish to introduce another
    // controllable object later to which these controls might apply.
    Ship ship;

    // Controls are run from the "Scene" object in JavaFX, so we will need to include this in this class.
    Scene gameScene;

    public Controller(Ship ship, Scene gameScene) {
        this.ship = ship;
        this.gameScene = gameScene;
    }

    public void initControls() {
        // Sets up an event that will continuously read user input with the help of AnimationTimer.
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    // This is where the gameObject.thrust() will go
                    // if UP is pressed, set thrusting true
                    ship.setThrusting(true);
                    break;
                case LEFT:
                    ship.turn(-10);
                    break;
                case RIGHT:
                    ship.turn(10);
                    break;
            }
        });

        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    // if UP is released, set thrusting false
                    ship.setThrusting(false);
                    break;
            }
        });
    }
}