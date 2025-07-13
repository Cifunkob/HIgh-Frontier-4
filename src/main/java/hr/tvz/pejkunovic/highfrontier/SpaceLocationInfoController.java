package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.*;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.MotorCard;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.ConnectionSpaceLocation;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.SpaceLocation;
import hr.tvz.pejkunovic.highfrontier.util.ControllerOpenUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    @FXML
    Button chooseRoverButton;
    private UniverseMapController universeMapController;
    private Player player;
    private String buttonName;
    private CardUtil cardUtil = new CardUtil();
    private SpaceLocation spaceLocation;
    Integer fuelCost;

    public void setUp(String buttonName, Player player, UniverseMapController parentController) {
        this.buttonName = buttonName;
        this.player = player;
        this.universeMapController = parentController;
        setUpInitial();
        checkIsRoverOnPlanet();
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
        setUpInitial();
    }

    public void setUpInitial() {
        Optional<ConnectionSpaceLocation> connectionSpaceLocation;
        try {
            spaceLocation = SpaceLocationDatabaseUtil.getSpaceLocationByName(buttonName);
            connectionSpaceLocation = ConnectionSpaceLocationUtil.getConnectionByLocationIds(player.getLocationId(), spaceLocation.getId());
            if (connectionSpaceLocation.isPresent()) {
                thrustCost.setText(connectionSpaceLocation.get().getThrustCost().toString());
            } else if (player.getLocationId().equals(spaceLocation.getId())) {
                thrustCost.setText("Current location");
            } else {
                thrustCost.setText("Unreacheable");
            }
            if (thrustCost.getText().equals("Unreacheable") || thrustCost.getText().equals("Current location")) {
                moveButton.setDisable(true);
            } else {
                moveButton.setDisable(false);
            }
            if (!thrustCost.getText().equals("Current location")) {
                chooseRoverButton.setDisable(true);
            }
            nameButton.setText(spaceLocation.getName());
            typeButton.setText(spaceLocation.getType().toString());
            if (cardUtil.playerHasMotor(player.getId()) && !thrustCost.getText().equals("Current location")) {
                MotorCard motorCard = MotorCardsDatabaseUtil.getMotorCardById(cardUtil.getMotorIdByPlayerId(player.getId()));
                fuelCost = calculateFuelCostToLocation(motorCard, Integer.parseInt(thrustCost.getText()));
                thrustCost.setText(fuelCost.toString());
            } else {
                thrustCost.setText("Buy an engine to calculate price of movement");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void move() {
        /*player.setLocationId(spaceLocation.getId());
        universeMapController.updateColors();*/
        try {
            if (cardUtil.playerHasMotor(player.getId())) {
                MotorCard motorCard = MotorCardsDatabaseUtil.getMotorCardById(cardUtil.getMotorIdByPlayerId(player.getId()));
                if (player.getFuel() >= fuelCost) {
                    player.setFuel(player.getFuel() - fuelCost);
                    PlayerDatabaseUtil.updatePlayerFuel(player.getId(), player.getFuel() - fuelCost);
                    universeMapController.updatePlayerLocation(spaceLocation.getId());
                    checkIsRoverOnPlanet();
                    Stage stage = (Stage) moveButton.getScene().getWindow();
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Not Enough Fuel");
                    alert.setHeaderText("Not enough fuel to move.");
                    alert.setContentText("You need to buy more fuel if you want to move");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Engine");
                alert.setHeaderText("Player has no engine.");
                alert.setContentText("You need to buy an engine if you want to move");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void openChooseRover() {
        ControllerOpenUtil controllerOpenUtil = new ControllerOpenUtil();
        controllerOpenUtil.openChooseRover(player, spaceLocation);
        Stage stage = (Stage) moveButton.getScene().getWindow();
        stage.close();

    }

    public void checkIsRoverOnPlanet() {
        DeploymentDatabaseUtil deploymentDatabaseUtil = new DeploymentDatabaseUtil();
        try {
            if (deploymentDatabaseUtil.hasPlayerDeployedOnPlanet(player.getId(), spaceLocation.getId())) {
                chooseRoverButton.setDisable(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int calculateFuelCostToLocation(MotorCard motor, int thrustCost) {
        if (motor == null) {
            throw new IllegalArgumentException("Motor cannot be null.");
        }

        int scalingFactor = 100000; // significantly bigger to boost output

        double rawCost = (motor.getMass() * thrustCost * motor.getThrust() * scalingFactor) / (double) motor.getIsp();
        int fuelCost = (int) Math.ceil(rawCost);

        System.out.println("Mass: " + motor.getMass() + ", ThrustCost: " + thrustCost + ", Thrust: " + motor.getThrust() + ", ISP: " + motor.getIsp() + ", RawCost: " + rawCost + ", FuelCost: " + fuelCost);

        return Math.min(Math.max(fuelCost, 100), 1000);
    }
}
