package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.CardUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsUtil;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.RoverCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.List;
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
            cardUtil.addRoverToPlayer(player.getId(), roverId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

//MORAS PROMIJENIT CONTROLLER TJ POZIV VIEWA