package oop.oop5;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.beans.property.SimpleStringProperty;

public class ViewCategories {
    Database db = Database.getInstance();
    @FXML private TableView<Category> categoryTable;
    @FXML private TableColumn<Category, String> categoryNameColumn;
    @FXML private TableColumn<Category, String> descriptionColumn;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        categoryNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoryID()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoryName()));
        categoryTable.getItems().setAll(db.getCategories());
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