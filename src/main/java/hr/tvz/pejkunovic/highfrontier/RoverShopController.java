package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.CardUtil;
import hr.tvz.pejkunovic.highfrontier.database.PlayerDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.RoverCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.Map;

public class RoverShopController {

    @FXML
    private Button buyExplorerButton;
    @FXML
    private Button buySurveyorButton;
    @FXML
    private Button buyHaulerButton;
    @FXML
    private Button buyNomadButton;
    @FXML
    private Button buyPioneerButton;
    public Player player;
    private CardUtil cardUtil=new CardUtil();

    private final Map<String, Long> buttonToRoverId = Map.of(
            "buyExplorerButton", 1L,
            "buySurveyorButton", 2L,
            "buyHaulerButton", 3L,
            "buyNomadButton", 4L,
            "buyPioneerButton", 5L
    );

    public void setUp(Player player){
        this.player=player;
    }

    @FXML
    private void buyRover(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        Long roverId = buttonToRoverId.get(buttonId);
        buySpecificRover(roverId);
    }

    private void buySpecificRover(Long roverId) {
        try {
            RoverCard roverCard=RoverCardsDatabaseUtil.getRoverCardById(roverId);
            if(player.getWater()>=roverCard.getCost()){
                player.setWater(player.getWater()-roverCard.getCost());
                try {
                    PlayerDatabaseUtil.updatePlayerWater(player.getId(), player.getWater());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                cardUtil.addRoverToPlayer(player.getId(), roverId);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rover Purchased");
                alert.setHeaderText("Rover Purchased");
                alert.setContentText("Player "+ player.getName() +" successfully bought a rover");
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not enough water to buy a rover!");
                alert.setHeaderText("You cannot afford this rover!");
                alert.setContentText("Try gathering more water or go with a cheaper option");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

