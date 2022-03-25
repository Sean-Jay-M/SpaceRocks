package com.spacerocks;

import javafx.scene.shape.Polygon;

public class largeAsteroid extends GameObject {
    public largeAsteroid(double speed, double xposition, double yposition) {
        //current shape is a hexagon scaled, I will regard this scale as 100%
        super(new Polygon(200.0, 50.0,
                400.0, 50.0,
                450.0, 150.0,
                400.0, 250.0,
                200.0, 250.0,
                150.0, 150.0 ), speed, xposition, yposition);
    }
}
