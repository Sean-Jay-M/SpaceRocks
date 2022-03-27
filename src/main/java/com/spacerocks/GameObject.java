package com.spacerocks;
//testing comment
//Import the relvent packages
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

//Import the relevent packages
public class GameObject{
    // replace xpoints, ypoints, rotation by polygon, so we can remove AffineTransform
    private Polygon polygon;
    private double speed;

    //sets the variables for the game object 
    public GameObject(Polygon polygon, double speed, double xposition, double yposition) {
        this.polygon = polygon;
        this.speed = speed;

        // set game object white
        this.polygon.setFill(Color.WHITE);

        // set initial location
        polygon.setTranslateX(xposition);
        polygon.setTranslateY(yposition);
    }

    public Polygon getPolygon() {
        return polygon;
    }

    //Getter for the speed
    public double getSpeed() {
        return speed;
    }

    //Setter for the speed
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    //Turn the object
    public void turn(int angle){
        this.polygon.setRotate(this.polygon.getRotate() + angle);
    }

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
    private void checkInRange(){
        if (this.polygon.getTranslateX() < 0) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() + Screen.getScreenWidth());
        }

        if (this.polygon.getTranslateX() > Screen.getScreenWidth()) {
            this.polygon.setTranslateX(this.polygon.getTranslateX() % Screen.getScreenWidth());
        }

        if (this.polygon.getTranslateY() < 0) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() + Screen.getScreenHeight());
        }

        if (this.polygon.getTranslateY() > Screen.getScreenHeight()) {
            this.polygon.setTranslateY(this.polygon.getTranslateY() % Screen.getScreenHeight());
        }
    }

    //check if it has crashed.
//    public boolean crash(GameObject obj){
//        Area areaObj1 = new Area(this.getPath());
//        Area areaObj2 = new Area(obj.getPath());
//        areaObj1.intersect(areaObj2);
//        return !areaObj1.isEmpty();
//
//    }
}
