package oop.oop5;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import static oop.oop5.Scenecontroller.setCurrentAttendee;

public class Login {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;

    @FXML
    private void login() {
        Database db = Database.getInstance();
        String username = usernameField.getText();
        String password = passwordField.getText();

        for (Admin admin : db.getAdmins()) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                showAlert("Success", "Admin login successful!");
                switchScene("AdminDashboard.fxml", loginButton);
                return;
            }
        }

        for (Attendee attendee : db.getAttendees()) {
            if (attendee.getUsername().equals(username) && attendee.getPassword().equals(password)) {
                showAlert("Success", "Attendee login successful!");
                switchScene("Attendeemenu.fxml", loginButton);
                setCurrentAttendee(attendee);
                return;
            }
        }

        for (Organizer organizer : db.getOrganizers()) {
            if (organizer.getUsername().equals(username) && organizer.getPassword().equals(password)) {
                showAlert("Success", "Organizer login successful!");
                switchScene("hello-view.fxml", loginButton);
                return;
            }
        }

        showAlert("Error", "Username or password incorrect!");
    }

    @FXML
    private void openRegister() {
        switchScene("Register.fxml", registerButton);
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