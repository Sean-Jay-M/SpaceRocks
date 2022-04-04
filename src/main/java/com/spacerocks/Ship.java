package com.spacerocks;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Ship extends GameObject {
    private boolean thrusting;
    // save bullets in ship object
    private ArrayList<Bullet> bullets;
    private final double maxSpeed = 1.5;
    private final double acceleration = 0.01;

    public int getTurnSpeedLeft() {
        return turnSpeedLeft;
    }

    public int getTurnSpeedRight() {
        return turnSpeedRight;
    }

    private final int turnSpeedLeft = -2;
    private final int turnSpeedRight = 2;
    private double swiftX = 0;
    private double swiftY = 0;

    public Ship() {
        super(new Polygon(-10, -10, 20, 0, -10, 10), 0);
        this.thrusting = false;

        angle = 2;

        //Half screen later
        spawnX = 250;
        spawnY = 250;

        // change initial angle
        this.turn(270);
        bullets = new ArrayList<>();
    }

    public boolean isThrusting() {
        return thrusting;
    }

    public void setThrusting(boolean thrusting) {
        this.thrusting = thrusting;
    }

    public void accelerate(){
        if (this.getSpeed() < maxSpeed){
            this.setSpeed(this.getSpeed() + acceleration);
        }
    }

    public void slowDown(){
        if (this.getSpeed() > 0){
            this.setSpeed(this.getSpeed() - 0.1);
        } else if (this.getSpeed() < 0) {
            this.setSpeed(0);
        }
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

    // move all the bullets
    public void shoot() {
        for (Bullet bullet: bullets) {
            bullet.move();
            if (bullet.isDecayed()) {
                removeBullet(bullet);
                if (spawnListener != null) spawnListener.onDespawn(bullet);
                break;
            }
        }
    }

    // if the ship is thrusting, the ship will accelerate. otherwise, it will slow down.
    public void thrust() {
        if (isThrusting()) {
            accelerate();
        }
    }

    public void removeBullet(Bullet bullet) { bullets.remove(bullet); }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void respawn(){
        super.setSpeed(0);
        this.getPolygon().setTranslateX(spawnX);
        this.getPolygon().setTranslateY(spawnY);
        this.getPolygon().setRotate(270);
    }

    @Override
    public void move(){
        if (isThrusting()) {
            swiftX = getRotateX();
            swiftY = getRotateY();
        }

        this.polygon.setTranslateX(this.polygon.getTranslateX() + swiftX * this.getSpeed());
        this.polygon.setTranslateY(this.polygon.getTranslateY() + swiftY * this.getSpeed());

        // stay in the window
        super.checkInRange();
    }

    public double getRotateX() {
        return Math.cos(Math.toRadians(this.polygon.getRotate()));
    }

    public double getRotateY() {
        return Math.sin(Math.toRadians(this.polygon.getRotate()));
    }
}