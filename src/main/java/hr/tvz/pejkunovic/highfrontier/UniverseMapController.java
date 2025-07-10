package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.util.ButtonUtil;
import hr.tvz.pejkunovic.highfrontier.util.ControllerOpenUtil;
import hr.tvz.pejkunovic.highfrontier.database.ConnectionSpaceLocationUtil;
import hr.tvz.pejkunovic.highfrontier.database.MotorCardsUtil;
import hr.tvz.pejkunovic.highfrontier.database.PlayerUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsUtil;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.MotorCard;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.RoverCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniverseMapController {
    @FXML
    private Button mercuryButton;
    @FXML
    private Button laGrangeM2VButton;
    @FXML
    private Button venusButton;
    @FXML
    private Button zoozveButton;
    @FXML
    private Button laGrangeV2EButton;
    @FXML
    private Button earthButton;
    @FXML
    private Button lunaButton;
    @FXML
    private Button laGrangeE2MButton;
    @FXML
    private Button marsButton;
    @FXML
    private Button fobosButton;
    @FXML
    private Button deimosButton;
    @FXML
    private Button laGrangeM2JButton;
    @FXML
    private Button jupiterButton;
    @FXML
    private Button ortozijaButton;
    @FXML
    private Button haldenaButton;
    @FXML
    private Button tebaButton;
    @FXML
    private Button hermipaButton;
    @FXML
    private Button europaButton;
    @FXML
    private Button karmaButton;
    @FXML
    private Button roverShopButton;
    ControllerOpenUtil controllerOpenUtil=new ControllerOpenUtil();
    ButtonUtil buttonUtil=new ButtonUtil();
    public Player player;
    protected Map<Long, Button> buttonMap= new HashMap<>();
    protected List<Long> spaceLocationNeighbours=new ArrayList<>();
    public void initialize() {
        try {
            spaceLocationNeighbours= ConnectionSpaceLocationUtil.getConnectedLocationIds(3L);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupButton();
        setupPlayer();
        changeButtonStyleNeighbour();
        try {
            List<MotorCard> motorCards=MotorCardsUtil.getAllMotorCards();
            List<RoverCard> roverCards= RoverCardsUtil.getAllRoverCards();
            System.out.println(roverCards);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void openSpaceObjectMenu(ActionEvent event){
        controllerOpenUtil.openSpaceObjectMenu(event,player,this);
    }

    public void openRoverShop(){
       controllerOpenUtil.openRoverShop(player);
    }

    public void openEngineShop(){
       controllerOpenUtil.openEngineShop(player);
    }

    public void openPlayerResources(){
        controllerOpenUtil.openResourceInformation(player);
    }

    public void setupPlayer(){
        try {
            player= PlayerUtil.getPlayerById(1L);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setupButton(){
        buttonUtil.addButtonsToMap(buttonMap,
                mercuryButton, venusButton, earthButton, marsButton, jupiterButton, lunaButton, fobosButton, deimosButton, europaButton, tebaButton,
                haldenaButton, hermipaButton, karmaButton, ortozijaButton, zoozveButton, laGrangeM2VButton, laGrangeV2EButton, laGrangeE2MButton, laGrangeM2JButton
        );
    }
    public void changeButtonStyleNeighbour(){
        buttonMap.forEach((id, button) -> {
            if (spaceLocationNeighbours.contains(id)) {
                button.setStyle("-fx-background-color: green;");
            } else if (id.equals(player.getLocationId())) {
                button.setStyle("-fx-background-color: blue;");
            } else {
                button.setStyle("-fx-background-color: red;");
            }
        });
    }

    public void updatePlayerLocation(Long newLocationId) {
        player.setLocationId(newLocationId);
        try {
            spaceLocationNeighbours = ConnectionSpaceLocationUtil.getConnectedLocationIds(newLocationId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        changeButtonStyleNeighbour();
    }
}

/* Izbrisi nek stoji dok je ispod 200
public void updateColors(){
    try {
        spaceLocationNeighbours= ConnectionSpaceLocationUtil.getConnectedLocationIds(player.getLocationId());
        changeButtonStyleNeighbour();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
*/
