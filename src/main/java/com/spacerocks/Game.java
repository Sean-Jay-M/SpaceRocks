package com.spacerocks;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    // Create the screen object and generate the main window
    Screen screen;
    Ship ship;
    // Creating the ship controller, passing in the Ship that we have created and the Scene property of the Screen.
    Controller shipController;
    // Random
    Random random;
    // Asteroids
    ArrayList<Asteroid> asteroids;
    // Lives
    int lives;

    Spawner spawner;

    public Game(Screen screen) {
        this.screen = screen;
        this.spawner = screen.getSpawner();
        this.ship = new Ship(Screen.getScreenWidth()/2.0,Screen.getScreenHeight()/2.0);
        this.shipController = new Controller(ship, screen);
        this.random = new Random();
        this.asteroids = new ArrayList<>();
        this.lives = 3;
        this.screen.createMainWindow();
        spawner.spawnGameObject(ship);

        // initial asteroids
        for (int i=0; i<3; i++){
            Asteroid asteroid = new Asteroid(AsteroidSize.BIG,this.random.nextDouble(Screen.getScreenWidth()/3.0),this.random.nextDouble(Screen.getScreenWidth()/3.0));
            asteroid.rotate(random.nextDouble(10,60));
            spawner.drawGameObject(asteroid);
            this.asteroids.add(asteroid);
        }
        for (int i=0; i<2; i++){
            Asteroid asteroid = new Asteroid(AsteroidSize.MEDIUM,this.random.nextDouble(Screen.getScreenWidth()/3.0),this.random.nextDouble(Screen.getScreenWidth()/3.0));
            asteroid.rotate(random.nextDouble(10,30));
            spawner.drawGameObject(asteroid);
            this.asteroids.add(asteroid);
        }
        for (int i=0; i<1; i++){
            Asteroid asteroid = new Asteroid(AsteroidSize.SMALL,this.random.nextDouble(Screen.getScreenWidth()/3.0),this.random.nextDouble(Screen.getScreenWidth()/3.0));
            asteroid.rotate(random.nextDouble(10,15));
            spawner.drawGameObject(asteroid);
            this.asteroids.add(asteroid);
        }
    }

    public void play(){
        // Set add listener to spawner to check if bullets need to be deleted
        ship.setSpawnListener(screen.getSpawner());
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

                // move each asteroid
                for (Asteroid asteroid: asteroids){
                    asteroid.move();
                }

                // detect ship collision with asteroid
                for (Asteroid asteroid: asteroids){
                    if (ship.hasCollided(asteroid)){
                        // respawn the ship
                        spawner.despawn(ship);
                        spawner.spawnGameObject(ship);
                        shipController.resetShip(ship);

                        // lives minus 1
                        screen.getUI().removeLife();
                        lives = lives - 1;
                        // break the loop. otherwise, it will crash
                        break;
                    }
                }

                // if live is equal to 0, terminate the game
//                if(lives == 0){
//                    stop();
//                }

                // detect bullet collision with asteroid
                for (Bullet bullet: ship.getBullets()){
                    for (Asteroid asteroid: asteroids){
                        if (bullet.hasCollided(asteroid)){
                            // add the score
                            if (asteroid.getSize() == AsteroidSize.BIG){
                                screen.getUI().addScoreValue(300);
                            }
                            if (asteroid.getSize() == AsteroidSize.MEDIUM){
                                screen.getUI().addScoreValue(200);
                            }
                            if (asteroid.getSize() == AsteroidSize.SMALL){
                                screen.getUI().addScoreValue(100);
                            }
                            asteroids.remove(asteroid);
                            screen.getSpawner().despawn(asteroid);
                            // break the loop. otherwise, it will crash
                            break;
                        }
                    }
                }
            }
        }.start();
    }
}