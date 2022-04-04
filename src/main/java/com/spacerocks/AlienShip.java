package com.spacerocks;

import javafx.scene.shape.Polygon;

import static com.spacerocks.Screen.getScreenHeight;
import static com.spacerocks.Screen.getScreenWidth;

public class AlienShip extends GameObject {

    public AlienShip(double speed, double xposition, double yposition) {
        super(new Polygon(-15, -15,
                15, -15,
                15, 15,
                -15, 15), 2, xposition, yposition);

        spawn_x = spawnCoord(getScreenWidth());
        spawn_x = spawnCoord(getScreenHeight());
    }

    private int spawnCoord(int upperBound){
        return (int)(Math.random() * upperBound);
    }


}
