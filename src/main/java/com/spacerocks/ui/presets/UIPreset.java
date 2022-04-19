package com.spacerocks.ui.presets;

import com.spacerocks.main.Screen;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

import java.awt.*;

public abstract class UIPreset implements UISetter {

    Screen screen = Screen.getScreenInstance();

    protected void setTextElement(Label[] textElements) {
        for (Label textElement: textElements) {
//            setDefaultTextProperties(textElement);
//            screen.getPane().getChildren().add(textElement);
            screen.getPane().getChildren().add(textElement);
        }
    }

    protected void clearScreen() {
        screen.getPane().getChildren().clear();
    }

//    private void setDefaultTextProperties(Text text) {
//        text.setFill(Color.WHITE);
//        text.setFont(Font.font("arial", FontWeight.BOLD, 20));
//    }
}
