package hr.tvz.pejkunovic.highfrontier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UniverseMapController.class.getResource("universeMapView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 750);
        stage.setTitle("Ekran za login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}