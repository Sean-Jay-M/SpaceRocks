package com.spacerocks;

public class Asteroid extends GameObject {

    private enum AsteroidSize {
        SMALL(new double [] {5.0, 7.5, 17.5, 20.0, 12.5}, new double [] {7.5, 15.0, 15.0, 7.5, 2.5}, 3),
        MEDIUM(new double [] {10.0, 15.0, 35.0, 40.0, 25.0}, new double [] {15.0, 30.0, 30.0, 15.0, 5.0}, 2),
        BIG(new double [] {20.0, 30.0, 70.0, 80.0, 50.0}, new double [] {30.0, 60.0, 60.0, 30.0, 10.0}, 1);

        private final double[] xpoints;
        private final double[] ypoints;
        private final int speed;

        AsteroidSize(double[] xpoints, double[] ypoints, int speed) {
            this.xpoints = xpoints;
            this.ypoints = ypoints;
            this.speed = speed;
        }

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

    public Asteroid(double [] xpoints, double [] ypoints, int rotation, int speed, double xposition, double yposition) {
        super(xpoints, ypoints, rotation, speed, xposition, yposition);
    }
}
