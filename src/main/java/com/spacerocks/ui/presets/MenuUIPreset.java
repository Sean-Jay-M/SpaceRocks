package com.spacerocks.ui.presets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuUIPreset extends UIPreset{

    private final Label mainTitle = new Label("SPACEROCKS");
    private final Label[] allTextElements = {mainTitle};

    @Override
    public void setScreen() {
        clearScreen();
        mainTitle.setId("mainTitle");
        mainTitle.relocate(45,100);
        initMenuButtons();
        setTextElement(allTextElements);
    }

    public void initMenuButtons() {
        Button playButton = initMenuPlayButton();
        Button exitButton = initMenuExitButton();
        Button highScoreButton = initMenuHighScoreButton();
        Button controlButton = initMenuControlButton();

        screen.getPane().getChildren().add(playButton);
        screen.getPane().getChildren().add(exitButton);
        screen.getPane().getChildren().add(highScoreButton);
        screen.getPane().getChildren().add(controlButton);
    }

    private Button initMenuExitButton() {
        Button exitButton = new Button("Exit");
        exitButton.setTranslateX(215);
        exitButton.setTranslateY(360);
        exitButton.setOnAction(e -> System.exit(0));
        return exitButton;
    }

    private Button initMenuPlayButton() {
        Button playButton = new Button("Play Game");
        playButton.setTranslateX(215);
        playButton.setTranslateY(240);
        playButton.setOnAction(e -> screen.setGameScreen());
        return playButton;
    }

    private Button initMenuHighScoreButton() {
        Button highScoreButton = new Button("High Score");
        highScoreButton.setTranslateX(215);
        highScoreButton.setTranslateY(320);
        highScoreButton.setOnAction(e -> screen.setHighScoreScreen());
        return highScoreButton;
    }

    private Button initMenuControlButton() {
        Button controlButton = new Button("Control");
        controlButton.setTranslateX(215);
        controlButton.setTranslateY(280);
        controlButton.setOnAction(e -> screen.setControlScreen());
        return controlButton;
    }
}
