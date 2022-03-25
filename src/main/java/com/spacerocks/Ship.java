package com.spacerocks;

import javafx.scene.shape.Polygon;

public class Ship extends GameObject {

    public Ship(double speed, double xposition, double yposition) {
        super(new Polygon(-20, -20, -5, -15, -20, 20), speed, xposition, yposition);
    }
}
