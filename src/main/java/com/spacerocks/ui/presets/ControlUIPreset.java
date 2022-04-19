package com.spacerocks.ui.presets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControlUIPreset extends UIPreset{

    private final Label controlTitle = new Label("How to Play: ");
    private final Label control1 = new Label("↑: Thrust");
    private final Label control2 = new Label("←: Turn Left");
    private final Label control3 = new Label("→: Turn Right");
    private final Label control4 = new Label("shift: Hyperspace ");

    private final Label[] allTextElements = {controlTitle, control1, control2, control3, control4};

    @Override
    public void setScreen() {
        clearScreen();
        controlTitle.relocate(180,100);
        controlTitle.setId("sub-title");
        control1.setId("menu_content");
        control2.setId("menu_content");
        control3.setId("menu_content");
        control4.setId("menu_content");
        control1.relocate(180,150);
        control2.relocate(180,180);
        control3.relocate(180,210);
        control4.relocate(180,240);
        Button controlExitButton = initControlExitButton();
        setTextElement(allTextElements);
        screen.getPane().getChildren().add(controlExitButton);
    }

    private Button initControlExitButton() {
        Button controlExitButton = new Button("Back");
        controlExitButton.setTranslateX(215);
        controlExitButton.setTranslateY(320);
        controlExitButton.setOnAction(e -> screen.setMenuScreen());
        return controlExitButton;
    }
}
