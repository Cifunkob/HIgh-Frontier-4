package hr.tvz.pejkunovic.highfrontier.util;

import hr.tvz.pejkunovic.highfrontier.EngineShopController;
import hr.tvz.pejkunovic.highfrontier.SpaceLocationInfoController;
import hr.tvz.pejkunovic.highfrontier.UniverseMapController;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerOpenUtil {
    public void openSpaceObjectMenu(ActionEvent event, Player player, UniverseMapController parentController){
        try {
            String buttonName = ((Button)event.getSource()).getText();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/tvz/pejkunovic/highfrontier/spaceLocationInfo.fxml"));
            Parent root = fxmlLoader.load();
            SpaceLocationInfoController controller = fxmlLoader.getController();
            controller.setUp(buttonName,player,parentController);
            Stage popupStage = new Stage();
            popupStage.setTitle("Space Object Information");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Failed to load the space object menu.");
            alert.showAndWait();
        }
    }

    public void openRoverShop(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/tvz/pejkunovic/highfrontier/roverShopView.fxml"));
            Parent root = fxmlLoader.load();
            Stage popupStage = new Stage();
            popupStage.setTitle("Rover shop");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Failed to load rover shop.");
            alert.showAndWait();
        }
    }

    public void openEngineShop(Player player){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/tvz/pejkunovic/highfrontier/engineShopView.fxml"));
            Parent root = fxmlLoader.load();
            EngineShopController controller = fxmlLoader.getController();
            controller.setUp(player);
            Stage popupStage = new Stage();
            popupStage.setTitle("Engine shop");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Failed to load rover shop.");
            alert.showAndWait();
        }
    }
}
