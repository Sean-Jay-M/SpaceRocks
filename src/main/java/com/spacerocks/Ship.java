package com.spacerocks;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Ship extends GameObject {
    private boolean thrusting;
    // save bullets in ship object
    private ArrayList<Bullet> bullets;
    private final double maxSpeed = 1.5;
    private final double acceleration = 0.01;
    private double swiftX;
    private double swiftY;

    public int getTurnSpeedLeft() {
        return turnSpeedLeft;
    }

    public int getTurnSpeedRight() {
        return turnSpeedRight;
    }

    private final int turnSpeedLeft = -2;
    private final int turnSpeedRight = 2;

    public Ship() {
        super(new Polygon(-10, -10, 20, 0, -10, 10), 0);
        this.thrusting = false;

        angle = 2;
        anchor = new Point2D(0, 0);

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
        swiftX = getRotateX() * 0.01;
        swiftY = getRotateY() * 0.01;
        Point2D speedPosition = new Point2D(swiftX, swiftY);

        if (getCurrentSpeed() <= maxSpeed) {
            anchor = anchor.add(speedPosition);
        } else {
            anchor = anchor.subtract(speedPosition).normalize().multiply(1.5);
        }
    }

    private double getCurrentSpeed() {
        Point2D currentPosition = new Point2D(this.polygon.getTranslateX(), this.polygon.getTranslateY());
        Point2D projectedPosition = new Point2D(this.polygon.getTranslateX() + anchor.getX(), this.polygon.getTranslateY() + anchor.getY());
        return currentPosition.distance(projectedPosition);
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

    public void removeBullet(Bullet bullet) { bullets.remove(bullet); }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void respawn(){
        anchor = new Point2D(0, 0);
        this.getPolygon().setTranslateX(spawnX);
        this.getPolygon().setTranslateY(spawnY);
        this.getPolygon().setRotate(270);
    }


    @Override
    public void move(){
        this.polygon.setTranslateX(this.polygon.getTranslateX() + anchor.getX());
        this.polygon.setTranslateY(this.polygon.getTranslateY() + anchor.getY());

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