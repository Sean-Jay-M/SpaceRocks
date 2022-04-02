package com.spacerocks;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Ship extends GameObject {
    private boolean thrusting;
    // save bullets in ship object
    private ArrayList<Bullet> bullets;

    public Ship( double xposition, double yposition) {
            super(new Polygon(-10, -10, 20, 0, -10, 10), 0, xposition, yposition);
            this.thrusting = false;

            //Half screen lateer
            spawn_x = 250;
            spawn_y = 250;

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
        if (this.getSpeed() < 6){
            this.setSpeed(this.getSpeed() + 1);
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
                if (despawnListener != null) despawnListener.onDespawn(bullet);
                break;
            }
        }
    }

    // if the ship is thrusting, the ship will accelerate. otherwise, it will slow down.
    public void thrust() {
        if (isThrusting()) {
            accelerate();
            return;
        }
        slowDown();
    }

    public void removeBullet(Bullet bullet) { bullets.remove(bullet); }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}