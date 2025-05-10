module oop.oop5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens oop.oop5 to javafx.fxml;
    exports oop.oop5;
}