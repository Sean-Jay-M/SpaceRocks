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

    Screen screen = Screen.getScreenInstance();
    Ship ship = new Ship();
    Controller shipController = new Controller(ship);

    AlienShip alienShip = new AlienShip();
    boolean isAlienSpawned = false;
    int alienSpawnCooldown = 500;

    Random random = new Random();
    boolean writtenToFile = false;
    private final Duration pauseBetweenLevels = new Duration(1000);

    ArrayList<Asteroid> asteroids = Asteroid.asteroids;

    Spawner spawner = screen.getSpawner();
    LevelManager levelManager = new LevelManager();
    ScoreBoardHandler scoreBoardHandler;
    int scoreToAdd = 0;

    public Game() {
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
                // Check if ship has collided, in which case
                if (shipHasCollided()) {
                    despawnAlienShip();
                    resetLevelPosition(this);
                } else if (asteroids.isEmpty()) {
                    despawnAlienShip();
                    nextLevel(this);
                }

                shipController.readUserInput();
                readHyperspaceJump();

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

                if (levelManager.getLives() == 0) {
                    saveAndExit();
                }
            }
        }.start();
    }
    

    // METHODS RELATING TO COLLISIONS

    public void readHyperspaceJump(){
        if(shipController.isShiftPressed()){ //if shift not pressed nothing happens
            int i = 0;
            do {
                shipController.readHyperspaceKey();
                i++;
            } while (shipHasCollided() && i < 10); //attempt to find safe location up to 10 times

            if (shipHasCollided()){ //if it never found a safe location move it anyway but start invincibility
                ship.resetInvincibility();
                ship.toggleInvincibility();
            }

            shipController.removeShiftPress(); //now that hyperspace jump is complete remove from pressed buttons
        }
    }

    private boolean shipHasCollided() {
        if (ship.getIsInvincible()) return false;
        if (shipHasCollidedWithAsteroid()) return true;
        if (shipHasCollidedWithAlienBullet()) return true;
        if (ship.hasCollided(alienShip)) return true;
        return false;
    }

    private boolean shipHasCollidedWithAlienBullet() {
        for (Bullet bullet: alienShip.getBullets()){
            if (ship.hasCollided(bullet)){
                bullet.setUsed();
                return true;
            }
        }
        return false;
    }

    private boolean shipHasCollidedWithAsteroid() {
        for (Asteroid asteroid: asteroids){
            if (ship.hasCollided(asteroid)){
                return true;
            }
        }
        return false;
    }

    private boolean alienHasCollided(){
        if (alienShipHasCollidedWithShipBullet()) return true;
        if (alienShip.hasCollided(ship)) return true;
        return false;
    }

    private boolean alienShipHasCollidedWithShipBullet() {
        for (Bullet bullet: ship.getBullets()){
            if (bullet.hasCollided(alienShip)){
                bullet.setUsed();
                return true;
            }
        }
        return false;
    }

    private void checkForBulletCollisionWithAsteroid() {
        // detect bullet collision with asteroid
        for (Bullet bullet: ship.getBullets()){
            for (Asteroid asteroid: asteroids){
                if (bullet.hasCollided(asteroid)){
                    manageAsteroidAfterCollision(bullet, asteroid);
                    break;
                }
            }
        }
    }

    private void manageAsteroidAfterCollision(Bullet bullet, Asteroid asteroid) {
        bullet.setUsed();
        spawner.despawnGameObject(bullet);
        addToScore(asteroid);
        if (asteroid.getSize() != AsteroidSize.SMALL) {
            createNewAsteroidPieces(asteroid);
        }
        asteroids.remove(asteroid);
        spawner.despawnGameObject(asteroid);
    }

    private void createNewAsteroidPieces(Asteroid asteroid) {
        Asteroid newAsteroid1 = Asteroid.getAsteroidPieces(asteroid);
        Asteroid newAsteroid2 = Asteroid.getAsteroidPieces(asteroid);
        screen.getSpawner().spawnGameObject(newAsteroid1);
        screen.getSpawner().spawnGameObject(newAsteroid2);
    }


    // METHODS RELATING TO ADDING / REMOVING OBJECTS TO SCREEN

    public void removeAllBulletsFromScreen() {
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
            bigAsteroid.turn(random.nextInt(0, 360));
            spawner.spawnGameObject(bigAsteroid);
        }
    }

    private void despawnAlienShip() {
        spawner.despawnGameObject(alienShip);
        isAlienSpawned = false;
        alienSpawnCooldown = 500;
    }

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


    // METHODS RELATING TO UI

    private void addToScore(Asteroid asteroid) {
        if (asteroid.getSize() == AsteroidSize.BIG){
            screen.getUI().addScoreValue(300);
        }
        if (asteroid.getSize() == AsteroidSize.MEDIUM){
            screen.getUI().addScoreValue(200);
        }
        if (asteroid.getSize() == AsteroidSize.SMALL){
            screen.getUI().addScoreValue(100);
        }
    }

    private void addScoreToScoreboard() {
        String currentScore = Integer.toString(scoreToAdd);
        try {
            scoreBoardHandler.refreshScoreBoard(currentScore);
            writtenToFile = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // METHODS RELATING TO LEVEL MANAGEMENT

    public void resetLevelPosition(AnimationTimer timer) {
        ship.respawn();
        if (ship.getIsInvincible()) {
            ship.resetInvincibility();
        }
        ship.toggleInvincibility();
        if (levelManager.getLives() > 0) {
            reduceLife();
        }
    }

    public void nextLevel(AnimationTimer timer) {
        pauseTimerForDuration(timer, pauseBetweenLevels);
        pauseGame();
        levelManager.updateHighestScore(screen.getUI().getCurrentScoreValue());
        levelManager.increaseLevel();
        initNewAsteroids();
        removeAllBulletsFromScreen();
        ship.respawn();
        screen.getUI().toggleNextLevelText(false);
        screen.setNextBackground();
    }

    private void reduceLife() {
        levelManager.reduceLife();
        screen.getUI().updateLives(levelManager.getLives());
    }

    public void pauseGame() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Something has gone wrong");
        }
    }

    private void pauseTimerForDuration(AnimationTimer timer, Duration duration) {
        PauseTransition pt = new PauseTransition(duration);
        pt.setOnFinished(event -> timer.start());
        timer.stop();
        pt.play();
    }

    private void saveAndExit() {
        scoreToAdd = screen.getUI().getCurrentScoreValue();
        if (!writtenToFile) {
            addScoreToScoreboard();
        }
        System.exit(0);
    }
}