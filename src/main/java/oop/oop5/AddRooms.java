package oop.oop5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class AddRooms {
    @FXML
    private TextField capacityField;
    @FXML
    private TextField amenitiesField;
    @FXML
    private Button addRoomButton;
    @FXML
    private Button backButton;

    @FXML
    private void handleAddRoom() {
        Database db = Database.getInstance();
        try {
            int capacity = Integer.parseInt(capacityField.getText());
            List<String> amenities = Arrays.asList(amenitiesField.getText().split("\\s*,\\s*"));
            Room newRoom = new Room("R" + (db.getRooms().size() + 1), capacity, amenities);
            db.getRooms().add(newRoom);
            showAlert("Success", "Room added successfully!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for capacity.");
        }
    }

    @FXML
    public void handleBack() {
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
