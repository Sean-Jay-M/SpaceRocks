package com.spacerocks.main;

import com.spacerocks.gameobjects.*;
import com.spacerocks.ui.ScoreBoardHandler;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;

import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// This class is responsible for the overall running of the game.
// It contains the game logic, and shows how the various components of the game will
// interact.
public class Game {

    // Initialise variables and components necessary for running of the game.
    // This includes a reference to the screen instance.
    Screen screen = Screen.getScreenInstance();
    Ship ship = new Ship();
    Controller shipController = new Controller(ship);

    // Variables relating to the alien ship
    AlienShip alienShip = new AlienShip();
    boolean isAlienSpawned = false;
    int alienSpawnCooldown = 500;

    // Static reference to all asteroids that have been created
    ArrayList<Asteroid> asteroids = Asteroid.asteroids;

    // Miscellaneous components relating to the running of the game
    Spawner spawner = screen.getSpawner();
    LevelManager levelManager = new LevelManager();
    ScoreBoardHandler scoreBoardHandler;

    // This figure represents the "in progress" score in the level.
    // If the player dies without finishing the level, this will go into
    // the high score (if high enough)
    int scoreToAdd = 0;

    // Miscellaneous variables, including
    Random random = new Random();
    boolean writtenToFile = false;
    private final Duration pauseBetweenLevels = new Duration(1000);

    // Constructor that initialises the game and sets certain values
    public Game() {
        screen.getUI().resetUIValues();
        spawner.spawnGameObject(ship);
        scoreBoardHandler = screen.getUI().getHighScoreUIPreset().getScoreBoardHandler();
        initNewAsteroids();
    }

    // This method represents the actual playing of the game. It should be called whenever the game starts.
    public void play(){
        // Starting new animation timer and setting up the controls inside to read user input continuously.
        new AnimationTimer() {
            @Override
            public void handle(long l) {

                // We deal with any significant events relating to the game at the start of each loop.
                // This includes events such as game over, or the player moving on to a new level.
                if (levelManager.getLives() == 0) {
                    saveAndExit();
                }

                // Check if ship has collided, in which case
                if (shipHasCollided()) {
                    // remove the alien ship
                    despawnAlienShip();
                    resetLevelPosition(this);
                } else if (asteroids.isEmpty()) {
                    // remove the alien ship
                    despawnAlienShip();
                    nextLevel(this);
                }


                shipController.readUserInput();
                readHyperspaceJump();

                // keep all bullets moving
                ship.moveBullets();

                // keep all asteroids moving
                Asteroid.moveAsteroids();
                checkForBulletCollisionWithAsteroid();

                // check whether the ship is invincible
                if (ship.getIsInvincible()) {
                    // show the animation
                    ship.playInvincibilityAnimation();
                }

                // Alien behaviour
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

                // All UI manipulation was included at the bottom of each loop for convenience.
                if (asteroids.isEmpty()) {
                    screen.getUI().toggleNextLevelText(true);
                }

                if (levelManager.getLives() == 0) {
                    screen.getUI().toggleGameOverText(true);
                }
            }
        }.start();
    }
    

    // Note: The methods listed below represent broken out versions of the methods in the core loop.
    // We have included comments to the extent anything in the code was unclear, with the method names
    // clearly representing what such method does.

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

    // Check if ship has collided
    private boolean shipHasCollided() {
        if (ship.getIsInvincible()) return false;
        if (shipHasCollidedWithAsteroid()) return true;
        if (shipHasCollidedWithAlienBullet()) return true;
        if (ship.hasCollided(alienShip)) return true;
        return false;
    }

    // Check if ship has collided with an alien bullet
    private boolean shipHasCollidedWithAlienBullet() {
        for (Bullet bullet: alienShip.getBullets()){
            if (ship.hasCollided(bullet)){
                bullet.setUsed();
                return true;
            }
        }
        return false;
    }

    // Check if ship has collided with an asteroid
    private boolean shipHasCollidedWithAsteroid() {
        for (Asteroid asteroid: asteroids){
            if (ship.hasCollided(asteroid)){
                return true;
            }
        }
        return false;
    }

    // Check if alien ship has collided with anything
    private boolean alienHasCollided(){
        if (alienShipHasCollidedWithShipBullet()) return true;
        if (alienShip.hasCollided(ship)) return true;
        return false;
    }

    // Check if alien ship has collided with a ship bullet
    private boolean alienShipHasCollidedWithShipBullet() {
        for (Bullet bullet: ship.getBullets()){
            if (bullet.hasCollided(alienShip)){
                bullet.setUsed();
                return true;
            }
        }
        return false;
    }

    // Check if bullet has collided with an asteroid
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

    // Manage asteroid if it has collided with something
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

    // Breaks the asteroid pieces into two
    private void createNewAsteroidPieces(Asteroid asteroid) {
        Asteroid newAsteroid1 = Asteroid.getAsteroidPieces(asteroid);
        Asteroid newAsteroid2 = Asteroid.getAsteroidPieces(asteroid);
        screen.getSpawner().spawnGameObject(newAsteroid1);
        screen.getSpawner().spawnGameObject(newAsteroid2);
    }


    // METHODS RELATING TO ADDING / REMOVING OBJECTS TO SCREEN

    // Removes all the bullets from the screen
    public void removeAllBulletsFromScreen() {
        for (Bullet bullet: ship.getBullets()) {
            spawner.despawnGameObject(bullet);
        }
        for (Bullet bullet: alienShip.getBullets()) {
            spawner.despawnGameObject(bullet);
        }
        ship.getBullets().clear();
    }

    // initialize new asteroids
    private void initNewAsteroids() {
        for (int i = 0; i < levelManager.getLevel(); i++) {
            Asteroid bigAsteroid = new Asteroid(AsteroidSize.BIG);
            bigAsteroid.turn(random.nextInt(0, 360));
            spawner.spawnGameObject(bigAsteroid);
        }
    }

    // Removes the alien ship and resets the cooldown
    private void despawnAlienShip() {
        spawner.despawnGameObject(alienShip);
        isAlienSpawned = false;
        alienSpawnCooldown = 500;
    }

    // Shows all the bullets on the screen
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

    // Adds score depending on size of asteroid that was hit
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

    // Adds the core to the scoreboard
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

    // Sets the next level of the game
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

    // reduce life
    private void reduceLife() {
        levelManager.reduceLife();
        screen.getUI().updateLives(levelManager.getLives());
    }

    // Pauses the game for one second, there is an exception in case something goes wrong with the thread.
    public void pauseGame() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Something has gone wrong");
        }
    }

    // Pauses the animation time for a particular duration
    private void pauseTimerForDuration(AnimationTimer timer, Duration duration) {
        PauseTransition pt = new PauseTransition(duration);
        pt.setOnFinished(event -> timer.start());
        timer.stop();
        pt.play();
    }

    // Saves the high score and exits the game
    private void saveAndExit() {
        scoreToAdd = screen.getUI().getCurrentScoreValue();
        if (!writtenToFile) {
            addScoreToScoreboard();
        }
        System.exit(0);
    }
}