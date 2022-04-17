package com.spacerocks.ui.presets;

import javafx.scene.control.Label;

public class GameUIPreset extends UIPreset {

    private final Label scoreText = new Label("Score:");
    private final Label scoreValue = new Label ("0");
    private final Label livesText = new Label("Lives:");
    private final Label livesValue = new Label("3");
    private final Label crashText = new Label("YOU CRASHED!");
    private final Label nextLevelText = new Label("WELL DONE!");

    private final Label[] allTextElements = {scoreText, scoreValue, livesText, livesValue, crashText, nextLevelText};

    @Override
    public void setScreen() {
        clearScreen();
        scoreText.relocate(10,30);
        scoreValue.relocate(120,30);
        livesText.relocate(365,30);
        livesValue.relocate(475,30);
        crashText.relocate(150,200);
        nextLevelText.relocate(150,200);
        scoreText.setId("game_content");
        scoreValue.setId("game_content");
        livesText.setId("game_content");
        livesValue.setId("game_content");
        nextLevelText.setId("game_content");
        setTextElement(allTextElements);
        crashText.setVisible(false);
        nextLevelText.setVisible(false);
    }

    public Label getScoreValue() {
        return scoreValue;
    }

    public Label getLivesValue() {
        return livesValue;
    }

    public Label getCrashText() {
        return crashText;
    }

    public Label getNextLevelText() {
        return nextLevelText;
    }
}
