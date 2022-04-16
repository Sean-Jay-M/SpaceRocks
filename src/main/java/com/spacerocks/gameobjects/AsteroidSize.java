package com.spacerocks.gameobjects;

// Enum containing all the constant variables relating to a particular asteroid size.
public enum AsteroidSize {
    // Constant variables containing x coordinates, y coordinates. Also containing Max and Min Speed.
    BIG(    69/1.5, 25/1.5,
            131/1.5, 25/1.5,
            175/1.5, 69/1.5,
            175/1.5, 131/1.5,
            131/1.5, 175/1.5,
            69/1.5, 175/1.5,
            25/1.5, 131/1.5,
            25/1.5, 69/1.5,
            0.2),
    MEDIUM(69/2.5, 25/2.5,
            131/2.5, 25/2.5,
            175/2.5, 69/2.5,
            175/2.5, 131/2.5,
            131/2.5, 175/2.5,
            69/2.5, 175/2.5,
            25/2.5, 131/2.5,
            25/2.5, 69/2.5,
            0.2),
    SMALL(  69/3.5, 25/3.5,
            131/3.5, 25/3.5,
            175/3.5, 69/3.5,
            175/3.5, 131/3.5,
            131/3.5, 175/3.5,
            69/3.5, 175/3.5,
            25/3.5, 131/3.5,
            25/3.5, 69/3.5,
            0.2);

    final double x1;
    final double y1;
    final double x2;
    final double y2;
    final double x3;
    final double y3;
    final double x4;
    final double y4;
    final double x5;
    final double y5;
    final double x6;
    final double y6;
    final double x7;
    final double y7;
    final double x8;
    final double y8;


    final double min;
    final double max;

    //Get the centers
    void AsteroidCenter(double x4, double x8, double y5, double y2){
         double xcenter = (x4 - x8) / 2;
         double ycenter = (y5 - y2) - 2;
    }

    AsteroidSize(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, double x5, double y5, double x6, double y6, double x7, double y7, double x8, double y8, double speed) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
        this.x5 = x5;
        this.y5 = y5;
        this.x6 = x6;
        this.y6 = y6;
        this.x7 = x7;
        this.y7=y7;
        this.x8 = x8;
        this.y8 = y8;
        this.speed = speed;
    }
}