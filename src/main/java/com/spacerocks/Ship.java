package com.spacerocks;

import javafx.scene.shape.Polygon;
import java.awt.geom.Point2D;
import java.math.*;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Ship extends GameObject {

    Polygon createShip(){
        Polygon spaceShip = new Polygon(-20, -20, -5, -15, -20, 20);
        return spaceShip;
    }


}
