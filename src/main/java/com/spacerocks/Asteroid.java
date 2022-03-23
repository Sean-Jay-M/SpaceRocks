package com.spacerocks;

public class Asteroid extends GameObject {

    // Enum containing all the constant variables relating to a particular asteroid size.
    private enum AsteroidSize {

        // Constant variables containing x coordinates, y coordinates and speed.
        SMALL(new double [] {5.0, 7.5, 17.5, 20.0, 12.5}, new double [] {7.5, 15.0, 15.0, 7.5, 2.5}, 3),
        MEDIUM(new double [] {10.0, 15.0, 35.0, 40.0, 25.0}, new double [] {15.0, 30.0, 30.0, 15.0, 5.0}, 2),
        BIG(new double [] {20.0, 30.0, 70.0, 80.0, 50.0}, new double [] {30.0, 60.0, 60.0, 30.0, 10.0}, 1);

        // Initial fields for the enum
        private final double[] xpoints;
        private final double[] ypoints;
        private final int speed;

        // Constructor for the enum
        AsteroidSize(double[] xpoints, double[] ypoints, int speed) {
            this.xpoints = xpoints;
            this.ypoints = ypoints;
            this.speed = speed;
        }

        // Getters for the enum
        public double[] getXpoints() {
            return xpoints;
        }

        public double[] getYpoints() {
            return ypoints;
        }

        public int getSpeed() {
            return speed;
        }
    }

    // Constructor for asteroid. The first parameter is the size of the asteroid, which will then automatically
    // populate the super class' constructor with the appropriate x points, y points and speed values.
    public Asteroid(AsteroidSize size, int rotation, double xposition, double yposition) {
        super(size.getXpoints(), size.getYpoints(), rotation, size.getSpeed(), xposition, yposition);
    }

    // The below is just a quick demo showing the creation of a small asteroid would look like, with proof showing
    // that it works properly. Please feel free to uncomment and run it / play around with it, we will of course
    // be deleting it for the final product.

    // public static void main(String[] args) {
    //     Asteroid asteroid = new Asteroid(AsteroidSize.SMALL, 1, 2.0, 3.0);
    //     System.out.println(asteroid.getXpoints()[0]);
    // }
}
