package com.spacerocks;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class UI {
    private final Pane pane;
    private Screen screen;

    private final Text scoreText = new Text(10, 30, "Score:");
    private Text scoreValue = new Text (75, 31, "0");

    private final Text livesText = new Text(400, 30, "Lives:");
    private Text livesValue = new Text(460, 30, "3");

    private final Text mainTitle = new Text(110, 110, "SPACEROCKS");


    public UI(Pane pane, Screen screen) {
        this.pane = pane;
        this.screen = screen;
    }

    public void addScoreValue(int score) {
        int newScore = getTextValue(scoreValue) + score;
        scoreValue.setText(Integer.toString(newScore));
    }

    public void removeLife() {
        int newLives = getTextValue(livesValue) - 1;
        livesValue.setText(Integer.toString(newLives));
    }

    public void resetUIValues() {
        scoreValue.setText("0");
        livesValue.setText("3");
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
        pane.getChildren().add(scoreText);
        pane.getChildren().add(scoreValue);
        pane.getChildren().add(livesText);
        pane.getChildren().add(livesValue);
    }

    public void initButton() {
        Button button = new Button("Play Game");
        button.setTranslateX(250);
        button.setTranslateY(250);
        button.setOnAction(e -> screen.setGameScreen());
        pane.getChildren().add(button);
    }


    private void setDefaultTextProperties(Text text) {
        text.setFill(Color.WHITE);
        text.setFont(Font.font("arial", FontWeight.BOLD, 20));
    }


    public void initMenuUI() {
        clearScreen();
        mainTitle.setFill(Color.WHITE);
        mainTitle.setFont(Font.font("arial", FontWeight.BOLD, 40));
        initButton();
        pane.getChildren().add(mainTitle);
        System.out.println(pane.getChildren());
    }

    public void clearScreen() {
        pane.getChildren().clear();
    }



}
