// This class is part of the the UI package
package com.spacerocks.ui.presets;

// Importing necessary local packages
import com.spacerocks.main.Screen;

// Importing necessary JavaFX elements
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

import java.awt.*;

// This abstract class represents a UI preset that is activated by the UI class.
// Please note because the UI presets are largely identical, with the only changes
// relating to the text of the labels and the button functionality.
public abstract class UIPreset implements UISetter {

    // Create reference to the screen
    Screen screen = Screen.getScreenInstance();

    // Add the text element to the screen
    protected void setTextElement(Label[] textElements) {
        for (Label textElement: textElements) {
//            setDefaultTextProperties(textElement);
//            screen.getPane().getChildren().add(textElement);
            screen.getPane().getChildren().add(textElement);
        }
    }

    // Method to clear the screen
    protected void clearScreen() {
        screen.getPane().getChildren().clear();
    }

//    private void setDefaultTextProperties(Text text) {
//        text.setFill(Color.WHITE);
//        text.setFont(Font.font("arial", FontWeight.BOLD, 20));
//    }
}
