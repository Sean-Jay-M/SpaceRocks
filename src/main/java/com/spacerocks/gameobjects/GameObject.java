package com.spacerocks.gameobjects;

//Import the relevant packages
import com.spacerocks.main.Screen;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

//Import the relevant packages
public abstract class GameObject{

    protected final Polygon polygon;
    private double speed;
    //initial spawning place
    protected double spawnX;
    protected double spawnY;
    protected int angle;
    protected Point2D anchor;

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

    //Getter for the spawn_x
    public double getSpawnX(){return spawnX;}

    //Getter for the spawn_y
    public double getSpawnY() {return spawnY;}

    public double getCurrentXPosition() { return polygon.getTranslateX(); }
    public double getCurrentYPosition() { return polygon.getTranslateY(); }

    //Getter for the speed
    public double getSpeed() {
        return speed;
    }

    public void turn(int angle) { this.polygon.setRotate(this.polygon.getRotate() + angle); }

    //Move the object
    public void move(){
        double swiftX = Math.cos(Math.toRadians(this.polygon.getRotate()));
        double swiftY = Math.sin(Math.toRadians(this.polygon.getRotate()));

        this.polygon.setTranslateX(this.polygon.getTranslateX() + swiftX * this.speed);
        this.polygon.setTranslateY(this.polygon.getTranslateY() + swiftY * this.speed);

        // stay in the window
        checkInRange();
    }

    //Check if object is in range
    protected void checkInRange(){
        if (this.polygon.getBoundsInParent().getCenterX() < 0) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() + Screen.getScreenWidth());
        }

        if (this.polygon.getBoundsInParent().getCenterX() > Screen.getScreenWidth()) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() % Screen.getScreenWidth());
        }

        if (this.polygon.getBoundsInParent().getCenterY() < 0) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() + Screen.getScreenHeight());
        }

        if (this.polygon.getBoundsInParent().getCenterY() > Screen.getScreenHeight()) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() % Screen.getScreenHeight());
        }
    }

    public boolean hasCollided(GameObject object){
        //checks coordinates bounded by both objects and if there is overlap returns true
        // Source: https://java-programming.mooc.fi/part-14/3-larger-application-asteroids
        Shape hitBox = Shape.intersect(this.polygon, object.getPolygon());
        return hitBox.getBoundsInLocal().getWidth() != -1;
    }

}
