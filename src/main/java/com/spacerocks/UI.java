package com.spacerocks;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class UI {
    private Screen screen;

    private final Text scoreText = new Text(10, 30, "Score:");
    private Text scoreValue = new Text (75, 31, "0");

    private final Text livesText = new Text(400, 30, "Lives:");
    private Text livesValue = new Text(460, 30, "3");

    private final Text crashText = new Text(170, 30, "YOU CRASHED!");
    private final Text nextLevelText = new Text(170, 30, "WELL DONE!");

    private final Text mainTitle = new Text(110, 110, "SPACEROCKS");

    public UI(Screen screen) {
        this.screen = screen;
    }

    public void addScoreValue(int score) {
        int newScore = getTextValue(scoreValue) + score;
        scoreValue.setText(Integer.toString(newScore));
    }

    public int getCurrentScoreValue() {
        return Integer.parseInt(scoreValue.getText());
    }



    public void removeLife() {
        int newLives = getTextValue(livesValue) - 1;
        livesValue.setText(Integer.toString(newLives));
    }

    public void resetUIValues() {
        scoreValue.setText("0");
        livesValue.setText("3");
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue.setText(Integer.toString(scoreValue));
    }

    private int getTextValue(Text text) {
        return Integer.parseInt(text.getText());
    }

    public void initScoreUI() {
        clearScreen();
        setDefaultTextProperties(scoreText);
        setDefaultTextProperties(scoreValue);
        setDefaultTextProperties(livesText);
        setDefaultTextProperties(livesValue);
        setDefaultTextProperties(crashText);
        setDefaultTextProperties(nextLevelText);
        screen.getPane().getChildren().add(scoreText);
        screen.getPane().getChildren().add(scoreValue);
        screen.getPane().getChildren().add(livesText);
        screen.getPane().getChildren().add(livesValue);
        screen.getPane().getChildren().add(crashText);
        screen.getPane().getChildren().add(nextLevelText);
        crashText.setVisible(false);
        nextLevelText.setVisible(false);
    }

    public void clearScreen() {
        screen.getPane().getChildren().clear();
    }

    public void toggleCrashText(boolean on) {
        crashText.setVisible(on);
    }

    public void toggleNextLevelText(boolean on) {
        nextLevelText.setVisible(on);
    }

    private void setDefaultTextProperties(Text text) {
        text.setFill(Color.WHITE);
        text.setFont(Font.font("arial", FontWeight.BOLD, 20));
    }

    public void initButtons() {
        Button playButton = initPlayButton();
        Button exitButton = initExitButton();

        screen.getPane().getChildren().add(playButton);
        screen.getPane().getChildren().add(exitButton);
    }

    private Button initExitButton() {
        Button exitButton = new Button("Exit");
        exitButton.setTranslateX(235);
        exitButton.setTranslateY(290);
        exitButton.setOnAction(e -> System.exit(0));
        return exitButton;
    }

    private Button initPlayButton() {
        Button playButton = new Button("Play Game");
        playButton.setTranslateX(215);
        playButton.setTranslateY(250);
        playButton.setOnAction(e -> screen.setGameScreen());
        return playButton;
    }

    public void initMenuUI() {
        clearScreen();
        mainTitle.setFill(Color.WHITE);
        mainTitle.setFont(Font.font("arial", FontWeight.BOLD, 40));
        initButtons();
        screen.getPane().getChildren().add(mainTitle);
    }

}
