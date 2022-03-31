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
        this.ship = new Ship(screen.getScreenWidth()/2,screen.getScreenHeight()/2);
        this.shipController = new Controller(ship, screen);

        this.screen.createMainWindow();
        this.screen.drawGameObject(ship);
    }

    public void play(){
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

                // move all the bullets
                for (Bullet bullet: ship.getBullets()){
                    bullet.move();
                    // if the bullet is over certain distance, it should be removed.
                    if (bullet.getDistance() > 10){
                        ship.getBullets().remove(bullet);
                        screen.removeGameObject(bullet);
                        // it is mandatory to break the loop. otherwise, it will crash.
                        break;
                    }
                }
            }
        }.start();
    }
}