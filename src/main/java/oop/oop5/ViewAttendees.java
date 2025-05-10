package oop.oop5;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ViewAttendees   {
    @FXML private TableView<Attendee> attendeeTable;
    @FXML private TableColumn<Attendee, String> attendeeNameColumn;
    @FXML private TableColumn<Attendee, Double> balanceColumn;
    @FXML private Button backButton;
    Database db = Database.getInstance();

    @FXML
    public void initialize() {
        attendeeNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        balanceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getWallet().getBalance()).asObject());

        attendeeTable.getItems().setAll(db.getAttendees());
    }

    @FXML
    private void back() {
        switchScene("AdminDashboard.fxml", backButton);
    }

    private void switchScene(String fxmlFile, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("EVENTATI");
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Failed to load " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}