package com.spacerocks;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;


public class Spawner implements DespawnListener {
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
    public void drawGameObject(GameObject gameObject) {
        gamePane.getChildren().add(gameObject.getPolygon());
    }

    @Override
    public void onDespawn(GameObject gameObject) {
        despawn(gameObject);
    }


    //this removes the game object
    public void despawn(GameObject gameObject){
        gamePane.getChildren().remove(gameObject.getPolygon());
    }

    // Removes an array list of game objects (to be used for bullets)
    public void despawn(ArrayList<Bullet> gameObjects) {
        ObservableList<Node> currentObjects = gamePane.getChildren();

        for (GameObject gameObject: gameObjects) {
            currentObjects.remove(gameObject);
        }
    }
}
