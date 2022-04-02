package com.spacerocks;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class Asteroid extends GameObject {
    AsteroidSize size;
    // Constructor for asteroid. The first parameter is the size of the asteroid, which will then automatically
    // populate the super class' constructor with the appropriate x points, y points and speed values.
    public Asteroid(AsteroidSize size, double xposition, double yposition) {
        super(new Polygon(size.x1,size.y1,size.x2,size.y2,size.x3,size.y3,size.x4,size.y4,size.x5,size.y5,size.x6,size.y6), size.speed, xposition, yposition);
        this.size = size;
        spawn_x = spawnX();
        spawn_y = spawnY();
    }

    public AsteroidSize getSize() {
        return size;
    }

    // create methods which will generate a value outside of the inner square
    // Get a random number, if the random number is in a range, generate another random number.
    // If that second random number is 1, move the first random number out of the range by addition
    //Otherwise do it by subtraction
    private double spawnX(){
        Random r = new Random();
        double rangeMin = 0.0;
        double rangeMax = Screen.getScreenWidth();
        double random_double = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        if ((random_double > (Screen.getScreenWidth() - 50)) && (random_double < (Screen.getScreenWidth() + 50))){
            int max = 1;
            int min = 0;
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            if(random_int == 1){
                random_double += 150;
            } else if (random_int == 0) {
                random_double -= 150;
            }
        }
        return random_double;
    }

    private double spawnY(){
        Random r = new Random();
        double rangeMin = 0.0;
        double rangeMax = Screen.getScreenHeight();
        double random_double = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        if ((random_double > (Screen.getScreenHeight() - 50)) && (random_double < (Screen.getScreenHeight() + 50))){
            int max = 1;
            int min = 0;
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            if(random_int == 1){
                random_double += 150;
            } else if (random_int == 0) {
                random_double -= 150;
            }
        }
        return random_double;
    }

    public void rotate(double angle){
        this.getPolygon().setRotate(angle);
    }

    // The below is just a quick demo showing the creation of a small asteroid would look like, with proof showing
    // that it works properly. Please feel free to uncomment and run it / play around with it, we will of course
    // be deleting it for the final product.

    // public static void main(String[] args) {
    //     Asteroid asteroid = new Asteroid(AsteroidSize.SMALL, 1, 2.0, 3.0);
    //     System.out.println(asteroid.getXpoints()[0]);
    // }
}
