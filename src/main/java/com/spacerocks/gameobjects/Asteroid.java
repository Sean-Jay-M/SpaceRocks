package com.spacerocks.gameobjects;
import com.spacerocks.main.Screen;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Random;

public class Asteroid extends GameObject {

    public static ArrayList<Asteroid> asteroids = new ArrayList<>(); //create list to keep track of the asteroids

    AsteroidSize size;
    Random random = new Random(); //random object to be used for choosing asteroid spawn coordinates
    Image image = new Image("file:images/asteroid.jpg");
    ImagePattern pattern = new ImagePattern(image);

    public static void moveAsteroids() { //for each asteroid moves it to next point according to its speed and current point
        for (Asteroid asteroid: asteroids)
            asteroid.move();
    }

    // Note: We use overloaded constructors depending on whether the asteroid is new, or spawned as a result of an
    // existing asteroid being destroyed.

    // Constructor for creating asteroid based on size exclusively
    public Asteroid(AsteroidSize size) {
        super(new Polygon(size.x1,size.y1,size.x2,size.y2,size.x3,size.y3, //creates GameObject with random speed within a range
                            size.x4,size.y4,size.x5,size.y5,size.x6,
                            size.y6, size.x7,size.y7,size.x8,size.y8), (Math.random() * (size.max - size.min) + size.min));
        this.size = size;
        super.turn(random.nextInt(1, 360)); //turns in a random direction
        spawnX = calculateRandomSpawnX();
        spawnY = calculateRandomSpawnY();
        polygon.setFill(pattern);
        asteroids.add(this); //add this asteroid to list of asteroids
    }

    // Constructor for creating asteroid in a particular position
    public Asteroid(AsteroidSize size, double currentAsteroidPosX, double currentAsteroidPosY) {
        //this constructor is to be used when a previous asteroid is destroyed and needs to split into 2 other asteroids
        super(new Polygon(size.x1,size.y1,size.x2,size.y2,size.x3,size.y3,size.x4,size.y4,size.x5,size.y5,size.x6,
                            size.y6, size.x7,size.y7,size.x8,size.y8), (Math.random() * (size.max - size.min) + size.min));
        this.size = size;
        spawnX = currentAsteroidPosX;
        spawnY = currentAsteroidPosY;
        polygon.setFill(pattern);
        super.turn(random.nextInt(1, 360));
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
            randomDouble = setInRange(randomDouble); //to prevent an asteroid spawning on top of the ship
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

    // Sets the random double into a specified range when it would have spawned on the ship
    private double setInRange(double randomDouble) {
        int max = 1;
        int min = 0;
        int randomInt = (int)Math.floor(Math.random()*(max-min+1)+min);
        if(randomInt == 1){ //change previously generated spawn coord by +150 or -150 pixels
            randomDouble += 150;
        } else if (randomInt == 0) {
            randomDouble -= 150;
        }
        return randomDouble;
    }

    public static Asteroid getAsteroidPieces(Asteroid asteroid) {
        if (asteroid.getSize() == AsteroidSize.BIG) { // if asteroid that split is big then turn into 2 medium
            return new Asteroid(AsteroidSize.MEDIUM, asteroid.getCurrentXPosition(), asteroid.getCurrentYPosition());
        } // if asteroid that split is medium then turn into 2 small
        return new Asteroid(AsteroidSize.SMALL, asteroid.getCurrentXPosition(), asteroid.getCurrentYPosition());
    }

}
