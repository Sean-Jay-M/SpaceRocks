package com.spacerocks;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class UI {
    private final Pane gamePane;
    private final Text scoreText = new Text(10, 30, "Score:");
    private Text scoreValue = new Text (75, 31, "0");

    public UI(Pane gamePane) {
        this.gamePane = gamePane;
        initScoreUI();
    }

    private void initScoreUI() {
        setDefaultTextProperties(scoreText);
        setDefaultTextProperties(scoreValue);
        gamePane.getChildren().add(scoreText);
        gamePane.getChildren().add(scoreValue);
    }

    private void setDefaultTextProperties(Text text) {
        text.setFill(Color.WHITE);
        text.setFont(Font.font("arial", FontWeight.BOLD, 20));
    }

}

