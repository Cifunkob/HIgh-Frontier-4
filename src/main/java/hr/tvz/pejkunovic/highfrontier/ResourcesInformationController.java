package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.CardUtil;
import hr.tvz.pejkunovic.highfrontier.database.MotorCardsUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsUtil;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.MotorCard;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.RoverCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ResourcesInformationController {
    @FXML
    private Label engineLabel;
    @FXML
    private Label roversLabel;
    Player player;
    CardUtil cardUtil=new CardUtil();
    RoverCardsUtil roverCardsUtil;
    Boolean playerHasMotor= null;

    public void initialize(){
     engineLabel.setText("Your rocket currently has no engine");
    }

    public void setUp(Player player){
        this.player=player;
        try {
            playerHasMotor = cardUtil.playerHasMotor(player.getId());
            if(playerHasMotor){
                MotorCard motorCard=MotorCardsUtil.getMotorCardById(cardUtil.getMotorIdByPlayerId(player.getId()));
                engineLabel.setText("Your rocket has a "+motorCard.getName()+" engine");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Optional<List<Long>> roversList=cardUtil.getRoversByPlayerId(player.getId());
        if(roversList.isPresent()){
            List<RoverCard> roverCards = roversList.get().stream().map(id -> {
                        try {
                            return roverCardsUtil.getRoverCardById(id);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
            roverCards.stream().forEach(card -> roversLabel.setText(roversLabel.getText()+"\n"+card.getName()));
        }
    }

}
