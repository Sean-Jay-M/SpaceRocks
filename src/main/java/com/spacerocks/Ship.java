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

    public void shoot(Bullet bullet){
        bullets.add(bullet);
    }

    public void moveBullets() {
        for (Bullet bullet: bullets) {
            bullet.move();
        }
    }

    public void removeAllBullets() { bullets.clear(); }

//    public void manageFiredBullets() {
//        for (Bullet bullet: bullets) {
//            bullets.remove(bullet);
//            screen.removeGameObject(bullet);
//        }
//    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}