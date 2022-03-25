package com.spacerocks;

import javafx.scene.shape.Polygon;

public class mediumAsteroid extends GameObject {
    public mediumAsteroid(double speed, double xposition, double yposition) {
        //current shape is a hexagon scaled, I will regard this scale as 60%%
        super(new Polygon(120.0, 30.0,
                240.0, 30.0,
                270.0, 90.0,
                240.0, 150.0,
                120.0, 150.0,
                90.0, 90.0), speed, xposition, yposition);
    }
}

