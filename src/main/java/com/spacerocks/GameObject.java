package com.spacerocks;

//Import the relvent packages
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

//Import the relevent packages
public class GameObject{
    private double [] xpoints;
    private double [] ypoints;
    private int rotation;
    private double speed;

    //sets the variables for the game object 
    public GameObject(double[] xpoints, double[] ypoints, int rotation, double speed, double xposition, double yposition) {
        this.xpoints = xpoints;
        this.ypoints = ypoints;
        this.rotation = rotation;
        this.speed = speed;
        AffineTransform at = new AffineTransform();
        at.translate(xposition,yposition);

        for (int i=0; i<xpoints.length; i++){
            Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
            at.transform(p,p);
            xpoints[i] = p.getX();
            ypoints[i] = p.getY();
        }
    }
    //getter for the X point
    public double[] getXpoints() {
        return xpoints;
    }
    //Getter for the Y Point
    public double[] getYpoints() {
        return ypoints;
    }
    //Getter for the Rotation
    public int getRotation() {
        return rotation;
    }
    //Getter for the speed
    public double getSpeed() {
        return speed;
    }
    //Setter for the speed
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    //Get the path
    public Path2D getPath(){
        Path2D path = new Path2D.Double();
        path.moveTo(xpoints[0],ypoints[0]);
        for(int i=1; i<xpoints.length; i++){
            path.lineTo(xpoints[i],ypoints[i]);
        }
        path.lineTo(xpoints[0],ypoints[0]);
        return path;
    }
    //Get the center X point
    public double getCenterX(){
        double total = 0;
        for (double xpoint : this.xpoints) {
            total = total + xpoint;
        }
        return total / this.xpoints.length;
    }
    //Get the center Y point
    public double getCenterY(){
        double total = 0;
        for (double ypoint : this.ypoints) {
            total = total + ypoint;
        }
        return total / this.ypoints.length;
    }
    //Draw the path
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        Path2D path = this.getPath();
        g2d.draw(path);
    }
    //Turn the object
    public void turn(int angle){
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(angle), (xpoints[0]+xpoints[1]+xpoints[2])/3, (ypoints[0]+ypoints[1]+ypoints[2])/3);

        for (int i=0; i<xpoints.length; i++) {
            Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
            at.transform(p, p);
            xpoints[i] = p.getX();
            ypoints[i] = p.getY();
        }
        this.rotation = this.rotation + angle;
    }
    //Move the object
    public void move(){
        double swiftX = Math.cos(Math.toRadians(this.rotation));
        double swiftY = Math.sin(Math.toRadians(this.rotation));
        AffineTransform at = new AffineTransform();
        at.translate(swiftX*this.speed,swiftY*this.speed);

        for (int i=0; i<xpoints.length; i++){
            Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
            at.transform(p,p);
            xpoints[i] = p.getX();
            ypoints[i] = p.getY();
        }
    }
    //Check if object is in range
    public void checkInRange(){
        //set variables
        double totalX = 0;
        double totalY = 0;
        //add points together
        for (int i=0; i<xpoints.length; i++){
            totalX = totalX + xpoints[i];
            totalY = totalY + ypoints[i];
        }
        double positionX = totalX / xpoints.length;
        double positionY = totalY / ypoints.length;

        AffineTransform at = new AffineTransform();
        if (positionX < 0){
            at.translate(positionX+Screen.getScreenWidth(),0);
            for (int i=0; i<xpoints.length; i++){
                Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
                at.transform(p,p);
                xpoints[i] = p.getX();
                ypoints[i] = p.getY();
            }
        }
        if (positionX > Screen.getScreenWidth()){
            at.translate(-Screen.getScreenWidth(),0);
            for (int i=0; i<xpoints.length; i++){
                Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
                at.transform(p,p);
                xpoints[i] = p.getX();
                ypoints[i] = p.getY();
            }
        }
        if (positionY < 0){
            at.translate(0, positionY+Screen.getScreenHeight());
            for (int i=0; i<xpoints.length; i++){
                Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
                at.transform(p,p);
                xpoints[i] = p.getX();
                ypoints[i] = p.getY();
            }
        }
        if (positionY > Screen.getScreenHeight()){
            at.translate(0, -Screen.getScreenHeight());
            for (int i=0; i<xpoints.length; i++){
                Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
                at.transform(p,p);
                xpoints[i] = p.getX();
                ypoints[i] = p.getY();
            }
        }
    }

    //check if it has crashed.
    public boolean crash(GameObject obj){
        Area areaObj1 = new Area(this.getPath());
        Area areaObj2 = new Area(obj.getPath());
        areaObj1.intersect(areaObj2);
        return !areaObj1.isEmpty();

    }
}
