package oop.oop5;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ViewRooms {
    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, String> roomIdColumn;
    @FXML private TableColumn<Room, Integer> capacityColumn;
    @FXML private Button backButton;
    Database db = Database.getInstance();
    @FXML
    public void initialize() {
        roomIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomId()));
        capacityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacity()).asObject());
        roomTable.getItems().setAll(db.getRooms());
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
