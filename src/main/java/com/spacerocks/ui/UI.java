// Class is part of UI package
package com.spacerocks.ui;

// Importing necessary local packages
import com.spacerocks.ui.presets.ControlUIPreset;
import com.spacerocks.ui.presets.GameUIPreset;
import com.spacerocks.ui.presets.HighScoreUIPreset;
import com.spacerocks.ui.presets.MenuUIPreset;

// Importing necessary JavaFX packages
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.control.Label;

// This class is responsible for manipulating the UI elements of the screen by
// selecting appropriate UI preset components
public class UI {

    // Initialising components and other various variables necessary
    private final GameUIPreset gameUIPreset;
    private final MenuUIPreset menuUIPreset;
    private final HighScoreUIPreset highScoreUIPreset;
    private final ControlUIPreset controlUIPreset;
    private ImagePattern[] backgrounds;
    private int currentBackground = 0;

    // Constructor for initialising the various components
    public UI() {
        gameUIPreset = new GameUIPreset();
        menuUIPreset = new MenuUIPreset();
        highScoreUIPreset = new HighScoreUIPreset();
        controlUIPreset = new ControlUIPreset();
        initSpaceBackgrounds();
    }

    // Method for looping through backgrounds
    public ImagePattern getNextBackground() {
        currentBackground++;
        if (currentBackground == backgrounds.length) {
            currentBackground = 0;
        }
        return backgrounds[currentBackground];
    }

    // Initialising all the backgrounds
    private void initSpaceBackgrounds() {
        backgrounds = new ImagePattern[]{new ImagePattern(new Image("file:images/space1.jpg")),
                                        new ImagePattern(new Image("file:images/space2.jpg")),
                                        new ImagePattern(new Image("file:images/space3.jpg")),
                                        new ImagePattern(new Image("file:images/space4.jpg")),
                                        new ImagePattern(new Image("file:images/space5.jpg"))};
    }

    // Getters for the various preset components
    public GameUIPreset getGameUIPreset() {
        return gameUIPreset;
    }
    public MenuUIPreset getMenuUIPreset() {
        return menuUIPreset;
    }
    public HighScoreUIPreset getHighScoreUIPreset() {
        return highScoreUIPreset;
    }
    public ControlUIPreset getControlUIPreset(){
        return controlUIPreset;
    }

    // Adding to score value
    public void addScoreValue(int score) {
        int newScore = getTextValue(gameUIPreset.getScoreValue()) + score;
        gameUIPreset.getScoreValue().setText(Integer.toString(newScore));
    }

    // Getting the current score value
    public int getCurrentScoreValue() {
        return Integer.parseInt(gameUIPreset.getScoreValue().getText());
    }

    // Updating the lives by a certain amount
    public void updateLives(int newLives) {
        gameUIPreset.getLivesValue().setText(Integer.toString(newLives));
    }

    // Reseting to default UI values
    public void resetUIValues() {
        gameUIPreset.getScoreValue().setText("0");
        gameUIPreset.getLivesValue().setText("3");
    }

    // Getting the current text value
    private int getTextValue(Label text) {
        return Integer.parseInt(text.getText());
    }

    // Switching on the "Well Done" text
    public void toggleNextLevelText(boolean on) {
        gameUIPreset.getNextLevelText().setVisible(on);
    }

}
