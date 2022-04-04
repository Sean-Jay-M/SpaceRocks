package com.spacerocks;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Game {
    // TODO: Delete bullets after the ship is destroyed.

    Screen screen;
    Ship ship = new Ship();
    Controller shipController;
    Random random = new Random();
    ArrayList<Asteroid> asteroids = new ArrayList<>();
    int lives = 3;

    Spawner spawner;
    LevelManager levelManager;

    public Game(Screen screen) {
        this.screen = screen;
        spawner = screen.getSpawner();
        shipController = new Controller(ship, screen);
        this.screen.createMainWindow();
        spawner.spawnGameObject(ship);

        Asteroid bigAsteroid = new Asteroid(AsteroidSize.BIG);
        bigAsteroid.rotate(random.nextDouble(10, 60));
        asteroids.add(bigAsteroid);
        spawner.spawnGameObject(bigAsteroid);

// TODO: Link number of asteroids to spawn to the level number
//        for (int i=0; i<2; i++){
//            Asteroid asteroid = new Asteroid(AsteroidSize.MEDIUM);
//            asteroid.rotate(random.nextDouble(10,30));
//            spawner.drawGameObject(asteroid);
//            this.asteroids.add(asteroid);
//        }
//        for (int i=0; i<1; i++){
//            Asteroid asteroid = new Asteroid(AsteroidSize.SMALL);
//            asteroid.rotate(random.nextDouble(10,15));
//            spawner.drawGameObject(asteroid);
//            this.asteroids.add(asteroid);
//        }
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

                Asteroid.moveAsteroids(asteroids);

                checkForAsteroidCollisions();
                checkForBulletCollisions();
            }
        }.start();
    }

    private void checkForAsteroidCollisions() {
        // detect ship collision with asteroid
        for (Asteroid asteroid: asteroids){
            if (ship.hasCollided(asteroid)){
                // respawn the ship
                spawner.despawn(ship);
                spawner.spawnGameObject(ship);
                shipController.resetShip(ship);

                // lives minus 1
                if (lives > 0) {
                    screen.getUI().removeLife();
                    lives = lives - 1;
                }

                // break the loop. otherwise, it will crash
                break;
            }
        }
    }

    private void checkForBulletCollisions() {
        // detect bullet collision with asteroid
        for (Bullet bullet: ship.getBullets()){
            for (Asteroid asteroid: asteroids){
                if (bullet.hasCollided(asteroid)){
                    bullet.setUsed();
                    spawner.despawn(bullet);
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
                    if (asteroid.getSize() != AsteroidSize.SMALL) {
                        Asteroid newAsteroid1 = Asteroid.getAsteroidPieces(asteroid);
                        Asteroid newAsteroid2 = Asteroid.getAsteroidPieces(asteroid);

                        asteroids.add(newAsteroid1);
                        screen.getSpawner().spawnGameObject(newAsteroid1);

                        asteroids.add(newAsteroid2);
                        screen.getSpawner().spawnGameObject(newAsteroid2);
                    }
                    asteroids.remove(asteroid);
                    screen.getSpawner().despawn(asteroid);
                    break;
                }
            }
        }
    }
}