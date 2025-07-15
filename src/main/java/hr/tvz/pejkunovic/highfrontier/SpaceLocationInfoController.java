package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.*;
import hr.tvz.pejkunovic.highfrontier.exception.MovementException;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardmodels.MotorCard;
import hr.tvz.pejkunovic.highfrontier.model.spaceexplorationmodels.ConnectionSpaceLocation;
import hr.tvz.pejkunovic.highfrontier.model.spaceexplorationmodels.SpaceLocation;
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
    public static final String CURRENT_LOCATION="Current location";

    public void setUp(String buttonName, Player player, UniverseMapController parentController) {
        this.buttonName = buttonName;
        this.player = player;
        this.universeMapController = parentController;
        setUpInitial();
        checkIsRoverOnPlanet();
    }

    public void setUpInitial() {
        try {
            spaceLocation = SpaceLocationDatabaseUtil.getSpaceLocationByName(buttonName);

            Optional<ConnectionSpaceLocation> connection = ConnectionSpaceLocationUtil.getConnectionByLocationIds(
                    player.getLocationId(), spaceLocation.getId());

            String thrustText;
            if (connection.isPresent()) {
                thrustText = connection.get().getThrustCost().toString();
            } else if (player.getLocationId().equals(spaceLocation.getId())) {
                thrustText = CURRENT_LOCATION;
            } else {
                thrustText = "Unreacheable";
            }
            thrustCost.setText(thrustText);

            boolean isUnreachable = "Unreacheable".equals(thrustText);
            boolean isCurrentLocation = CURRENT_LOCATION.equals(thrustText);
            moveButton.setDisable(isUnreachable || isCurrentLocation);
            chooseRoverButton.setDisable(!isCurrentLocation);

            nameButton.setText(spaceLocation.getName());
            typeButton.setText(spaceLocation.getType().toString());

            if (cardUtil.playerHasMotor(player.getId()) && !isCurrentLocation) {
                MotorCard motorCard = MotorCardsDatabaseUtil.getMotorCardById(cardUtil.getMotorIdByPlayerId(player.getId()));
                fuelCost = calculateFuelCostToLocation(motorCard, Integer.parseInt(thrustText));
                thrustCost.setText(fuelCost.toString());
            } else if (!isCurrentLocation) {
                thrustCost.setText("Buy an engine to calculate price of movement");
            }

        } catch (SQLException e) {
            throw new MovementException(e);
        }
    }


    public void move() {
        try {
            if (cardUtil.playerHasMotor(player.getId())) {
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
            throw new MovementException(e);
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
            throw new MovementException(e);
        }
    }

    public int calculateFuelCostToLocation(MotorCard motor, int thrustCost) {
        if (motor == null) {
            throw new IllegalArgumentException("Motor cannot be null.");
        }

        int scalingFactor = 100000;

        double rawCost = (motor.getMass() * thrustCost * motor.getThrust() * scalingFactor) / (double) motor.getIsp();

        return Math.clamp((int) Math.ceil(rawCost), 100, 1000);
    }
}
