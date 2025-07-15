package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.CardUtil;
import hr.tvz.pejkunovic.highfrontier.database.MotorCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.PlayerDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.exception.UniverseException;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardmodels.MotorCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EngineShopController {

    @FXML
    private Button buyIonSparkButton;
    @FXML
    private Button buyFusionDriveButton;
    @FXML
    private Button buyChemicalBlazeButton;
    @FXML
    private Button buyNuclearPulseButton;
    @FXML
    private Button buySolarThermalButton;
    private Player player;
    private CardUtil cardUtil=new CardUtil();
    private Map<String, Button> engineButtons=new HashMap<>();

    public void initialize(){
        engineButtons = Map.of(
                "buyIonSparkButton", buyIonSparkButton,
                "buyFusionDriveButton", buyFusionDriveButton,
                "buyChemicalBlazeButton", buyChemicalBlazeButton,
                "buyNuclearPulseButton", buyNuclearPulseButton,
                "buySolarThermalButton", buySolarThermalButton
        );
    }

    private final Map<String, Long> buttonToEngineId = Map.of(
            "buyIonSparkButton", 1L,
            "buyFusionDriveButton", 2L,
            "buyChemicalBlazeButton", 3L,
            "buyNuclearPulseButton", 4L,
            "buySolarThermalButton", 5L
    );

    public void setUp(Player player){
      this.player=player;
        try {
            boolean playerHasMotor = cardUtil.playerHasMotor(player.getId());
            if(playerHasMotor){
                engineButtons.forEach((id, button) ->
                    button.setDisable(true)
                );
            }
        } catch (SQLException e) {
            throw new UniverseException(e);
        }
    }

    @FXML
    private void buyEngine(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        Long engineId = buttonToEngineId.get(buttonId);
        buySpecificEngine(engineId);
        engineButtons.forEach((id, button) ->
                button.setDisable(true)
        );
    }

    private void buySpecificEngine(Long engineId) {
            MotorCard motorCard = null;
            try {
                motorCard = MotorCardsDatabaseUtil.getMotorCardById(engineId);
                if (player.getWater() > motorCard.getCost()) {
                    player.setWater(player.getWater() - motorCard.getCost());
                    PlayerDatabaseUtil.updatePlayerWater(player.getId(), player.getWater());
                    cardUtil.addMotorToPlayer(player.getId(), engineId);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Engine Purchased");
                    alert.setHeaderText("Engine Purchased");
                    alert.setContentText("Player " + player.getName() + " successfully bought an engine");
                    alert.showAndWait();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Not enough water to buy an engine!");
                    alert.setHeaderText("You cannot afford this engine!");
                    alert.setContentText("Gaather resources or go with a cheaper option");
                    alert.showAndWait();
                }
            } catch(SQLException e){
                    throw new UniverseException(e);
                }
            }
        }



