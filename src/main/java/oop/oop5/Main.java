package oop.oop5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Database db = Database.getInstance();
            Image icon = new Image(getClass().getResourceAsStream("eventati_2__1_-removebg-preview.png"));
            primaryStage.getIcons().add(icon);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("EVENTATI");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
