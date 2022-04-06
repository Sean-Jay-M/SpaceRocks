package com.spacerocks;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;


public class Spawner implements SpawnListener {
    //This class is responsible for handling the spawning of new objects
    Screen screen;
    public Spawner(Screen screen){
        this.screen = screen;
    }

    public void spawnGameObject(GameObject gameObject){
        setSpawnCords(gameObject);
        drawGameObject(gameObject);
    }

    //sets the spawning coordinates
    private void setSpawnCords(GameObject gameObject){
        gameObject.getPolygon().setTranslateX(gameObject.getSpawnX());
        gameObject.getPolygon().setTranslateY(gameObject.getSpawnY());
    }

    //This draws the object
    private void drawGameObject(GameObject gameObject) {
        screen.getPane().getChildren().add(gameObject.getPolygon());
    }

    @Override
    public void onDespawn(GameObject gameObject) {
        despawn(gameObject);
    }


    //this removes the game object
    public void despawn(GameObject gameObject){
        screen.getPane().getChildren().remove(gameObject.getPolygon());
    }

}