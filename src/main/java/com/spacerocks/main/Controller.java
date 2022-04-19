// Class is part of main package
package com.spacerocks.main;

// Importing necessary local packages
import com.spacerocks.gameobjects.Ship;

// Importing necessary JavaFX elements
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

// This class will continuously read key inputs within the game and move the ship accordingly.
// This is done by setting up an event that will continuously read user input with the help of AnimationTimer.
// To avoid ragged animation, two ArrayLists are set up to which the keys are added and removed from, and the
// movement of the ship is done by reading from those ArrayLists.
// Source: https://www.youtube.com/watch?v=7Vb9StpxFtw&t=637s&ab_channel=LeeStemkoski.
public class Controller {

    // Initialising default variables.
    Ship ship;
    Screen screen = Screen.getScreenInstance();
    // set up consecutive keys and continuous keys
    // source: https://www.youtube.com/watch?v=7Vb9StpxFtw&t=637s&ab_channel=LeeStemkoski
    private final ArrayList<String> pressedKeys = new ArrayList<>();
    private final ArrayList<String> tempPressedKeys = new ArrayList<>();

    // Adding immutable string variables to ensure there are no typos when creating methods.
    private final String up = "UP";
    private final String left = "LEFT";
    private final String right = "RIGHT";
    private final String space = "SPACE";
    private final String shift = "SHIFT";

    // The constructor takes in an instance of Ship.
    public Controller(Ship ship) {
        this.ship = ship;
    }

    // The main public method that will be responsible for reading the controls of the user.
    public void readUserInput(){
        initControls();
        readMovementKeys();
        readShootKey();
    }


    // Defines the actions that will occur when the key is pressed and when the key is released.
    private void initControls() {
        onKeyDown();
        onKeyReleased();
    }

    // Sets the actions that will occur when the key is pressed down. The is "pressedKeys" does not contain the
    // key that was pressed down, add it. Also, add it to "tempPressedKeys". "tempPressedKeys" represents the keys
    // that need discrete movement, so we should only allow these to be activated when the key is released.
    private void onKeyDown() {
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
    }

    // Sets the actions of what occurs when the key is released. If the key in question requires continuous pressing
    // (i.e. it's related to movement) then we remove it from "pressedKeys" (i.e. it's no longer activate when we
    // let go of the key),
    private void onKeyReleased() {
        screen.getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String key = keyEvent.getCode().toString();
                pressedKeys.remove(key);
            }
        });
    }

    // Reading the shoot key and ensuring we can only press it discretely (i.e. not continuously).
    private void readShootKey() {
        if (tempPressedKeys.contains(space)) {
            ship.shoot();
        }
        // to avoid the continuous bullets
        tempPressedKeys.remove(space);
    }

    // Reading the hyperspace key and ensuring we can only press it discretely (i.e. not continuously).
    public void readHyperspaceKey(){
            ship.activateHyperSpaceJump();
    }

    public boolean isShiftPressed(){
        return tempPressedKeys.contains(shift);
    }

    public void removeShiftPress(){
        tempPressedKeys.remove(shift);
    }

    // Setting a particular movement to each key.
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

        // While not pressing a key, continue moving the ship and its bullets.
        ship.move();
        ship.moveBullets();
    }
}