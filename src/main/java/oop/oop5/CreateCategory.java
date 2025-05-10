

package oop.oop5;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class CreateCategory {
    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private Button createButton;
    @FXML private Button backButton;

    @FXML
    private void create() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();

        if (name.isEmpty() || description.isEmpty()) {
            showAlert("Error", "Category name and description cannot be empty.");
            return;
        }

        String id = "C" + (Database.getCategories().size() + 1);
        Category newCategory = new Category(id,name);
        showAlert("Success", "Category created successfully!");
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
