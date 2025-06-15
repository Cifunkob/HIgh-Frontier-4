package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.ConnectionSpaceLocationUtil;
import hr.tvz.pejkunovic.highfrontier.database.PlayerUtil;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    public static Player player;
    protected static Map<Long, Button> buttonMap= new HashMap<>();
    protected static List<Long> spaceLocationNeighbours=new ArrayList<>();
    public void initialize() {
        try {
            spaceLocationNeighbours= ConnectionSpaceLocationUtil.getConnectedLocationIds(3L);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupButton();
        setupPlayer();
        changeButtonStyleNeighbour();
    }
    public void openSpaceObjectMenu(ActionEvent event){
        try {
            String buttonName = ((Button)event.getSource()).getText();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spaceLocationInfo.fxml"));
            Parent root = fxmlLoader.load();
            SpaceLocationInfoController controller = fxmlLoader.getController();
            controller.setButtonName(buttonName);
            Stage popupStage = new Stage();
            popupStage.setTitle("Space Object Information");
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Failed to load the space object menu.");
            alert.showAndWait();
        }
    }
    public void setupPlayer(){
        try {
            player= PlayerUtil.getPlayerById(1L);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setupButton(){
        buttonMap.put(1L, mercuryButton);
        buttonMap.put(2L, venusButton);
        buttonMap.put(3L, earthButton);
        buttonMap.put(4L, marsButton);
        buttonMap.put(5L, jupiterButton);
        buttonMap.put(6L, lunaButton);
        buttonMap.put(7L, fobosButton);
        buttonMap.put(8L, deimosButton);
        buttonMap.put(9L, europaButton);
        buttonMap.put(10L, tebaButton);
        buttonMap.put(11L, haldenaButton);
        buttonMap.put(12L, hermipaButton);
        buttonMap.put(13L, karmaButton);
        buttonMap.put(14L, ortozijaButton);
        buttonMap.put(15L, zoozveButton);
        buttonMap.put(16L, laGrangeM2VButton);
        buttonMap.put(17L, laGrangeV2EButton);
        buttonMap.put(18L, laGrangeE2MButton);
        buttonMap.put(19L, laGrangeM2JButton);
    }
    public static void changeButtonStyleNeighbour(){
        buttonMap.forEach((id, button) -> {
            if (spaceLocationNeighbours.contains(id)) {
                button.setStyle("-fx-background-color: green;");
            } else if (id== player.getLocationId()) {
                button.setStyle("-fx-background-color: blue;");
            } else {
                button.setStyle("-fx-background-color: red;");
            }
        });
    }
    public static void updateColors(){
        try {
            spaceLocationNeighbours= ConnectionSpaceLocationUtil.getConnectedLocationIds(player.getLocationId());
            changeButtonStyleNeighbour();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
