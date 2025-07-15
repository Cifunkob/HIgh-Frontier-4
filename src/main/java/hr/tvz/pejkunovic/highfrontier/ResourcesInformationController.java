package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.CardUtil;
import hr.tvz.pejkunovic.highfrontier.database.MotorCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.PlayerDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.exception.BuyingException;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardmodels.MotorCard;
import hr.tvz.pejkunovic.highfrontier.model.cardmodels.RoverCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ResourcesInformationController {
    @FXML
    private Label engineLabel;
    @FXML
    private Label roversLabel;
    @FXML
    private Label waterLabel;
    @FXML
    private Label fuelLabel;
    private Player player;
    private CardUtil cardUtil=new CardUtil();

    public void initialize(){
     engineLabel.setText("Your rocket currently has no engine");
    }

    public void setUp(Player player){
        boolean playerHasMotor;
        this.player=player;
        waterLabel.setText(player.getWater().toString());
        fuelLabel.setText(player.getFuel().toString());
        try {
            playerHasMotor = cardUtil.playerHasMotor(player.getId());
            if(playerHasMotor){
                MotorCard motorCard= MotorCardsDatabaseUtil.getMotorCardById(cardUtil.getMotorIdByPlayerId(player.getId()));
                engineLabel.setText("Your rocket has a "+motorCard.getName()+" engine");
            }
        } catch (SQLException e) {
            throw new BuyingException(e);
        }
        Optional<List<Long>> roversList=cardUtil.getRoversByPlayerId(player.getId());
        if(roversList.isPresent()){
            List<RoverCard> roverCards = roversList.get().stream().map(id -> {
                        try {
                            return RoverCardsDatabaseUtil.getRoverCardById(id);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .toList();
            roverCards.stream().forEach(card -> roversLabel.setText(roversLabel.getText()+"\n"+card.getName()));
        }
    }

    public void buyFuel(){
        if (player.getWater()>=1){
            player.setWater(player.getWater()-1);
            player.setFuel(player.getFuel()+100);
            try {
                PlayerDatabaseUtil.updatePlayerFuel(player.getId(), player.getFuel());
                PlayerDatabaseUtil.updatePlayerWater(player.getId(), player.getWater());
                waterLabel.setText(player.getWater().toString());
                fuelLabel.setText(player.getFuel().toString());
            } catch (SQLException e) {
                throw new BuyingException(e);
            }
        }
    }
}
