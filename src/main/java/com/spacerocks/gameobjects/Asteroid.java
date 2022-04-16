package com.spacerocks.gameobjects;
import com.spacerocks.main.Screen;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Random;

public class Asteroid extends GameObject {

    public static ArrayList<Asteroid> asteroids = new ArrayList<>();

    AsteroidSize size;
    Random random = new Random();
    // Constructor for asteroid. The first parameter is the size of the asteroid, which will then automatically
    // populate the super class' constructor with the appropriate x points, y points and speed values.

    Image image = new Image("file:images/asteroid.jpg");
    ImagePattern pattern = new ImagePattern(image);

    public static void moveAsteroids() {
        for (Asteroid asteroid: asteroids)
            asteroid.move();
    }
    
    public Asteroid(AsteroidSize size) {
        super(new Polygon(size.x1,size.y1,size.x2,size.y2,size.x3,size.y3,size.x4,size.y4,size.x5,size.y5,size.x6,size.y6, size.x7,size.y7,size.x8,size.y8), (Math.random() * (size.max - size.min) + size.min));
        this.size = size;
        rotate(random.nextDouble(1, 360));
        spawnX = calculateRandomSpawnX();
        spawnY = calculateRandomSpawnY();
        polygon.setFill(pattern);
        asteroids.add(this);
    }

    public Asteroid(AsteroidSize size, double currentAsteroidPosX, double currentAsteroidPosY) {
        super(new Polygon(size.x1,size.y1,size.x2,size.y2,size.x3,size.y3,size.x4,size.y4,size.x5,size.y5,size.x6,size.y6, size.x7,size.y7,size.x8,size.y8), (Math.random() * (size.max - size.min) + size.min));
        this.size = size;
        spawnX = currentAsteroidPosX;
        spawnY = currentAsteroidPosY;
        polygon.setFill(pattern);
        rotate(random.nextDouble(1, 360));
        asteroids.add(this);
    }

    public AsteroidSize getSize() {
        return size;
    }

    // create methods which will generate a value outside the inner square
    // Get a random number, if the random number is in a range, generate another random number.
    // If that second random number is 1, move the first random number out of the range by addition
    //Otherwise do it by subtraction
    private double calculateRandomSpawnX(){
        Random r = new Random();
        double rangeMin = 0.0;
        double rangeMax = Screen.getScreenWidth();
        double randomDouble = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            if ((randomDouble > ((Screen.getScreenWidth() /2) - 120)) && (randomDouble < ((Screen.getScreenWidth() /2) + 120))){
                randomDouble = setInRange(randomDouble);
      }
        return randomDouble;
    }

    private double calculateRandomSpawnY(){
        Random r = new Random();
        double rangeMin = 0.0;
        double rangeMax = Screen.getScreenHeight();
        double randomDouble = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        if ((randomDouble > ((Screen.getScreenHeight() / 2) - 120)) && (randomDouble < ((Screen.getScreenHeight() /2) + 120))){
            randomDouble = setInRange(randomDouble);
        }
        return randomDouble;
    }

    private double setInRange(double randomDouble) {
        int max = 1;
        int min = 0;
        int randomInt = (int)Math.floor(Math.random()*(max-min+1)+min);
        if(randomInt == 1){
            randomDouble += 150;
        } else if (randomInt == 0) {
            randomDouble -= 150;
        }
        return randomDouble;
    }

    public void rotate(double angle){
        this.getPolygon().setRotate(angle);
    }
    public static Asteroid getAsteroidPieces(Asteroid asteroid) {
        if (asteroid.getSize() == AsteroidSize.BIG) {
            return new Asteroid(AsteroidSize.MEDIUM, asteroid.getCurrentXPosition(), asteroid.getCurrentYPosition());
        }
        return new Asteroid(AsteroidSize.SMALL, asteroid.getCurrentXPosition(), asteroid.getCurrentYPosition());}

}
