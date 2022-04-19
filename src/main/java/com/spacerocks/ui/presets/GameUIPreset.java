package com.spacerocks.ui.presets;

import javafx.scene.control.Label;

// Note: this is a UI present. Its structure is identical to the others, and its
// responsibility is to generate the UI text and buttons and package them
// in a "setScreen" method that needs to be implemented via an interface.
public class GameUIPreset extends UIPreset {

    private final Label scoreText = new Label("Score:");
    private final Label scoreValue = new Label ("0");
    private final Label livesText = new Label("Lives:");
    private final Label livesValue = new Label("3");
    private final Label gameOverText = new Label("GAME OVER!");
    private final Label nextLevelText = new Label("WELL DONE!");

    private final Label[] allTextElements = {scoreText, scoreValue, livesText, livesValue, gameOverText, nextLevelText};

    @Override
    public void setScreen() {
        clearScreen();
        scoreText.relocate(10,30);
        scoreValue.relocate(120,30);
        livesText.relocate(365,30);
        livesValue.relocate(475,30);
        gameOverText.relocate(150, 200);
        nextLevelText.relocate(150,200);
        scoreText.setId("game_content");
        scoreValue.setId("game_content");
        livesText.setId("game_content");
        livesValue.setId("game_content");
        nextLevelText.setId("game_content");
        gameOverText.setId("game_content");
        gameOverText.setVisible(false);
        setTextElement(allTextElements);
        nextLevelText.setVisible(false);
    }

    public Label getScoreValue() {
        return scoreValue;
    }

    public Label getLivesValue() {
        return livesValue;
    }

    public Label getGameOverText() { return gameOverText; }

    public Label getNextLevelText() {
        return nextLevelText;
    }
}
