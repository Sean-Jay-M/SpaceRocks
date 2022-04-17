package com.spacerocks.main;

import com.spacerocks.gameobjects.*;
import com.spacerocks.ui.ScoreBoardHandler;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;

import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    // TODO: Delete bullets after the ship is destroyed.

    Screen screen = Screen.getScreenInstance();
    Ship ship = new Ship();
    Controller shipController = new Controller(ship, screen);

    AlienShip alienShip = new AlienShip();
    boolean isAlienSpawned = false;
    int alienSpawnCooldown = 500;

    Random random = new Random();
    boolean writtenToFile = false;
    private final Duration pauseBetweenLevels = new Duration(1000);

    ArrayList<Asteroid> asteroids = Asteroid.asteroids;

    int lives;


    Spawner spawner = screen.getSpawner();
    LevelManager levelManager = new LevelManager();
    ScoreBoardHandler scoreBoardHandler;

    public Game() {
        System.out.println("Starting new game");
        lives = 3;
        screen.getUI().resetUIValues();
        spawner.spawnGameObject(ship);
        scoreBoardHandler = screen.getUI().getHighScoreUIPreset().getScoreBoardHandler();
        initNewAsteroids();
    }

    public void play(){
        // Starting new animation timer and setting up the controls inside to read user input continuously.

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (shipHasCollided()) {
                    despawnAlienShip();
                    resetLevel(this);
                } else if (asteroids.isEmpty()) {
                    nextLevel(this);
                }
                // Read keyboard keys from the user.
                shipController.readUserInput();
                // default speed of ship is 0, so the ship is moving all the time.
                ship.moveBullets();
                Asteroid.moveAsteroids();
                checkForBulletCollisionWithAsteroid();

                if (ship.getIsInvincible()) {
                    ship.playInvincibilityAnimation();
                }


                if (isAlienSpawned) {
                    alienShip.move();
                    alienShip.changeDirection(); //every 100 calls this will change the angle of travel of the alien
                    showAlienBulletOnScreen(); //shoots bullet every 20 calls
                    alienShip.shoot();
                    if (alienHasCollided()){
                        despawnAlienShip();
                    }
                } else {
                    alienShip.shoot(); //repeated here in case any of the alien bullets still exist
                    alienSpawnCooldown--;
                    if (alienSpawnCooldown == 0){
                        spawner.spawnGameObject(alienShip);
                        isAlienSpawned = true;
                        alienSpawnCooldown = 500;
                    }
                }

                // Note: UI manipulation and pausing have to be done and separate parts of the frame
                if (asteroids.isEmpty()) {
                    screen.getUI().toggleNextLevelText(true);
                }
                if (lives == 0) {
                    if (!writtenToFile) {
                        addScoreToScoreboard();
                    }
                    System.exit(0);
                }
            }
        }.start();
    }

    private void despawnAlienShip() {
        spawner.despawnGameObject(alienShip);
        isAlienSpawned = false;
        alienSpawnCooldown = 500;
    }


    private void addScoreToScoreboard() {
        String currentScore = Integer.toString(screen.getUI().getCurrentScoreValue());
        try {
            scoreBoardHandler.refreshScoreBoard(currentScore);
            writtenToFile = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Potentially figure out a way to move this to ship class
    private boolean shipHasCollided() {
        if (ship.getIsInvincible()) return false;

        // detect ship collision with asteroid
        for (Asteroid asteroid: asteroids){
            if (ship.hasCollided(asteroid)){
                return true;
            }
        }
        // detect ship collision with alien bullet
        for (Bullet bullet: alienShip.getBullets()){
            if (ship.hasCollided(bullet)){
                bullet.setUsed();
                return true;
            }
        }
        //detect ship collision with alien ship
        if (ship.hasCollided(alienShip)){
            return true;
        }
        return false;
    }

    private boolean alienHasCollided(){
//        for (Asteroid asteroid: asteroids){
//            if (alienShip.hasCollided(asteroid)){
//                return true;
//            }
//        }
        for (Bullet bullet: ship.getBullets()){
            if (bullet.hasCollided(alienShip)){
                bullet.setUsed();
                return true;
            }
        }
        if (alienShip.hasCollided(ship)){
            return true;
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
                    spawner.despawnGameObject(bullet);
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
                        screen.getSpawner().spawnGameObject(newAsteroid1);
                        screen.getSpawner().spawnGameObject(newAsteroid2);
                    }
                    asteroids.remove(asteroid);
                    screen.getSpawner().despawnGameObject(asteroid);
                    break;
                }
            }
        }
        // check alien bullet collision with asteroid
//        for (Bullet bullet: alienShip.getBullets()){
//            for (Asteroid asteroid: asteroids){
//                if (bullet.hasCollided(asteroid)){
//                    bullet.setUsed();
//                    spawner.despawn(bullet);
//                    //removed getting points for alien ship shooting an asteroid
//                    if (asteroid.getSize() != AsteroidSize.SMALL) {
//                        Asteroid newAsteroid1 = Asteroid.getAsteroidPieces(asteroid);
//                        Asteroid newAsteroid2 = Asteroid.getAsteroidPieces(asteroid);
//                        screen.getSpawner().spawnGameObject(newAsteroid1);
//                        screen.getSpawner().spawnGameObject(newAsteroid2);
//                    }
//                    asteroids.remove(asteroid);
//                    screen.getSpawner().despawn(asteroid);
//                    break;
//                }
//            }
//        }
    }

    public void resetLevel(AnimationTimer timer) {
        ship.respawn();
        if (ship.getIsInvincible()) {
            ship.resetInvincibility();
        }
        ship.toggleInvincibility();
        if (lives > 0) {
            reduceLife();
        }
//        pauseTimerForDuration(timer, pauseBetweenLevels);
//        pauseGame();
//        screen.getUI().setScoreValue(levelManager.getHighestScore());
//        if (lives > 0) {
//            reduceLife();
//        }
//        removeCurrentAsteroids();
//        removeCurrentBullets();
//        if (alienShip != null) {
//            despawnAlienShip();
//        }
//        initNewAsteroids();
//        ship.respawn();
//        screen.getUI().toggleCrashText(false);
    }

    public void nextLevel(AnimationTimer timer) {
        pauseTimerForDuration(timer, pauseBetweenLevels);
        pauseGame();
        levelManager.updateHighestScore(screen.getUI().getCurrentScoreValue());
        levelManager.increaseLevel();
        initNewAsteroids();
        removeCurrentBullets();
        ship.respawn();
        screen.getUI().toggleNextLevelText(false);
        screen.setNextBackground();
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
            spawner.despawnGameObject(asteroid);
        }
        asteroids.clear();
    }

    public void removeCurrentBullets() {
        for (Bullet bullet: ship.getBullets()) {
            spawner.despawnGameObject(bullet);
        }
        for (Bullet bullet: alienShip.getBullets()) {
            spawner.despawnGameObject(bullet);
        }
        ship.getBullets().clear();
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

    // TODO: Potentially figure out a better class for this method
    public void showAlienBulletOnScreen() {
        if (alienShip.checkBulletCooldown()){ //every 20 calls to the checkBulletCooldown creates a bullet
            Bullet bullet = new Bullet((int) alienShip.getCurrentXPosition(), (int) alienShip.getCurrentYPosition(), alienShip.getSpeed() + 1.0);
            bullet.getPolygon().setRotate(alienShip.getPolygon().getRotate());

            // add bullet to the arraylist in ship class
            alienShip.addBullet(bullet);
            // draw the bullet
            screen.getSpawner().spawnGameObject(bullet);
        }
    }
}