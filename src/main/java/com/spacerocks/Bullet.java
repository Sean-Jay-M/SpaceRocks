package com.spacerocks;

import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    private double distance;

    public Bullet(int xposition, int yposition){
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2),1, xposition, yposition);
        distance = 0.0;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public void move() {
        super.move();
        this.distance = this.distance + 1.0 / 60.0;
    }
}
