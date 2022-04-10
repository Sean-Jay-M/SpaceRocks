package com.spacerocks.gameobjects;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;

import static com.spacerocks.main.Screen.getScreenHeight;
import static com.spacerocks.main.Screen.getScreenWidth;

public class AlienShip extends GameObject {
    private int calls = 0;
    private final ArrayList<Bullet> bullets;
    private int bulletCalls = 0;

    public AlienShip() {
        super(new Polygon(-15, -15, 15, -15, 15, 15, -15, 15), 1);
            //AlienShip is a square for now with constant speed 2
        spawnX = (int)(Math.random() * getScreenWidth());
        spawnY = (int)(Math.random() * getScreenHeight());

        bullets = new ArrayList<>();
    }

    public void changeDirection(){
        calls++;
        int TURN_FREQUENCY = 100;
        if (calls >= TURN_FREQUENCY){
            this.turn((int)(Math.random() * 360));
            calls = 0;
        }
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet){
        bullets.remove(bullet);
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public boolean checkBulletCooldown(){
        if (bulletCalls >= 20){
             bulletCalls = 0;
             return true;
        } else {
            bulletCalls++;
            return false;
        }
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



}
