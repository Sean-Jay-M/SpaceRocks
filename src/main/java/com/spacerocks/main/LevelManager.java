package com.spacerocks.main;

/*
a Level object would be created when the game is started
reset the object on losing back to 1
increase when level has been won
get level at start of new level gives number of asteroids to spawn
*/
public class LevelManager {
    private int level = 1;
    private int lives = 3;

    private int highestScore = 0;
    public void increaseLevel(){ //level can only be increased by one at a time (or reset)
        level++;
    }
    public int getLevel(){
        return level;
    }
    public int getLives() { return lives; }

    public void resetGame() {
        resetScore();
        resetToFirstLevel();
        resetLives();
    }

    public void reduceLife() { lives -= 1; }

    private void resetLives() { lives = 3; }
    private void resetScore() { highestScore = 0; }
    private void resetToFirstLevel(){
        level = 1;
    }
    public void updateHighestScore(int highestScore) { this.highestScore += highestScore; }
}
