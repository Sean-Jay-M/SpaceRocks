package com.spacerocks;

import javafx.scene.shape.Polygon;

import static com.spacerocks.Screen.getScreenHeight;
import static com.spacerocks.Screen.getScreenWidth;

public class AlienShip extends GameObject {

    public AlienShip() {
        super(new Polygon(-15, -15, 15, -15, 15, 15, -15, 15), 2);
            //AlienShip is a square for now
        spawnX = spawnCoord(getScreenWidth());
        spawnY = spawnCoord(getScreenHeight());
    }

    private int spawnCoord(int upperBound){
        return (int)(Math.random() * upperBound);
    }


}
