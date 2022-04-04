package com.spacerocks;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    // TODO: Delete bullets after the ship is destroyed.

    Screen screen;
    Ship ship = new Ship();
    Controller shipController;
    Random random = new Random();
    private final Duration pauseBetweenLevels = new Duration(1000);

    ArrayList<Asteroid> asteroids = Asteroid.asteroids;

    int lives = 3;

    Spawner spawner;
    LevelManager levelManager = new LevelManager();

    public Game(Screen screen) {
        this.screen = screen;
        spawner = screen.getSpawner();
        shipController = new Controller(ship, screen);
        this.screen.createMainWindow();
        spawner.spawnGameObject(ship);

        initNewAsteroids();
    }

    public void resetLevel(AnimationTimer timer) {
        screen.getUI().toggleCrashText(false);
        pauseTimerForDuration(timer, pauseBetweenLevels);
        pauseGame();
        screen.getUI().setScoreValue(levelManager.getHighestScore());
        if (lives > 0) { reduceLife(); }
        removeCurrentAsteroids();
        initNewAsteroids();
        ship.respawn();
//        screen.getUI().toggleCrashText(false);
    }

    public void pauseGame() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Something has gone wrong");
        }
    }

    public void play(){
        // Set add listener to spawner to check if bullets need to be deleted
        ship.setSpawnListener(screen.getSpawner());
        // Starting new animation timer and setting up the controls inside to read user input continuously.

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (shipHasCollidedWithAsteroid()) {
                    resetLevel(this);
                }
                // Read keyboard keys from the user.
                shipController.readUserInput();
                // default speed of ship is 0, so the ship is moving all the time.
                ship.move();
                ship.thrust();
                ship.shoot();
                Asteroid.moveAsteroids();
                checkForBulletCollisionWithAsteroid();
                if (shipHasCollidedWithAsteroid()) {
                    screen.getUI().toggleCrashText(true);
                }

            }
        }.start();
    }

    // TODO: Potentially figure out a way to move this to ship class
    private boolean shipHasCollidedWithAsteroid() {
        // detect ship collision with asteroid
        for (Asteroid asteroid: asteroids){
            if (ship.hasCollided(asteroid)){
                return true;
            }
        }
        return false;
    }

    // TODO: Potentially figure out a way to move this to bullet class
    private void checkForBulletCollisionWithAsteroid() {
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

    private void reduceLife() {
        screen.getUI().removeLife();
        lives = lives - 1;
    }

    public void removeCurrentAsteroids() {
        for (Asteroid asteroid: asteroids) {
            spawner.despawn(asteroid);
        }
        asteroids.clear();
    }

    private void initNewAsteroids() {
        for (int i = 0; i < levelManager.getLevel(); i++) {
            Asteroid bigAsteroid = new Asteroid(AsteroidSize.BIG);
            bigAsteroid.rotate(random.nextDouble(10, 60));
            spawner.spawnGameObject(bigAsteroid);
        }
    }

    void pauseTimerForDuration(AnimationTimer timer, Duration duration) {
        PauseTransition pt = new PauseTransition(duration);
        pt.setOnFinished(event -> timer.start());
        timer.stop();
        pt.play();
    }

}