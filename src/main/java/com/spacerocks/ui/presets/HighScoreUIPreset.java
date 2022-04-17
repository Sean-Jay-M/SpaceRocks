package com.spacerocks.ui.presets;

import com.spacerocks.ui.ScoreBoardHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.FileNotFoundException;

public class HighScoreUIPreset extends UIPreset {

    private final ScoreBoardHandler scoreBoardHandler = new ScoreBoardHandler();

    private final Label scoreTitle = new Label("Hall Of Fame:");
    private final Label score1 = new Label("0");
    private final Label score2 = new Label("0");
    private final Label score3 = new Label("0");
    private final Label score4 = new Label("0");
    private final Label score5 = new Label("0");

    private final Label[] allTextElements = {scoreTitle, score1, score2, score3, score4, score5};
    private final Label[] scoreEntries = {score1, score2, score3, score4, score5};

    public ScoreBoardHandler getScoreBoardHandler() {
        return scoreBoardHandler;
    }

    @Override
    public void setScreen() {
        clearScreen();
        scoreTitle.relocate(160,100);
        scoreTitle.setId("sub-title");
        score1.setId("menu_content");
        score2.setId("menu_content");
        score3.setId("menu_content");
        score4.setId("menu_content");
        score5.setId("menu_content");
        score1.relocate(220,150);
        score2.relocate(220,180);
        score3.relocate(220,210);
        score4.relocate(220,240);
        score5.relocate(220,270);
        Button highScoreExitButton = initHighScoreExitButton();
        try {
            scoreBoardHandler.setHigHScoreFromText(scoreEntries);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setTextElement(allTextElements);
        screen.getPane().getChildren().add(highScoreExitButton);
    }

    private Button initHighScoreExitButton() {
        Button highScoreExitButton = new Button("Back");
        highScoreExitButton.setTranslateX(215);
        highScoreExitButton.setTranslateY(320);
        highScoreExitButton.setOnAction(e -> screen.setMenuScreen());
        return highScoreExitButton;
    }
}
