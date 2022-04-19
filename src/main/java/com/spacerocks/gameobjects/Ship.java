package com.spacerocks.gameobjects;
import com.spacerocks.main.Screen;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

import static com.spacerocks.main.Screen.getScreenWidth;

public class Ship extends GameObject {
    // Bullets that the ship has fired
    private ArrayList<Bullet> bullets = new ArrayList<>();

    // Variables relating to position of ship
    private final double maxSpeed = 1.5;
    private double angularVelocityX;
    private double angularVelocityY;
    Point2D anchor = new Point2D(0, 0);

    // Variables relating to invincibility mode
    private boolean isInvincible = false;
    private int invincibilityTimer = 100;
    Image image = new Image("file:images/spaceship.jpg");
    ImagePattern pattern = new ImagePattern(image);



    public int getTurnSpeedLeft() { return turnSpeedLeft; }
    public int getTurnSpeedRight() { return turnSpeedRight; }

    // Setting turn speed. Should remain consistent throughout the game.
    private final int turnSpeedLeft = -2;
    private final int turnSpeedRight = 2;

    public Ship() {
        super(new Polygon(-10, -10, 20, 0, -10, 10), 0);

        polygon.setFill(pattern);
        angle = 2;

        // Set default position of ship
        setSpawnX(250);
        setSpawnY(250);
        this.turn(270);
    }

    // METHODS RELATING TO MOVEMENT

    // Accelerate the ship.
    public void accelerate(){
        Point2D speedPosition = calculateNewPosition();

        // If not at max speed, move to next position. If at max speed, stay at speed 1.5.
        if (getSpeed() <= maxSpeed) {
            anchor = anchor.add(speedPosition);
        } else {
            anchor = anchor.subtract(speedPosition).normalize().multiply(maxSpeed);
        }
    }

    // Calculating where the ship will be in the next frame.
    private Point2D calculateNewPosition() {
        angularVelocityX = getRotateX() * 0.01;
        angularVelocityY = getRotateY() * 0.01;
        return new Point2D(angularVelocityX, angularVelocityY);
    }

    // Overriding speed getter because it is tied to the current movement vector in reference to the angular velocity.
    @Override
    public double getSpeed() {
        Point2D currentPosition = new Point2D(this.polygon.getTranslateX(), this.polygon.getTranslateY());
        Point2D projectedPosition = new Point2D(this.polygon.getTranslateX() + anchor.getX(), this.polygon.getTranslateY() + anchor.getY());
        return currentPosition.distance(projectedPosition);
    }

    // Movement method overridden so that we can tie speed to angular velocity.
    // Movement working via 2D anchor point source: https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
    // The above source allowed us to more easily tie the movement to angular velocity than if we were just using getTranslate.
    @Override
    public void move(){
        this.polygon.setTranslateX(this.polygon.getTranslateX() + anchor.getX());
        this.polygon.setTranslateY(this.polygon.getTranslateY() + anchor.getY());

        // stay in the window
        super.checkInRange();
    }

    // Using trigonometry to get rotation value, to be used to calculate angular velocity
    public double getRotateX() { return Math.cos(Math.toRadians(this.polygon.getRotate())); }
    public double getRotateY() { return Math.sin(Math.toRadians(this.polygon.getRotate())); }


    // METHODS RELATING TO SHOOTING

    // Getter for all bullets
    public ArrayList<Bullet> getBullets() { return bullets; }

    // Add and remove bullets from array.
    public void addBullet(Bullet bullet){ bullets.add(bullet); }
    public void removeBullet(Bullet bullet) { bullets.remove(bullet); }

    // Create new bullet at appropriate position and speed and spawn into the environment.
    public void shoot() {
        double bulletSpeed = getSpeed() + 1.5;
        Bullet bullet = new Bullet((int)getCurrentXPosition(),(int)getCurrentYPosition(), bulletSpeed);
        bullet.getPolygon().setRotate(getPolygon().getRotate());

        // Add bullet to the array in ship class.
        addBullet(bullet);
        Screen.getScreenInstance().getSpawner().spawnGameObject(bullet);
    }

    // Ensure that the bullets in the scene are moving.
    public void moveBullets() {
        for (Bullet bullet: bullets) {
            bullet.move();
            if (bullet.isDecayed()) {
                removeBullet(bullet);
                Screen.getScreenInstance().getSpawner().despawnGameObject(bullet);
                break;
            }
        }
    }

    //  METHODS RELATING TO INVINCIBILITY

    public void toggleInvincibility() { isInvincible = !isInvincible; }
    public boolean getIsInvincible() { return isInvincible; }

    // Set invincibility off
    public void resetInvincibility() {
        invincibilityTimer = 100;
        isInvincible = false;
    }

    // Toggle visibility on the ship on screen, to be used with invincibility animation.
    public void toggleVisibility() {
        if (polygon.isVisible()) {
            polygon.setVisible(false);
        } else {
            polygon.setVisible(true);
        }
    }

    // Flash ship on and off while player is invincible
    public void playInvincibilityAnimation() {
        invincibilityTimer--;
        if (invincibilityTimer % 10 == 0) {
            toggleVisibility();
        }

        if (invincibilityTimer <= 0) {
            resetInvincibility();
        }
    }

    // Respawn method will bring the ship back to the center of the screen.
    public void respawn(){
        anchor = new Point2D(0, 0);
        this.getPolygon().setTranslateX(spawnX);
        this.getPolygon().setTranslateY(spawnY);
        this.getPolygon().setRotate(270);
    }



    //  Setting coordinates for hyperspace jump
    public void activateHyperSpaceJump(){
        this.polygon.setTranslateX((int)(Math.random() * getScreenWidth()));
        this.polygon.setTranslateY((int)(Math.random() * getScreenWidth()));
    }
}