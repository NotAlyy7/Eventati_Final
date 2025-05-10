package oop.oop5;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class DeleteCategory {
    @FXML private TextField idField;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    Database db = Database.getInstance();
    @FXML
    private void delete() {
        String id = idField.getText().trim();

        if (id.isEmpty()) {
            showAlert("Error", "Category ID cannot be empty.");
            return;
        }

        boolean removed = db.getCategories().removeIf(category -> category.getCategoryID().equals(id));

        if (removed) {
            showAlert("Success", "Category removed successfully.");
        } else {
            showAlert("Error", "No category found with that ID.");
        }

        idField.clear();
    }

    @FXML
    private void back() {
        switchScene("ManageCategories.fxml", backButton);
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