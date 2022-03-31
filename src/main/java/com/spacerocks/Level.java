package com.spacerocks;

/*
a Level object would be created when the game is started
reset the object on losing back to 1
increase when level has been won
get level at start of new level gives number of asteroids to spawn
*/
public class Level {
    private int level;

    public Level(){ //can only create object with level = 1
        level = 1;
    }

    public void increaseLevel(){ //level can only be increased by one at a time (or reset)
        level++;
    }

    public int getLevel(){
        return level;
    }

    public void resetLevel(){
        level = 1;
    }
}
