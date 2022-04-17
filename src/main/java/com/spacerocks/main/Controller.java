package com.spacerocks.main;

import com.spacerocks.gameobjects.Bullet;
import com.spacerocks.gameobjects.Ship;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

public class Controller {

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    Ship ship;

    // Controls are run from the "Scene" object in JavaFX, so we will need to include this in this class.
    // replace scene by screen.
    Screen screen;

    ArrayList<String> pressedKeys;
    ArrayList<String> tempPressedKeys;

    // Added immutable strings to avoid typos.
    final String up = "UP";
    final String left = "LEFT";
    final String right = "RIGHT";
    final String space = "SPACE";
    final String shift = "SHIFT";


    public Controller(Ship ship, Screen screen) {
        this.ship = ship;
        this.screen = screen;
        this.pressedKeys = new ArrayList<>();
        this.tempPressedKeys = new ArrayList<>();
    }

    public void readUserInput(){
        initControls();
        readMovementKeys();
        readShootKey();
        readHyperspaceKey();
    }

    private void initControls() {
        // Sets up an event that will continuously read user input with the help of AnimationTimer.
        // source: https://www.youtube.com/watch?v=7Vb9StpxFtw&t=637s&ab_channel=LeeStemkoski
        // use arraylist rather than hashmap
        screen.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String key = keyEvent.getCode().toString();
                if (!pressedKeys.contains(key)){
                    pressedKeys.add(key);
                    tempPressedKeys.add(key);
                }
            }
        });
        screen.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String key = keyEvent.getCode().toString();
                pressedKeys.remove(key);
            }
        });
    }

    private void readShootKey() {
        if (tempPressedKeys.contains(space)) {
            ship.shoot();
        }
        // to avoid the continuous bullets
        tempPressedKeys.remove(space);
    }

    private void showBulletOnScreen() {
        // create new bullet
        Bullet bullet = new Bullet((int)ship.getCurrentXPosition(),(int)ship.getCurrentYPosition(), ship.getSpeed() + 2.0);
        bullet.getPolygon().setRotate(ship.getPolygon().getRotate());

        // add bullet to the arraylist in ship class
        ship.addBullet(bullet);
        // draw the bullet
        screen.getSpawner().spawnGameObject(bullet);
    }

    private void readMovementKeys() {
        if(this.pressedKeys.contains(left)) {
            ship.turn(ship.getTurnSpeedLeft());
        }

        if(this.pressedKeys.contains(right)) {
            ship.turn(ship.getTurnSpeedRight());
        }

        if (this.pressedKeys.contains(up)) {
            ship.accelerate();
        }

        ship.move();
        ship.moveBullets();
    }
    private void readHyperspaceKey(){
        if(tempPressedKeys.contains(shift)){
            ship.hyperspaceJump();
        }
        tempPressedKeys.remove(shift);
    }
}