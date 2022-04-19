package com.spacerocks.gameobjects;

import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    private double distance;
    private final double decayValue = 1.5;
    private boolean used = false;

    public Bullet(int xPosition, int yPosition, double speed){
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2),speed);
        distance = 0.0;
        spawnX = xPosition;
        spawnY = yPosition;
    }

    public void setUsed() { used = true; }

    @Override
    public void move() {
        super.move();
        this.distance = this.distance + 1.0 / 60.0;
    }

    public boolean isDecayed() {
        return distance > decayValue;
    }

    @Override
    public boolean hasCollided(GameObject object){
        if (used) return false;
        return this.getPolygon().getBoundsInParent().intersects(object.getPolygon().getBoundsInParent());
    }

}
