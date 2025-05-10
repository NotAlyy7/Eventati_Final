package oop.oop5;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class UpdateCategory {
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private Button updateButton;
    @FXML private Button backButton;

    @FXML
    private void update() {
        String id = idField.getText().trim();
        String newName = nameField.getText().trim();
        String newDescription = descriptionField.getText().trim();

        if (id.isEmpty() || newName.isEmpty() || newDescription.isEmpty()) {
            showAlert("Error", "All fields must be filled.");
            return;
        }

        Category categoryToUpdate = Database.getCategories().stream()
                .filter(category -> category.getCategoryID().equals(id))
                .findFirst()
                .orElse(null);

        if (categoryToUpdate != null) {
            categoryToUpdate.setCategoryName(newName);
            showAlert("Success", "Category updated successfully!");
        } else {
            showAlert("Error", "No category found with that ID.");
        }

        idField.clear();
        nameField.clear();
        descriptionField.clear();
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
