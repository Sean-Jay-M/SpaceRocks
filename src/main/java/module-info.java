module com.spacerocks.spacerocks {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.spacerocks to javafx.fxml;
    exports com.spacerocks;
}