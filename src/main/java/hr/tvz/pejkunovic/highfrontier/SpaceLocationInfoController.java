package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.ConnectionSpaceLocationUtil;
import hr.tvz.pejkunovic.highfrontier.database.SpaceLocationUtil;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.ConnectionSpaceLocation;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.SpaceLocation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.*;
import java.util.List;
import java.util.Objects;
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
    private String buttonName;
    private SpaceLocation spaceLocation;
    private Optional<ConnectionSpaceLocation> connectionSpaceLocation;
    public void initialize(){

    }
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
        setUpInitial();
    }
    public void setUpInitial () {
        try {
            spaceLocation = SpaceLocationUtil.getSpaceLocationByName(buttonName);
            connectionSpaceLocation= ConnectionSpaceLocationUtil.getConnectionByLocationIds(UniverseMapController.player.getLocationId(), spaceLocation.getId());
           if(connectionSpaceLocation.isPresent()){
               thrustCost.setText(connectionSpaceLocation.get().getThrustCost().toString());
           } else if(UniverseMapController.player.getLocationId().equals(spaceLocation.getId())) {
               thrustCost.setText("Current location");
           }
           else {
               thrustCost.setText("Unreacheable");
           }
           if(thrustCost.getText().equals("Unreacheable") ||thrustCost.equals("Current location")){
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
        UniverseMapController.player.setLocationId(spaceLocation.getId());
        UniverseMapController.updateColors();
    }
}
