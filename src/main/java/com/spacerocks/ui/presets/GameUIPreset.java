package com.spacerocks.ui.presets;

import javafx.scene.text.Text;

public class GameUIPreset extends UIPreset {

    private final Text scoreText = new Text(10, 30, "Score:");
    private final Text scoreValue = new Text (75, 31, "0");
    private final Text livesText = new Text(400, 30, "Lives:");
    private final Text livesValue = new Text(460, 30, "3");
    private final Text crashText = new Text(170, 30, "YOU CRASHED!");
    private final Text nextLevelText = new Text(170, 30, "WELL DONE!");

    private final Text[] allTextElements = {scoreText, scoreValue, livesText, livesValue, crashText, nextLevelText};

    @Override
    public void setScreen() {
        clearScreen();
        setTextElement(allTextElements);
        crashText.setVisible(false);
        nextLevelText.setVisible(false);
    }

    public Text getScoreValue() {
        return scoreValue;
    }

    public Text getLivesValue() {
        return livesValue;
    }

    public Text getCrashText() {
        return crashText;
    }

    public Text getNextLevelText() {
        return nextLevelText;
    }
}
