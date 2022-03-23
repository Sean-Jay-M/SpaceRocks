package com.spacerocks;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class GameObject{
    private double [] xpoints;
    private double [] ypoints;
    private int rotation;
    private double speed;

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

    public double[] getXpoints() {
        return xpoints;
    }

    public double[] getYpoints() {
        return ypoints;
    }

    public int getRotation() {
        return rotation;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Path2D getPath(){
        Path2D path = new Path2D.Double();
        path.moveTo(xpoints[0],ypoints[0]);
        for(int i=1; i<xpoints.length; i++){
            path.lineTo(xpoints[i],ypoints[i]);
        }
        path.lineTo(xpoints[0],ypoints[0]);
        return path;
    }

    public double getCenterX(){
        double total = 0;
        for (double xpoint : this.xpoints) {
            total = total + xpoint;
        }
        return total / this.xpoints.length;
    }

    public double getCenterY(){
        double total = 0;
        for (double ypoint : this.ypoints) {
            total = total + ypoint;
        }
        return total / this.ypoints.length;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        Path2D path = this.getPath();
        g2d.draw(path);
    }

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

    public void checkInRange(){
        double totalX = 0;
        double totalY = 0;
        for (int i=0; i<xpoints.length; i++){
            totalX = totalX + xpoints[i];
            totalY = totalY + ypoints[i];
        }
        double positionX = totalX / xpoints.length;
        double positionY = totalY / ypoints.length;

        AffineTransform at = new AffineTransform();
        if (positionX < 0){
            at.translate(positionX+Screen.SCREEN_WIDTH,0);
            for (int i=0; i<xpoints.length; i++){
                Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
                at.transform(p,p);
                xpoints[i] = p.getX();
                ypoints[i] = p.getY();
            }
        }
        if (positionX > Screen.SCREEN_WIDTH){
            at.translate(-Screen.SCREEN_WIDTH,0);
            for (int i=0; i<xpoints.length; i++){
                Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
                at.transform(p,p);
                xpoints[i] = p.getX();
                ypoints[i] = p.getY();
            }
        }
        if (positionY < 0){
            at.translate(0, positionY+Screen.SCREEN_HEIGHT);
            for (int i=0; i<xpoints.length; i++){
                Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
                at.transform(p,p);
                xpoints[i] = p.getX();
                ypoints[i] = p.getY();
            }
        }
        if (positionY > Screen.SCREEN_HEIGHT){
            at.translate(0, -Screen.SCREEN_HEIGHT);
            for (int i=0; i<xpoints.length; i++){
                Point2D p = new Point2D.Double(xpoints[i], ypoints[i]);
                at.transform(p,p);
                xpoints[i] = p.getX();
                ypoints[i] = p.getY();
            }
        }
    }


    public boolean crash(GameObject obj){
        Area areaObj1 = new Area(this.getPath());
        Area areaObj2 = new Area(obj.getPath());
        areaObj1.intersect(areaObj2);
        return !areaObj1.isEmpty();

    }
}
