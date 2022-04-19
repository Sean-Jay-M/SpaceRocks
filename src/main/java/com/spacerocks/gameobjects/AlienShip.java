package com.spacerocks.gameobjects;

import com.spacerocks.main.Screen;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

import static com.spacerocks.main.Screen.getScreenHeight;
import static com.spacerocks.main.Screen.getScreenWidth;

public class AlienShip extends GameObject {

    private int calls = 0; //counter for changing direction
    private final ArrayList<Bullet> bullets; //instantiate array that will store created bullets
    private int bulletCalls = 0; //counter to prevent continuous bullet stream
    Image image = new Image("file:images/alienship.jpg");
    ImagePattern pattern = new ImagePattern(image);

    public AlienShip() {
        super(new Polygon(-15, -15, 15, -15, 15, 15, -15, 15), 1); //creates instance of GameObject
            //AlienShip is a square with constant speed 1
        setSpawnX((int)(Math.random() * getScreenWidth())); //spawn pint is not set
        setSpawnY((int)(Math.random() * getScreenHeight()));
        polygon.setFill(pattern); //place image on alien ship
        bullets = new ArrayList<>();
    }

    public void changeDirection(){
        calls++;
        final int TURN_FREQUENCY = 100; //when counter reaches this number the alien ship rotates
        if (calls >= TURN_FREQUENCY){
            this.turn((int)(Math.random() * 360)); //will rotate a random amount
            calls = 0; //reset counter back to 0
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
        if (bulletCalls >= 20){ //when counter hits 20 it returns true (to fire a bullet)
             bulletCalls = 0;
             return true;
        } else { //otherwise increment counter and tell it not to fire bullet yet
            bulletCalls++;
            return false;
        }
    }

    // move all the bullets
    public void shoot() {
        for (Bullet bullet: bullets) {
            bullet.move();
            if (bullet.isDecayed()) { //remove bullet when it has travelled its max distance
                removeBullet(bullet);
                Screen.getScreenInstance().getSpawner().despawnGameObject(bullet);
                break;
            }
        }
    }
}
