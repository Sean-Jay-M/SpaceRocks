package com.spacerocks;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;


public class Spawner {
    //This class is responsible for handling the spawning of new objects
    Pane gamePane;
    public Spawner(Pane gamePane){
        this.gamePane = gamePane;
    }

    public void spawnGameObject(GameObject gameObject){
        setSpawnCords(gameObject);
        drawGameObject(gameObject);
    }

    //sets the spawning coordinates
    private void setSpawnCords(GameObject gameObject){
        Polygon polygon = gameObject.getPolygon();
        double spawn_x = gameObject.getSpawn_x();
        double spawn_y = gameObject.getSpawn_y();

        polygon.setTranslateX(spawn_x);
        polygon.setTranslateY(spawn_y);
    }

    //This draws the object
    private void drawGameObject(GameObject gameObject) {
        gamePane.getChildren().add(gameObject.getPolygon());
    }

    //this removes the game object
    private void removeGameObject(GameObject gameObject){
        gamePane.getChildren().remove(gameObject.getPolygon());
    }
}
