package com.spacerocks.ui.presets;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuUIPreset extends UIPreset{

    private final Text mainTitle = new Text(110, 110, "SPACEROCKS");
    private final Text[] allTextElements = {mainTitle};

    @Override
    public void setScreen() {
        clearScreen();
        mainTitle.setFill(Color.WHITE);
        mainTitle.setFont(Font.font("arial", FontWeight.BOLD, 40));
        initMenuButtons();
        setTextElement(allTextElements);
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
}
