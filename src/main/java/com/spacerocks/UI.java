package com.spacerocks;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;


public class UI {
    private final GameUIPreset gameUIPreset;
    private final MenuUIPreset menuUIPreset;
    private final HighScoreUIPreset highScoreUIPreset;

    public UI() {
        gameUIPreset = new GameUIPreset();
        menuUIPreset = new MenuUIPreset();
        highScoreUIPreset = new HighScoreUIPreset();
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

    public void initScoreUI() {
        gameUIPreset.setScreen();
    }

    public void toggleCrashText(boolean on) {
        gameUIPreset.getCrashText().setVisible(on);
    }

    public void toggleNextLevelText(boolean on) {
        gameUIPreset.getNextLevelText().setVisible(on);
    }

    public void initMenuUI() {
        menuUIPreset.setScreen();
    }

    public void initHighScoreUI() {
        highScoreUIPreset.setScreen();
    }


}
