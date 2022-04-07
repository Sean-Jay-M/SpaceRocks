package com.spacerocks;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class UI {
    private Screen screen;

    public ScoreBoardHandler getScoreBoardHandler() {
        return scoreBoardHandler;
    }

    private ScoreBoardHandler scoreBoardHandler = new ScoreBoardHandler();


    private final Text scoreText = new Text(10, 30, "Score:");
    private Text scoreValue = new Text (75, 31, "0");

    private final Text livesText = new Text(400, 30, "Lives:");
    private Text livesValue = new Text(460, 30, "3");

    private final Text crashText = new Text(170, 30, "YOU CRASHED!");
    private final Text nextLevelText = new Text(170, 30, "WELL DONE!");

    private final Text mainTitle = new Text(110, 110, "SPACEROCKS");

    private Text scoreTitle = new Text(180, 100, "HALL OF FAME");
    private Text score1 = new Text(180, 130, "0");
    private Text score2 = new Text(180, 160, "0");
    private Text score3 = new Text(180, 190, "0");
    private Text score4 = new Text(180, 220, "0");
    private Text score5 = new Text(180, 250, "0");

    Text[] allUIElements = {scoreText, scoreValue, livesText, livesValue, crashText, nextLevelText, mainTitle,
                            scoreTitle, score1, score2, score3, score4, score5};

    public UI(Screen screen) {
        this.screen = screen;
        for (Text text: allUIElements) {
            setDefaultTextProperties(text);
        }
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
        screen.getPane().getChildren().add(scoreText);
        screen.getPane().getChildren().add(scoreValue);
        screen.getPane().getChildren().add(livesText);
        screen.getPane().getChildren().add(livesValue);
        screen.getPane().getChildren().add(crashText);
        screen.getPane().getChildren().add(nextLevelText);
        crashText.setVisible(false);
        nextLevelText.setVisible(false);
    }

    public void initHighScoreUI() {
        clearScreen();
        screen.getPane().getChildren().add(scoreTitle);
        screen.getPane().getChildren().add(score1);
        screen.getPane().getChildren().add(score2);
        screen.getPane().getChildren().add(score3);
        screen.getPane().getChildren().add(score4);
        screen.getPane().getChildren().add(score5);
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

    public void initMenuButtons() {
        Button playButton = initMenuPlayButton();
        Button exitButton = initMenuExitButton();
        Button highScoreButton = initMenuHighScoreButton();

        screen.getPane().getChildren().add(playButton);
        screen.getPane().getChildren().add(exitButton);
        screen.getPane().getChildren().add(highScoreButton);
    }

    private Button initMenuExitButton() {
        Button exitButton = new Button("Exit");
        exitButton.setTranslateX(235);
        exitButton.setTranslateY(320);
        exitButton.setOnAction(e -> System.exit(0));
        return exitButton;
    }

    private Button initMenuPlayButton() {
        Button playButton = new Button("Play Game");
        playButton.setTranslateX(215);
        playButton.setTranslateY(260);
        playButton.setOnAction(e -> screen.setGameScreen());
        return playButton;
    }

    private Button initMenuHighScoreButton() {
        Button highScoreButton = new Button("High Score");
        highScoreButton.setTranslateX(214);
        highScoreButton.setTranslateY(290);
        highScoreButton.setOnAction(e -> screen.setHighScoreScreen());
        return highScoreButton;
    }

    public void initMenuUI() {
        clearScreen();
        mainTitle.setFill(Color.WHITE);
        mainTitle.setFont(Font.font("arial", FontWeight.BOLD, 40));
        initMenuButtons();
        screen.getPane().getChildren().add(mainTitle);
    }

}
