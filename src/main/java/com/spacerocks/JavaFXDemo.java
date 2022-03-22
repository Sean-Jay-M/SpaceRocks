package com.spacerocks;

// These are all the JavaFX imports that we need. Remember to include JavaFX in the libraries list when
// creating the project in IntelliJ.
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

// By extending "Application", we are creating a JavaFX app (i.e. an app with a GUI).
// Anytime we want to use this library we should extend Application with our main class.
public class JavaFXDemo extends Application {
    public static void main(String[] args) {
        // We use launch method here so that the IDE can run it.
        // Once we build the application, "start" method (below) will be used instead. We don't actually need a "main" method for the
        // final product.
        launch(args);
        System.exit(0);
    }

    // The "start" method is what actually starts the JavaFX application instead of main.
    // Stage object is the main container for the application. Everything that goes into our GUI has to be on the "Stage".
    // Note that the stage object is created when it's passed as argument into "start".
    public void start(Stage mainStage) {

        // This is the text on top of the window. Just for cosmetic purposes, not too important.
        mainStage.setTitle("Asteroids");

        // This is the "body" of the application window, where the various graphical elements will go.
        // There is also "BorderPane" where you can place items in certain areas of the window, but we will only have
        // the game window, so it's probably overkill, and in any event the Brightspace video suggests that we use Pane.
        Pane mainWindow = new Pane();

        // Sets the default size of the main window.
        mainWindow.setPrefSize(500, 500);

        // The "scene" is an element that will be used later to manage the controls.
        Scene mainScene = new Scene(mainWindow);

        // Attach scene to stage.
        mainStage.setScene(mainScene);

        // The below is a brief summary on how to create a shape using JavaFX. Later, we can replace these with
        // images, but we will still need the Polygon objects to measure collisions etc. This will go in a different
        // class, of course, but for demo purposes I just wanted to have everything in the one Java file.

        // The polygon class constructor will take an argument of pairs of coordinates. Here we have three pairs, so it will be
        // a triangle.
        // We can do something similar for the asteroids themselves, too.
        Polygon spaceShip = new Polygon(-5, -5, 10, 0, -5, 5);

        // setTranslate sets the coordinates of where the shape will be rendered.
        // Eventually we can use these methods to move the shape around the screen (i.e., while key is pressed down,
        // setTranslateX()+=10, that sort of thing).
        spaceShip.setTranslateX(150);
        spaceShip.setTranslateY(100);

        // Get Children / Add is how we add graphical elements, such as polygons, to the main window of the application.
        mainWindow.getChildren().add(spaceShip);

        // This just makes the application visible.
        mainStage.show();
    }
}
