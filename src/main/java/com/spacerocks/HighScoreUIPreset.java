package com.spacerocks;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

public class HighScoreUIPreset extends UIPreset {

    private final ScoreBoardHandler scoreBoardHandler = new ScoreBoardHandler();

    private final Text scoreTitle = new Text(180, 100, "HALL OF FAME");
    private final Text score1 = new Text(180, 130, "0");
    private final Text score2 = new Text(180, 160, "0");
    private final Text score3 = new Text(180, 190, "0");
    private final Text score4 = new Text(180, 220, "0");
    private final Text score5 = new Text(180, 250, "0");

    private final Text[] allTextElements = {scoreTitle, score1, score2, score3, score4, score5};
    private final Text[] scoreEntries = {score1, score2, score3, score4, score5};

    private Button initHighScoreExitButton() {
        Button highScoreExitButton = new Button("Back");
        highScoreExitButton.setTranslateX(235);
        highScoreExitButton.setTranslateY(320);
        highScoreExitButton.setOnAction(e -> screen.setMenuScreen());
        return highScoreExitButton;
    }


    @Override
    public void setScreen() {
        clearScreen();
        Button highScoreExitButton = initHighScoreExitButton();
        try {
            scoreBoardHandler.setHigHScoreFromText(scoreEntries);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setTextElement(allTextElements);
        screen.getPane().getChildren().add(highScoreExitButton);
    }
}
