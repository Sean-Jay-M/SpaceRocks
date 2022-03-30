package com.spacerocks;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    // I have decided to keep the object to be controlled broad (i.e. not just ship). We may wish to introduce another
    // controllable object later to which these controls might apply.
    Ship ship;

    // Controls are run from the "Scene" object in JavaFX, so we will need to include this in this class.
    Scene gameScene;
    Map<KeyCode, Boolean> pressedKeys;

    public Controller(Ship ship, Scene gameScene) {
        this.ship = ship;
        this.gameScene = gameScene;
        this.pressedKeys = new HashMap<>();
    }

    public void initControls() {
        // Sets up an event that will continuously read user input with the help of AnimationTimer.
        // source: https://programming-f20.mooc.fi/part-14/3-larger-application-asteroids
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                pressedKeys.put(keyEvent.getCode(), Boolean.TRUE);
            }
        });
        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                pressedKeys.put(keyEvent.getCode(), Boolean.FALSE);
            }
        });
    }

    public void control(){
        if(this.pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
            ship.turn(-10);
        }

        if(this.pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
            ship.turn(10);
        }

        if(this.pressedKeys.getOrDefault(KeyCode.UP, false)) {
            ship.setThrusting(true);
        }else{
            ship.setThrusting(false);
        }
    }
}