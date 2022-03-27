module com.spacerocks.spacerocks {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.spacerocks to javafx.fxml;
    exports com.spacerocks;
}