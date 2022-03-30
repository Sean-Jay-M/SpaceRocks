package com.spacerocks;

import javafx.scene.Scene;

public class Controller {

    // I have decided to keep the object to be controlled broad (i.e. not just ship). We may wish to introduce another
    // controllable object later to which these controls might apply.
    GameObject gameObject;

    // Controls are run from the "Scene" object in JavaFX, so we will need to include this in this class.
    Scene gameScene;

    public Controller(GameObject gameObject, Scene gameScene) {
        this.gameObject = gameObject;
        this.gameScene = gameScene;
    }

    public void initControls() {
        // Sets up an event that will continuously read user input with the help of AnimationTimer.
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    // This is where the gameObject.thrust() will go
                    gameObject.move();
                    break;
                case LEFT:
                    gameObject.turn(-10);
                    break;
                case RIGHT:
                    gameObject.turn(10);
                    break;
            }
        });
    }
}
