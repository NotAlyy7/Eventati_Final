package oop.oop5;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class AdminDashboard {
    @FXML private Button addRoomButton;
    @FXML private Button viewRoomsButton;
    @FXML private Button viewAttendeesButton;
    @FXML private Button viewCategoriesButton;
    @FXML private Button manageCategoriesButton;
    @FXML private Button logoutButton;

    @FXML
    private void addRoom() {
        switchScene("AddRoom.fxml", addRoomButton);
    }

    @FXML
    private void viewRooms() {
        switchScene("ViewRooms.fxml", viewRoomsButton);
    }

    @FXML
    private void viewAttendees() {
        switchScene("ViewAttendees.fxml", viewAttendeesButton);
    }

    @FXML
    private void viewCategories() {
        switchScene("ViewCategories.fxml", viewCategoriesButton);
    }

    @FXML
    private void manageCategories() {
        switchScene("ManageCategories.fxml", manageCategoriesButton);
    }

    @FXML
    private void logout() {
        switchScene("Login.fxml", logoutButton);
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
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

