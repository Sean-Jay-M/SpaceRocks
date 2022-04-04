package com.spacerocks;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    private double distance;
    private boolean used = false;

    public Bullet(int xposition, int yposition){
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2),1);
        distance = 0.0;
        spawnX = xposition;
        spawnY = yposition;
    }

    public double getDistance() {
        return distance;
    }

    public void setUsed() { used = true; }

    @Override
    public void move() {
        super.move();
        this.distance = this.distance + 1.0 / 60.0;
    }

    public boolean isDecayed() {
        return distance > 10;
    }

    @Override
    public boolean hasCollided(GameObject object){
        if (used) return false;
        return this.getPolygon().getBoundsInParent().intersects(object.getPolygon().getBoundsInParent());
    }

}
