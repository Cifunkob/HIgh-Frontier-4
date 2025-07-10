package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.CardUtil;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    public Player player;
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
            Boolean playerHasMotor = cardUtil.playerHasMotor(player.getId());
            if(playerHasMotor){
                engineButtons.forEach((id, button) -> {
                    button.setDisable(true);
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void buyEngine(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        Long engineId = buttonToEngineId.get(buttonId);
        buySpecificEngine(engineId);
        engineButtons.forEach((id, button) -> {
                button.setDisable(true);
        });
    }

    private void buySpecificEngine(Long engineId) {
        try {
            cardUtil.addMotorToPlayer(player.getId(), engineId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
