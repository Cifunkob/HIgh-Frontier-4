package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.threads.PlayerOneServerThread;
import hr.tvz.pejkunovic.highfrontier.threads.PlayerTwoServerThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static String playerName;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UniverseMapController.class.getResource("universeMapView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 750);
        stage.setTitle("Ekran za login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if(args.length > 0) {
            playerName = args[0];

            if(playerName.equals("player2")) {
                PlayerTwoServerThread playerTwoServerThread = new PlayerTwoServerThread();
                Thread thread = new Thread(playerTwoServerThread);
                thread.start();
            }
            else if(playerName.equals("player1")) {
                PlayerOneServerThread playerOneServerThread = new PlayerOneServerThread();
                Thread thread = new Thread(playerOneServerThread);
                thread.start();
            }

            launch();
        }
        else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Player must be provided");
                alert.setContentText("Either player1 or player2 must be provided as an argument to the application.");
                alert.showAndWait();
            });
        }
    }
}