package com.spacerocks;

import javafx.scene.shape.Polygon;

import static com.spacerocks.Screen.getScreenHeight;
import static com.spacerocks.Screen.getScreenWidth;

public class AlienShip extends GameObject {
    private int calls = 0;

    public AlienShip() {
        super(new Polygon(-15, -15, 15, -15, 15, 15, -15, 15), 2);
            //AlienShip is a square for now with constant speed 2
        spawnX = (int)(Math.random() * getScreenWidth());
        spawnY = (int)(Math.random() * getScreenHeight());
    }

    public void changeDirection(){
        calls++;
        int TURN_FREQUENCY = 100;
        if (calls >= TURN_FREQUENCY){
            this.turn((int)(Math.random() * 360));
            calls = 0;
        }
    }



}
