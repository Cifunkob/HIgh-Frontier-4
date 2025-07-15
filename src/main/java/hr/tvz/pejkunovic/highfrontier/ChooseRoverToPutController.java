package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.CardUtil;
import hr.tvz.pejkunovic.highfrontier.database.DeploymentDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.exception.PlayerException;
import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardmodels.RoverCard;
import hr.tvz.pejkunovic.highfrontier.model.spaceexplorationmodels.SpaceLocation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChooseRoverToPutController {
    @FXML
    ChoiceBox<String> roverChoiceBox;
    List<String> roverNames=new ArrayList<>();
    CardUtil cardUtil=new CardUtil();
    Player player;
    List<RoverCard> playerRoverCards=new ArrayList<>();
    SpaceLocation spaceLocation;
    public void setUp(Player player,SpaceLocation spaceLocation){
        this.player=player;
        this.spaceLocation=spaceLocation;
        setUpInitial();
    }

    public void setUpInitial(){
        Optional<List<Long>> optionalRoverIds = cardUtil.getRoversByPlayerId(player.getId());
        if(optionalRoverIds.isPresent()){
          List<Long> roverIds=optionalRoverIds.get();
          for(Long roverId : roverIds){
              try {
                  RoverCard roverCard=RoverCardsDatabaseUtil.getRoverCardById(roverId);
                  playerRoverCards.add(roverCard);
                  roverNames.add(roverCard.getName());
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
            roverChoiceBox.getItems().setAll(roverNames);
        }
}

public void deployRover(ActionEvent event){
    Deployment deployment=new Deployment(player.getId(), playerRoverCards.get(roverChoiceBox.getSelectionModel().getSelectedIndex()).getId(), spaceLocation.getId());
    try {
        DeploymentDatabaseUtil.createDeployment(deployment);
        cardUtil.removeRoverFromPlayer(player.getId(), playerRoverCards.get(roverChoiceBox.getSelectionModel().getSelectedIndex()).getId());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    } catch (SQLException e) {
        throw new PlayerException(e);
    }
}
}