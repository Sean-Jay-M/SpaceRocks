package com.spacerocks;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;

import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    // TODO: Delete bullets after the ship is destroyed.

    Screen screen;
    Ship ship = new Ship();
    AlienShip alienShip = new AlienShip();
    Controller shipController;
    Random random = new Random();
    boolean gameOver = false;
    private final Duration pauseBetweenLevels = new Duration(1000);


    ArrayList<Asteroid> asteroids = Asteroid.asteroids;

    int lives = 3;
    boolean isAlienSpawned = false;
    int alienSpawnCooldown = 500;

    Spawner spawner;
    LevelManager levelManager = new LevelManager();
    ScoreBoardHandler scoreBoardHandler = new ScoreBoardHandler();

    public Game(Screen screen) {
        System.out.println("Starting new game");
        this.screen = screen;
        spawner = screen.getSpawner();
        shipController = new Controller(ship, screen);
        spawner.spawnGameObject(ship);
        //spawner.spawnGameObject(alienShip);
        initNewAsteroids();
    }

    public void play(){
        // Set add listener to spawner to check if bullets need to be deleted
        ship.setSpawnListener(screen.getSpawner());
        // Starting new animation timer and setting up the controls inside to read user input continuously.

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (shipHasCollided()) {
                    resetLevel(this);
                } else if (asteroids.isEmpty()) {
                    nextLevel(this);
                }
                // Read keyboard keys from the user.
                shipController.readUserInput();
                // default speed of ship is 0, so the ship is moving all the time.
                ship.shoot();
                Asteroid.moveAsteroids();
                checkForBulletCollisionWithAsteroid();

                if (isAlienSpawned) {
                    alienShip.move();
                    alienShip.changeDirection(); //every 100 calls this will change the angle of travel of the alien
                    showAlienBulletOnScreen(); //shoots bullet every 20 calls
                    alienShip.shoot();
                    if (alienHasCollided()){
                        spawner.despawn(alienShip);
                        isAlienSpawned = false;
                        alienSpawnCooldown = 500;
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
                if (shipHasCollided()) {
                    screen.getUI().toggleCrashText(true);
                } else if (asteroids.isEmpty()) {
                    screen.getUI().toggleNextLevelText(true);
                }
                if (lives == 0) {
                    addScoreToScoreboard();
                    this.stop();
                    levelManager.resetGame();
                    screen.setMenuScreen();
                }
            }
        }.start();
    }

    private void addScoreToScoreboard() {
        String currentScore = Integer.toString(screen.getUI().getCurrentScoreValue());
        try {
            scoreBoardHandler.refreshScoreBoard(currentScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Potentially figure out a way to move this to ship class
    private boolean shipHasCollided() {
        // detect ship collision with asteroid
        for (Asteroid asteroid: asteroids){
            if (ship.hasCollided(asteroid)){
                return true;
            }
        }
        // detect ship collision with alien bullet
        for (Bullet bullet: alienShip.getBullets()){
            if (bullet.hasCollided(ship)){
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
        for (Asteroid asteroid: asteroids){
            if (alienShip.hasCollided(asteroid)){
                return true;
            }
        }
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
                        screen.getSpawner().spawnGameObject(newAsteroid1);
                        screen.getSpawner().spawnGameObject(newAsteroid2);
                    }
                    asteroids.remove(asteroid);
                    screen.getSpawner().despawn(asteroid);
                    break;
                }
            }
        }
        // check alien bullet collision with asteroid
        for (Bullet bullet: alienShip.getBullets()){
            for (Asteroid asteroid: asteroids){
                if (bullet.hasCollided(asteroid)){
                    bullet.setUsed();
                    spawner.despawn(bullet);
                    //removed getting points for alien ship shooting an asteroid
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
        screen.getUI().setScoreValue(levelManager.getHighestScore());
        if (lives > 0) {
            reduceLife();
        }
        removeCurrentAsteroids();
        initNewAsteroids();
        ship.respawn();
        screen.getUI().toggleCrashText(false);
    }

    public void nextLevel(AnimationTimer timer) {
        pauseTimerForDuration(timer, pauseBetweenLevels);
        pauseGame();
        levelManager.updateHighestScore(screen.getUI().getCurrentScoreValue());
        levelManager.increaseLevel();
        initNewAsteroids();
        ship.respawn();
        screen.getUI().toggleNextLevelText(false);
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