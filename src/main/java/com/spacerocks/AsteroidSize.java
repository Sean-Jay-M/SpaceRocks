package com.spacerocks;

// Enum containing all the constant variables relating to a particular asteroid size.
public enum AsteroidSize {
    // Constant variables containing x coordinates, y coordinates and speed.
    BIG(200.0/3.0, 50.0/3.0,
            400.0/3.0, 50.0/3.0,
            450.0/3.0, 150.0/3.0,
            400.0/3.0, 250.0/3.0,
            200.0/3.0, 250.0/3.0,
            150.0/3.0, 150.0/3.0, 0.6),
    MEDIUM(120.0/2.0, 30.0/2.0,
            240.0/2.0, 30.0/2.0,
            270.0/2.0, 90.0/2.0,
            240.0/2.0, 150.0/2.0,
            120.0/2.0, 150.0/2.0,
            90.0/2.0, 90.0/2.0, 1.0),
    SMALL(50.0, 12.5,
            100.0, 12.5,
            112.5, 37.5,
            100.0, 62.5,
            50.0, 62.5,
            37.5, 37.5, 1.6);

    double x1;
    double y1;
    double x2;
    double y2;
    double x3;
    double y3;
    double x4;
    double y4;
    double x5;
    double y5;
    double x6;
    double y6;

    double speed;


    AsteroidSize(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, double x5, double y5, double x6, double y6, double speed) {
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
        this.speed = speed;
    }
}