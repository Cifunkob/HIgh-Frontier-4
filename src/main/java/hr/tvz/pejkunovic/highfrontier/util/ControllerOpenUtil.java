package hr.tvz.pejkunovic.highfrontier.util;

import hr.tvz.pejkunovic.highfrontier.*;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.spaceexplorationmodels.SpaceLocation;
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

    public static final String ERROR="Error";

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
            popupStage.focusedProperty().addListener(event1 ->
                controller.checkIsRoverOnPlanet()
            );
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ERROR);
            alert.setContentText("Failed to load the space object menu.");
            alert.showAndWait();
        }
    }

    public void openRoverShop(Player player){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/tvz/pejkunovic/highfrontier/roverShopView.fxml"));
            Parent root = fxmlLoader.load();
            RoverShopController controller = fxmlLoader.getController();
            controller.setUp(player);
            Stage popupStage = new Stage();
            popupStage.setTitle("Rover shop");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ERROR);
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
            alert.setHeaderText(ERROR);
            alert.setContentText("Failed to load engine shop.");
            alert.showAndWait();
        }
    }

    public void openResourceInformation(Player player){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/tvz/pejkunovic/highfrontier/playerResourcesView.fxml"));
            Parent root = fxmlLoader.load();
            ResourcesInformationController controller = fxmlLoader.getController();
            controller.setUp(player);
            Stage popupStage = new Stage();
            popupStage.setTitle("Resources information");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ERROR);
            alert.setContentText("Failed to resource information.");
            alert.showAndWait();
        }
    }

    public void openChooseRover(Player player, SpaceLocation spaceLocation){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/tvz/pejkunovic/highfrontier/chooseRoverToPutView.fxml"));
            Parent root = fxmlLoader.load();
            ChooseRoverToPutController controller = fxmlLoader.getController();
            controller.setUp(player,spaceLocation);
            Stage popupStage = new Stage();
            popupStage.setTitle("Choose rover to put");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ERROR);
            alert.setContentText("Failed to load Put rover menu.");
            alert.showAndWait();
        }
    }

    public void openDeployment(Player player){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/tvz/pejkunovic/highfrontier/deploymentsView.fxml"));
            Parent root = fxmlLoader.load();
            DeploymentController controller = fxmlLoader.getController();
            controller.setUp(player);
            Stage popupStage = new Stage();
            popupStage.setTitle("Deployment information");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ERROR);
            alert.setContentText("Failed to load deployment menu.");
            alert.showAndWait();
        }
    }

    public void openOpponentResourcesInformation(Player opponent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/tvz/pejkunovic/highfrontier/opponentResourcesView.fxml"));
            Parent root = fxmlLoader.load();
            OpponenetResourcesInformationController controller = fxmlLoader.getController();
            controller.setUp(opponent);
            Stage popupStage = new Stage();
            popupStage.setTitle("Opponent information");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ERROR);
            alert.setContentText("Failed to load opponent resources.");
            alert.showAndWait();
        }
    }
}

