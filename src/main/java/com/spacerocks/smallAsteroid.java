package com.spacerocks;

import javafx.scene.shape.Polygon;
public class smallAsteroid extends GameObject {
    public smallAsteroid(double speed, double xposition, double yposition) {
        //current shape is a hexagon scaled, I will regard this scale as 25%
        super(new Polygon(50.0, 12.5,
                100.0, 12.5,
                112.5, 37.5,
                100.0, 62.5,
                50.0, 62.5,
                37.5, 37.5 ), speed, xposition, yposition);
    }
}
