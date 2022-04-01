package com.spacerocks;

import javafx.scene.shape.Polygon;

public class AlienShip extends GameObject {


    public AlienShip(double speed, double xposition, double yposition) {
        super(new Polygon(-15, -15, 15, -15, 15, 15, 15, -15), speed, xposition, yposition);
    }
}
