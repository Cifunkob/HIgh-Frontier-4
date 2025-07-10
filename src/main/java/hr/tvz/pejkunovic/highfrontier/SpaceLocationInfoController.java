package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.ConnectionSpaceLocationUtil;
import hr.tvz.pejkunovic.highfrontier.database.SpaceLocationUtil;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.ConnectionSpaceLocation;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.SpaceLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.*;
import java.util.Optional;

public class SpaceLocationInfoController {
    @FXML
    private Text nameButton;
    @FXML
    private Text typeButton;
    @FXML
    private Text thrustCost;
    @FXML
    Button moveButton;
    private UniverseMapController universeMapController;
    private Player player;
    private String buttonName;
    private SpaceLocation spaceLocation;
    public void initialize(){

    }
    public void setUp(String buttonName, Player player, UniverseMapController parentController) {
        this.buttonName = buttonName;
        this.player = player;
        this.universeMapController = parentController;
        setUpInitial();
    }
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
        setUpInitial();
    }
    public void setUpInitial () {
         Optional<ConnectionSpaceLocation> connectionSpaceLocation;
        try {
            spaceLocation = SpaceLocationUtil.getSpaceLocationByName(buttonName);
            connectionSpaceLocation= ConnectionSpaceLocationUtil.getConnectionByLocationIds(player.getLocationId(), spaceLocation.getId());
           if(connectionSpaceLocation.isPresent()){
               thrustCost.setText(connectionSpaceLocation.get().getThrustCost().toString());
           } else if(player.getLocationId().equals(spaceLocation.getId())) {
               thrustCost.setText("Current location");
           }
           else {
               thrustCost.setText("Unreacheable");
           }
           if(thrustCost.getText().equals("Unreacheable") || thrustCost.equals("Current location")){
               moveButton.setDisable(true);
           }
           else {
               moveButton.setDisable(false);
           }
            nameButton.setText(spaceLocation.getName());
            typeButton.setText(spaceLocation.getType().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void move(){
        /*player.setLocationId(spaceLocation.getId());
        universeMapController.updateColors();*/
        universeMapController.updatePlayerLocation(spaceLocation.getId());
    }
}
