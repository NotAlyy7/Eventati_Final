package oop.oop5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Attendeemenu extends Application {

    private static Attendee currentAttendee = new Attendee("Hosny", "hosny1234",
            new DateOfBirth(3, 4, 2006), "Cairo street1", 1200, "5%", Gender.Male);

    @Override
    public void start(Stage stage) throws IOException {
      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Attendeemenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("EVENTATI");
        stage.setScene(scene);
        stage.show();
        Scenecontroller controller = loader.getController();

    }

    public  Attendeemenu (Stage stage, Database db) {}

    public static Attendee getCurrentAttendee() {
        return currentAttendee;
    }

    public static void setCurrentAttendee(Attendee attendee) {
        currentAttendee = attendee;
    }

    public static void main(String[] args) {
        launch(args);
    }
}