package oop.oop5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Register {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private DatePicker birthDatePicker;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TextField workHoursField;
    @FXML private TextField balanceField;
    @FXML private TextField addressField;
    @FXML private TextField interestsField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private Button registerButton;
    @FXML private Label workHours;
    @FXML private Label balance;
    @FXML private Label address;
    @FXML private Label interests;
    @FXML private Label gender;

    public void initialize() {
        roleComboBox.setOnAction(e -> updateFieldsBasedOnRole());
    }

    private void updateFieldsBasedOnRole() {
        String selectedRole = roleComboBox.getValue();
        workHoursField.setVisible("Admin".equals(selectedRole));
        balanceField.setVisible("Attendee".equals(selectedRole));
        addressField.setVisible("Attendee".equals(selectedRole));
        interestsField.setVisible("Attendee".equals(selectedRole));
        genderComboBox.setVisible("Attendee".equals(selectedRole));
        workHours.setVisible("Admin".equals(selectedRole));
        balance.setVisible("Attendee".equals(selectedRole));
        address.setVisible("Attendee".equals(selectedRole));
        interests.setVisible("Attendee".equals(selectedRole));
        gender.setVisible("Attendee".equals(selectedRole));
    }

    @FXML
    private void register() {
        Database db = Database.getInstance();
        String username = usernameField.getText();
        String password = passwordField.getText();
        LocalDate birthDate = birthDatePicker.getValue();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || db.isUsernameTaken(username)) {
            showAlert("Error", "Username is taken or empty!");
            return;
        }

        if (password.length() < 8) {
            showAlert("Error", "Password must be at least 8 characters!");
            return;
        }

        if (birthDate == null) {
            showAlert("Error", "Please select a valid birthdate!");
            return;
        }

        DateOfBirth dob = new DateOfBirth(birthDate.getDayOfMonth(), birthDate.getMonthValue(), birthDate.getYear());

        switch (role) {
            case "Admin" -> registerAdmin(username, password, dob);
            case "Attendee" -> registerAttendee(username, password, dob);
            case "Organizer" -> registerOrganizer(username, password, dob);
            default -> showAlert("Error", "Select a valid role!");
        }


    }

    private void registerAdmin(String username, String password, DateOfBirth dob) {
        Database db = Database.getInstance();
        try {
            double workingHours = Double.parseDouble(workHoursField.getText());
            if (workingHours <= 0 ) {
                showAlert("Error", "Working hours must be positive!");
                return;
            }
            if (workingHours > 24) {
                showAlert("Error", "Working hours must be less than 24!");
                return;
            }
            Admin newAdmin = new Admin(username, password, dob, "Admin", workingHours);
            db.getAdmins().add(newAdmin);
            showAlert("Success", "Admin registered successfully!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid working hours format!");
        }

    }

    private void registerAttendee(String username, String password, DateOfBirth dob) {
        Database db = Database.getInstance();
        try {
            double balance = Double.parseDouble(balanceField.getText());
            if (balance <= 0) {
                showAlert("Error", "Balance must be positive!");
                return;
            }
            String address = addressField.getText();
            String interests = interestsField.getText();
            String gender = genderComboBox.getValue();
            if (address.isBlank() || interests.isBlank() || gender == null) {
                showAlert("Error", "All fields must be filled!");
                return;
            }
            Attendee newAttendee = new Attendee(username, password, dob, address, balance, interests, Gender.valueOf(gender));
            db.getAttendees().add(newAttendee);
            showAlert("Success", "Attendee registered successfully!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid balance format!");
        }

    }

    private void registerOrganizer(String username, String password, DateOfBirth dob) {
        Database db = Database.getInstance();
        Organizer newOrganizer = new Organizer(username, password, dob);
        db.getOrganizers().add(newOrganizer);
        showAlert("Success", "Organizer registered successfully!");

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
private Parent root;
    private Stage stage;
    private Scene  scene;
    public void redirectToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}