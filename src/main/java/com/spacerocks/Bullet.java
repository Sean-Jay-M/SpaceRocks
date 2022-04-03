package com.spacerocks;

import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    private double distance;

    public Bullet(int xposition, int yposition){
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2),1, 250, 250);
        distance = 0.0;
        spawnX = xposition;
        spawnY = yposition;
        System.out.println(spawnX);
        System.out.println(spawnY);
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public void move() {
        super.move();
        this.distance = this.distance + 1.0 / 60.0;
    }

    public boolean isDecayed() {
        return distance > 10;
    }
}
