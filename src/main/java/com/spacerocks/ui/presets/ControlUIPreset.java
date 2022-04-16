package com.spacerocks.ui.presets;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ControlUIPreset extends UIPreset{

    private final Text controlTitle = new Text(180, 100, "How to Play: ");
    private final Text control1 = new Text(180, 130, "↑: Thrust");
    private final Text control2 = new Text(180, 160, "←: Turn Left");
    private final Text control3 = new Text(180, 190, "→: Turn Right");
    private final Text control4 = new Text(180, 220, "shift: Hyperspace ");

    private final Text[] allTextElements = {controlTitle, control1, control2, control3, control4};

    @Override
    public void setScreen() {
        clearScreen();
        Button controlExitButton = initControlExitButton();
        setTextElement(allTextElements);
        screen.getPane().getChildren().add(controlExitButton);
    }

    private Button initControlExitButton() {
        Button controlExitButton = new Button("Back");
        controlExitButton.setTranslateX(235);
        controlExitButton.setTranslateY(320);
        controlExitButton.setOnAction(e -> screen.setMenuScreen());
        return controlExitButton;
    }
}
