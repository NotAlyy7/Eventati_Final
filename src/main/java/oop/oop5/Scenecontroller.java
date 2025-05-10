package oop.oop5;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Scenecontroller  implements Initializable {
    private static Attendee currentAttendee;
    Database db = Database.getInstance();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField eventSelectionField;
    @FXML
    private TextArea outputArea;
    @FXML
    private TextArea outputArea1;
    @FXML
    private TextArea outputArea2;
    @FXML
    private TextField inputfield1;
    @FXML
    private TextField inputfield2;
    @FXML
    private TextField inputfield3;
    @FXML
    private TextField inputfield4;
    @FXML
    private TextField outputfield1;
    @FXML
    private TextField outputfield2;
    @FXML
    private TextField outputfield3;
    @FXML
    private TextField outputfield4;
    @FXML
    private TextField balancefield1;
    public static void setCurrentAttendee(Attendee attendee) {
        currentAttendee = attendee;
    }
  public static Attendee getCurrentAttendee() {
        return currentAttendee;
    }
    public void switchView(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Viewevent.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Scenecontroller controller = loader.getController();

        controller.ViewEventcont(controller.outputArea1);
    }

    public void switchBook(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Bookevent.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Scenecontroller controller = loader.getController();
        controller.ViewEventcont(controller.outputArea);
    }

    public void switchdashboard(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Attendeemenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Scenecontroller controller = loader.getController();

    }

    static String s;

    public void switchbook2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Book.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Scenecontroller controller = loader.getController();
        s = eventSelectionField.getText();
        Thread t = new Thread(() -> {

            Platform.runLater(() -> {
                String output = currentAttendee.bookEvent(s);
                controller.outputArea2.setText(output);
            });
        });
        t.start();
    }

    public void ViewEventcont(TextArea outputArea) {
        outputArea.setText(Attendee.viewEvents(db));
    }

    public void switchupdate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Update.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Scenecontroller controller = loader.getController();

        controller.setCurrentAttendee(getCurrentAttendee());


        controller.outputfield1.setText(getCurrentAttendee().getUsername());
        controller.outputfield3.setText(getCurrentAttendee().getInterests());
        controller.outputfield2.setText(getCurrentAttendee().getPassword());
        controller.outputfield4.setText(getCurrentAttendee().getAddress());
    }

    public void updatecont(ActionEvent event) {
        if (currentAttendee == null) return;


        String newUsername = inputfield1.getText().trim();
        String newPassword = inputfield2.getText().trim();
        String newInterests = inputfield3.getText().trim();
        String newAddress = inputfield4.getText().trim();

        if (!newPassword.isEmpty() && newPassword.length() < 8) {
            showErrorAlert("Password must be at least 8 characters long!");

        }

        // Validate username if it's being changed
        if (!newUsername.isEmpty() && db.isUsernameTaken(newUsername)) {
            showErrorAlert("Username already taken!");

        }
        if (!newUsername.isEmpty() && !db.isUsernameTaken(newUsername) && !newPassword.isEmpty() && newPassword.length() > 8) {


            try {
                if (!newUsername.isEmpty()) currentAttendee.setUsername(newUsername);
                if (!newPassword.isEmpty()) currentAttendee.setPassword(newPassword);
                if (!newInterests.isEmpty()) currentAttendee.setInterests(newInterests);
                if (!newAddress.isEmpty()) currentAttendee.setAddress(newAddress);

                Attendeemenu.setCurrentAttendee(currentAttendee);
                refreshDisplayedInfo();
                showSuccessAlert("Update successful!");
            } catch (Exception e) {
                showErrorAlert("Error updating attendee: " + e.getMessage());
            }
        }


        if (!newUsername.isEmpty()) currentAttendee.setUsername(inputfield1,db,outputfield1);
        if (!newPassword.isEmpty()) currentAttendee.setpassword(inputfield2,db,outputfield2);
        if (!newInterests.isEmpty()) currentAttendee.setInterests(newInterests);
        if (!newAddress.isEmpty()) currentAttendee.setAddress(newAddress);


        Attendeemenu.setCurrentAttendee(currentAttendee);

        inputfield2.clear();
        inputfield1.clear();
        inputfield3.clear();
        inputfield4.clear();
        outputfield1.setText(currentAttendee.getUsername());
        outputfield3.setText(currentAttendee.getInterests());
        outputfield4.setText(currentAttendee.getAddress());
        outputfield2.setText(currentAttendee.getPassword());
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("EVENTATI");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to logout.");
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


    private void refreshDisplayedInfo() {
        if (currentAttendee != null) {
            outputfield1.setText(currentAttendee.getUsername());
            outputfield3.setText(currentAttendee.getInterests());
            outputfield4.setText(currentAttendee.getAddress());
        }
    }
    public void showErrorAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    public void showSuccessAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public TextField getOutputfield1() {
        return outputfield1;
    }

    public void setOutputfield1(String s) {
        outputfield1.setText(s);
    }

    public TextField getInputfield1() {
        return inputfield1;
    }

    public void setInputfield1(String s) {
        inputfield1.setText(s);
    }

    public TextField getInputfield2() {
        return inputfield2;
    }

    public void setInputfield2(String s) {
        inputfield2.setText(s);
    }

    public TextField getInputfield3() {
        return inputfield3;
    }

    public void setInputfield3(String s) {
        inputfield3.setText(s);
    }


    public TextField getInputfield4() {
        return inputfield4;
    }

    public void setInputfield4(String s) {
        inputfield4.setText(s);
    }


    public TextField getOutputfield2() {
        return outputfield2;
    }

    public void setOutputfield2(String s) {
        outputfield2.setText(s);
    }

    public TextField getOutputfield3() {
        return outputfield3;
    }

    public void setOutputfield3(String s) {
        outputfield3.setText(s);
    }

    public TextField getOutputfield4() {
        return outputfield4;
    }

    public void setOutputfield4(String s) {
        outputfield4.setText(s);
    }
    public void viewbalance()  {
        Thread t = new Thread(() -> {

            Platform.runLater(() -> {
                balancefield1.setText(currentAttendee.getWallet().toString());
            });
        });
        t.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewbalance();
    }
}