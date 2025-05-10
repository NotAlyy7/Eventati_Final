package oop.oop5;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable
{
    @FXML private TextField eventIdField;
    @FXML private TextField eventNameField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker datePicker;
    @FXML private TextField priceField;
    @FXML private ChoiceBox<Category> categoryField;
    @FXML private Button createEventButton;
    @FXML private ChoiceBox<Room> roomChoiceBox;
    @FXML private ChoiceBox<String> timeSlotChoiceBox;

    private Stage stage;
    private Scene scene;
    private Parent root;


    private String[] timeSlots = {
            "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM",
            "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM",
            "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM",
            "11:00 PM", "12:00 AM"
    };


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");

    private Room[] rooms = new Room[10];

    public void initialize (URL arg0, ResourceBundle arg1)
    {
        Database db = Database.getInstance();
        timeSlotChoiceBox.getItems().addAll(timeSlots);
        Thread t = new Thread(() -> {

            Platform.runLater(() -> {
                Room[] rooms = db.getRooms().toArray(new Room[0]);
                Category[] categories = db.getCategories().toArray(new Category[0]);
                roomChoiceBox.getItems().addAll(rooms);
                categoryField.getItems().addAll(categories);

            });
        });
        t.start();

    }

    private void showAlert(Alert.AlertType alertType, String title, String message)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void HandleCreateEvent(ActionEvent event)
    {
        Database db = Database.getInstance();
        try
        {

            if (eventIdField.getText().isEmpty() ||
                    eventNameField.getText().isEmpty() ||
                    descriptionField.getText().isEmpty() ||
                    datePicker.getValue() == null ||
                    timeSlotChoiceBox.getValue() == null ||
                    priceField.getText().isEmpty() ||
                    categoryField.getValue() == null ||
                    roomChoiceBox.getValue() == null)
            {

                showAlert(Alert.AlertType.ERROR, "Missing Input", "Please fill in all fields.");
                return;
            }


            LocalTime time;
            try
            {
                time = LocalTime.parse(timeSlotChoiceBox.getValue(), formatter);
            } catch (Exception e)
            {
                showAlert(Alert.AlertType.ERROR, "Invalid Time Input", "Please enter a valid time slot.");
                return;
            }

            double price;
            try
            {
                price = Double.parseDouble(priceField.getText());
                if (price < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Price", "Ticket price must be a positive number.");
                return;
            }
            int id ;
            try
            {
                id = Integer.parseInt(eventIdField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Id", "Id must be a number");
                return;
            }




            String name = eventNameField.getText();
            String description = descriptionField.getText();
            LocalDate date = datePicker.getValue();
            Category category = categoryField.getValue();
            Room room = roomChoiceBox.getValue();

            Organizer.createEvent(id, name, description, date, time, price, category, room);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Event created successfully.");

        } catch (Exception e)
        {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchtoUpdateEvent(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UpdateEventButton.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void SwitchtoCancelEvent(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CancelEventButton.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchtoLogout(ActionEvent event) throws IOException
    {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
