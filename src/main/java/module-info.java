module com.spacerocks.spacerocks {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports com.spacerocks.ui;
    opens com.spacerocks.ui to javafx.fxml;
    exports com.spacerocks.ui.presets;
    opens com.spacerocks.ui.presets to javafx.fxml;
    exports com.spacerocks.main;
    opens com.spacerocks.main to javafx.fxml;
    exports com.spacerocks.gameobjects;
    opens com.spacerocks.gameobjects to javafx.fxml;
}