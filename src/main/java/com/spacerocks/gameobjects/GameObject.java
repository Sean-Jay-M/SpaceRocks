package com.spacerocks.gameobjects;

//Import the relevant packages
import com.spacerocks.main.Screen;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

//Import the relevant packages
public abstract class GameObject{

    protected final Polygon polygon;
    private double speed;
    protected double spawnX;
    protected double spawnY;
    protected int angle;

    //sets the variables for the game object
    public GameObject(Polygon polygon, double speed) {
        this.polygon = polygon;
        this.speed = speed;

        // set game object white
        this.polygon.setFill(Color.WHITE);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    //Getter for the spawnX
    public double getSpawnX(){return spawnX;}

    //Getter for the spawnY
    public double getSpawnY() {return spawnY;}

    //Setter for the spawnX
    public void setSpawnX(double spawnX) { this.spawnX = spawnX; }

    //Setter for the spawnY
    public void setSpawnY(double spawnY) { this.spawnY = spawnY; }

    //Getter for the currentX
    public double getCurrentXPosition() { return polygon.getTranslateX(); }

    //Getter for the currentY
    public double getCurrentYPosition() { return polygon.getTranslateY(); }

    //Getter for the speed
    public double getSpeed() {
        return speed;
    }

    //Rotate the object
    public void turn(int angle) { this.polygon.setRotate(this.polygon.getRotate() + angle); }

    //Move the object
    public void move(){
        // Direction and movement is according to the angle and speed
        // Source: https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
        double swiftX = Math.cos(Math.toRadians(this.polygon.getRotate()));
        double swiftY = Math.sin(Math.toRadians(this.polygon.getRotate()));

        this.polygon.setTranslateX(this.polygon.getTranslateX() + swiftX * this.speed);
        this.polygon.setTranslateY(this.polygon.getTranslateY() + swiftY * this.speed);

        // stay in the window
        checkInRange();
    }

    // Check if object is in range
    // Source for Implementation of Looping Objects:  https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
    protected void checkInRange(){
        // if the object's width is below 0, it will show up in the opposite.
        if (this.polygon.getBoundsInParent().getCenterX() < 0) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() + Screen.getScreenWidth());
        }

        // if the object's width is above screen's width, it will show up in the opposite.
        if (this.polygon.getBoundsInParent().getCenterX() > Screen.getScreenWidth()) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() % Screen.getScreenWidth());
        }

        // if the object's height is below 0, it will show up in the opposite.
        if (this.polygon.getBoundsInParent().getCenterY() < 0) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() + Screen.getScreenHeight());
        }

        // if the object's height is above screen's height, it will show up in the opposite.
        if (this.polygon.getBoundsInParent().getCenterY() > Screen.getScreenHeight()) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() % Screen.getScreenHeight());
        }
    }

    public boolean hasCollided(GameObject object){
        // Check coordinates bounded by both objects and if there is overlap returns true
        // Source: https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
        Shape hitBox = Shape.intersect(this.polygon, object.getPolygon());

        // By setting the hitbox to the width, we get a significantly more accurate measurement.
        // Hitboxes without perform quite poorly.
        return hitBox.getBoundsInLocal().getWidth() != -1;
    }

}
