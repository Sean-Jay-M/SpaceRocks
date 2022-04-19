package com.spacerocks.gameobjects;

import javafx.scene.shape.Polygon;

public class Bullet extends GameObject{
    // the distance that the bullet has travelled
    private double distance;
    // the maximum distance that a bullet can travel
    private final double decayValue = 1.5;
    private boolean used = false;

    public Bullet(int xPosition, int yPosition, double speed){
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2),speed);
        distance = 0.0;
        spawnX = xPosition;
        spawnY = yPosition;
    }

    //Setter for used
    public void setUsed() { used = true; }

    //Move the bullet
    @Override
    public void move() {
        super.move();
        this.distance = this.distance + 1.0 / 60.0;
    }

    // If the bullet's travel distance is over decayValue, it will return true
    public boolean isDecayed() {
        return distance > decayValue;
    }

    // Check if the bullet is crashed
    @Override
    public boolean hasCollided(GameObject object){
        if (used) return false;
        return this.getPolygon().getBoundsInParent().intersects(object.getPolygon().getBoundsInParent());
    }

}
