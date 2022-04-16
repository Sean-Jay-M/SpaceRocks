package com.spacerocks.ui;

import com.spacerocks.ui.presets.ControlUIPreset;
import com.spacerocks.ui.presets.GameUIPreset;
import com.spacerocks.ui.presets.HighScoreUIPreset;
import com.spacerocks.ui.presets.MenuUIPreset;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

public class UI {
    private final GameUIPreset gameUIPreset;
    private final MenuUIPreset menuUIPreset;
    private final HighScoreUIPreset highScoreUIPreset;
    private final ControlUIPreset controlUIPreset;
    private ImagePattern[] backgrounds;
    private int currentBackground = 0;


    public UI() {
        gameUIPreset = new GameUIPreset();
        menuUIPreset = new MenuUIPreset();
        highScoreUIPreset = new HighScoreUIPreset();
        controlUIPreset = new ControlUIPreset();
        initSpaceBackgrounds();
    }

    public ImagePattern getNextBackground() {
        currentBackground++;
        if (currentBackground == backgrounds.length) {
            currentBackground = 0;
        }
        return backgrounds[currentBackground];
    }

    private void initSpaceBackgrounds() {
        backgrounds = new ImagePattern[]{new ImagePattern(new Image("file:images/space1.jpg")),
                                        new ImagePattern(new Image("file:images/space2.jpg")),
                                        new ImagePattern(new Image("file:images/space3.jpg")),
                                        new ImagePattern(new Image("file:images/space4.jpg")),
                                        new ImagePattern(new Image("file:images/space5.jpg"))};
    }

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

    public void addScoreValue(int score) {
        int newScore = getTextValue(gameUIPreset.getScoreValue()) + score;
        gameUIPreset.getScoreValue().setText(Integer.toString(newScore));
    }

    public int getCurrentScoreValue() {
        return Integer.parseInt(gameUIPreset.getScoreValue().getText());
    }

    public void removeLife() {
        int newLives = getTextValue(gameUIPreset.getLivesValue()) - 1;
        gameUIPreset.getLivesValue().setText(Integer.toString(newLives));
    }

    public void resetUIValues() {
        gameUIPreset.getScoreValue().setText("0");
        gameUIPreset.getLivesValue().setText("3");
    }

    public void setScoreValue(int scoreValue) {
        gameUIPreset.getScoreValue() .setText(Integer.toString(scoreValue));
    }

    private int getTextValue(Text text) {
        return Integer.parseInt(text.getText());
    }

    public void toggleCrashText(boolean on) {
        gameUIPreset.getCrashText().setVisible(on);
    }

    public void toggleNextLevelText(boolean on) {
        gameUIPreset.getNextLevelText().setVisible(on);
    }

}
