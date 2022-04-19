// Class is part of main package.
package com.spacerocks.main;

// Importing necessary local packages.
import com.spacerocks.ui.UI;

// Importing necessary JavaFX elements.
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

// This class is responsible displaying all elements of the application,
// with several selectable options as to what is being shown on the screen at any given time
// (such as menus, menu options, and the game itself). Because the screen
// is present throughout the entire application, it was decided to make it
// a "singleton".
// Source for singleton pattern: https://www.geeksforgeeks.org/singleton-design-pattern/
public class Screen {

    // The screen size will not change, and as such was made final
    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 500;

    // Defining components that will be used by this class
    private Stage gameStage;
    private Pane pane;
    private Scene scene;
    private UI ui;
    private Spawner spawner;
    private Game game;

    // For the singleton pattern to work, we will need a static reference to the Screen.
    // A blank private constructor is used by singletons, the variables will be initialised later.
    private static Screen screenInstance = null;
    private Screen() {}

    // The public static getter that will be used to return a reference to the Screen throughout this application.
    public static Screen getScreenInstance() {
        if (screenInstance == null) {
            screenInstance = new Screen();
        }
        return screenInstance;
    }

    // Defining getters for common elements used by other classes.
    public static int getScreenWidth(){
        return SCREEN_WIDTH;
    }
    public static int getScreenHeight(){
        return SCREEN_HEIGHT;
    }
    public Pane getPane() { return pane; }
    public Scene getScene() { return scene; }
    public Spawner getSpawner(){return spawner;}
    public UI getUI() { return ui; }

    // Setting initial state for the screen, to be used when the application starts.
    public void initScreen(Stage gameStage) {
        initJavaFXElements(gameStage);
        gameStage.setTitle("SpaceRocks");
        resetScreen();
        setDefaultScreenProperties();
        loadNewContent();
    }

    // Initiating local variables by creating, or assigning, the relevant components.
    private void initJavaFXElements(Stage gameStage) {
        this.gameStage = gameStage;
        this.spawner = new Spawner();
        this.ui = new UI();
    }

    // Setting the default screen state
    private void setDefaultScreenProperties() {
        setInitialBackground();
        pane.setPrefSize(SCREEN_HEIGHT, SCREEN_WIDTH);
        pane.setStyle("-fx-background-color: transparent;");

    }

    // Setting the background of the screen using a background saved locally.
    private void setInitialBackground() {
        Image image = new Image("file:images/space1.jpg");
        ImagePattern pattern = new ImagePattern(image);
        scene.setFill(pattern);
    }

    // Setting the next background in the list, activated whenever we change levels in the "Game" class
    public void setNextBackground() {
        scene.setFill(ui.getNextBackground());
    }

    // If the game is running, end the game. Afterwards, set the menu UI preset.
    public void setMenuScreen() {
        if (game != null) { game = null; }
        resetScreen();
        ui.getMenuUIPreset().setScreen();
        loadNewContent();
    }

    // Set the game UI preset, create a new game and start the game.
    public void setGameScreen() {
        resetScreen();
        ui.getGameUIPreset().setScreen();
        loadNewContent();
        game = new Game();
        game.play();
    }

    // Set the high score preset.
    public void setHighScoreScreen() {
        resetScreen();
        ui.getHighScoreUIPreset().setScreen();
        loadNewContent();
    }

    // Set the control preset.
    public void setControlScreen() {
        resetScreen();
        ui.getControlUIPreset().setScreen();
        loadNewContent();
    }

    // Reset the screen by replacing the existing pane and scene and setting default properties.
    public void resetScreen() {
        pane = new Pane();
        scene = new Scene(pane);
        setDefaultScreenProperties();
    }

    // Activate new content by adding CSS and showing the new content added to the Stage.
    public void loadNewContent() {
        scene.getStylesheets().add("file:css/styles.css");
        gameStage.setScene(scene);
        gameStage.show();
    }
}