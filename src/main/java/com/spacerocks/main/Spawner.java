// Class is part of the Main package.
package com.spacerocks.main;

// Importing necessary local packages.
import com.spacerocks.gameobjects.GameObject;

// This class is responsible for spawning and despawning objects from
// the game. It is a component of "Screen" and is accessed exclusively
// from the Screen class.
public class Spawner {

    // Defining reference to Screen singleton.
    Screen screen = Screen.getScreenInstance();

    // Adds an object to the screen.
    public void spawnGameObject(GameObject gameObject){
        setSpawnCords(gameObject);
        drawGameObject(gameObject);
    }

    // Removes an object from the screen.
    public void despawnGameObject(GameObject gameObject){
        screen.getPane().getChildren().remove(gameObject.getPolygon());
    }

    // Sets the default spawn coordinates for the object as defined within the object class.
    private void setSpawnCords(GameObject gameObject){
        gameObject.getPolygon().setTranslateX(gameObject.getSpawnX());
        gameObject.getPolygon().setTranslateY(gameObject.getSpawnY());
    }

    // Draws the game object by adding it to the Pane.
    private void drawGameObject(GameObject gameObject) {
        screen.getPane().getChildren().add(gameObject.getPolygon());
    }
}