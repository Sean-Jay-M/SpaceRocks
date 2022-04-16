package com.spacerocks.gameobjects;
import com.spacerocks.main.Screen;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

import static com.spacerocks.main.Screen.getScreenWidth;

public class Ship extends GameObject {
    private boolean thrusting;
    // save bullets in ship object
    private ArrayList<Bullet> bullets;
    private final double maxSpeed = 1.5;
    private final double acceleration = 0.01;
    private double swiftX;
    private double swiftY;
    private boolean isInvincible = false;
    private int invincibilityTimer = 100;
    Image image = new Image("file:images/spaceship.jpg");
    ImagePattern pattern = new ImagePattern(image);



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

        polygon.setFill(pattern);
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

    public void toggleInvincibility() {
        isInvincible = !isInvincible;
    }

    public boolean getIsInvincible() {
        return isInvincible;
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

    public void shoot() {
        // create new bullet
        Bullet bullet = new Bullet((int)getCurrentXPosition(),(int)getCurrentYPosition(), getSpeed() + 2.0);
        bullet.getPolygon().setRotate(getPolygon().getRotate());

        // add bullet to the arraylist in ship class
        addBullet(bullet);
        Screen.getScreenInstance().getSpawner().spawnGameObject(bullet);
    }

    // move all the bullets
    public void moveBullets() {
        for (Bullet bullet: bullets) {
            bullet.move();
            if (bullet.isDecayed()) {
                removeBullet(bullet);
                if (spawnListener != null) spawnListener.onDespawn(bullet);
                break;
            }
        }
    }

    public void resetInvincibility() {
        invincibilityTimer = 500;
        isInvincible = false;
    }

    public void toggleVisibility() {
        if (polygon.isVisible()) {
            polygon.setVisible(false);
        } else {
            polygon.setVisible(true);
        }
    }

    public void playInvincibilityAnimation() {
        invincibilityTimer--;
        if (invincibilityTimer % 10 == 0) {
            toggleVisibility();
        }

        if (invincibilityTimer <= 0) {
            resetInvincibility();
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

    public void hyperspaceJump(){
        this.polygon.setTranslateX((int)(Math.random() * getScreenWidth()));
        this.polygon.setTranslateY((int)(Math.random() * getScreenWidth()));
    }
}