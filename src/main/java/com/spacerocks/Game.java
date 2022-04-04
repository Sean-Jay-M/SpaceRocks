package com.spacerocks;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;

import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    // TODO: Delete bullets after the ship is destroyed.

    Screen screen;
    UI ui;
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
        ui = screen.getUI();
        spawner = screen.getSpawner();
        shipController = new Controller(ship, screen);
        this.screen.createMainWindow();
        spawner.spawnGameObject(ship);
        initNewAsteroids();
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
                } else if (asteroids.isEmpty()) {
                    nextLevel(this);
                }
                // Read keyboard keys from the user.
                shipController.readUserInput();
                // default speed of ship is 0, so the ship is moving all the time.
                ship.move();
                ship.thrust();
                ship.shoot();
                Asteroid.moveAsteroids();
                checkForBulletCollisionWithAsteroid();

                // Note: UI manipulation and pausing have to be done and separate parts of the frame
                if (shipHasCollidedWithAsteroid()) {
                    ui.toggleCrashText(true);
                } else if (asteroids.isEmpty()) {
                    ui.toggleNextLevelText(true);
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
                        ui.addScoreValue(300);
                    }
                    if (asteroid.getSize() == AsteroidSize.MEDIUM){
                        ui.addScoreValue(200);
                    }
                    if (asteroid.getSize() == AsteroidSize.SMALL){
                        ui.addScoreValue(100);
                    }
                    if (asteroid.getSize() != AsteroidSize.SMALL) {
                        Asteroid newAsteroid1 = Asteroid.getAsteroidPieces(asteroid);
                        Asteroid newAsteroid2 = Asteroid.getAsteroidPieces(asteroid);
                        screen.getSpawner().spawnGameObject(newAsteroid1);
                        screen.getSpawner().spawnGameObject(newAsteroid2);
                    }
                    asteroids.remove(asteroid);
                    screen.getSpawner().despawn(asteroid);
                    break;
                }
            }
        }
    }

    public void resetLevel(AnimationTimer timer) {
        pauseTimerForDuration(timer, pauseBetweenLevels);
        pauseGame();
        ui.setScoreValue(levelManager.getHighestScore());
        if (lives > 0) { reduceLife(); }
        removeCurrentAsteroids();
        initNewAsteroids();
        ship.respawn();
        ui.toggleCrashText(false);
    }

    public void nextLevel(AnimationTimer timer) {
        pauseTimerForDuration(timer, pauseBetweenLevels);
        pauseGame();
        levelManager.updateHighestScore(ui.getCurrentScoreValue());
        levelManager.increaseLevel();
        initNewAsteroids();
        ship.respawn();
        ui.toggleNextLevelText(false);
    }

    public void pauseGame() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Something has gone wrong");
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