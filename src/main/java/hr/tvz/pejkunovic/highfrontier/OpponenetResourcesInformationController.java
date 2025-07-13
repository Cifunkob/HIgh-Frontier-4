package hr.tvz.pejkunovic.highfrontier;

import hr.tvz.pejkunovic.highfrontier.database.CardUtil;
import hr.tvz.pejkunovic.highfrontier.database.MotorCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.VictoryPointsPlayer;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.MotorCard;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.RoverCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class OpponenetResourcesInformationController {
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
        private Boolean playerHasMotor;
        private RoverCardsDatabaseUtil roverCardsDatabaseUtil=new RoverCardsDatabaseUtil();

        public void initialize(){
                engineLabel.setText("Your rocket currently has no engine");
        }

        public void setUp(Player opponent, List<Deployment> deployments, VictoryPointsPlayer victoryPointsPlayer){
                this.player=opponent;
                waterLabel.setText(opponent.getWater().toString());
                fuelLabel.setText(opponent.getFuel().toString());
                try {
                        playerHasMotor = cardUtil.playerHasMotor(opponent.getId());
                        if(playerHasMotor){
                                MotorCard motorCard= MotorCardsDatabaseUtil.getMotorCardById(cardUtil.getMotorIdByPlayerId(opponent.getId()));
                                engineLabel.setText("Your opponent has a  "+motorCard.getName()+" engine");
                        }
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
                Optional<List<Long>> roversList=cardUtil.getRoversByPlayerId(opponent.getId());
                if(roversList.isPresent()){
                        List<RoverCard> roverCards = roversList.get().stream().map(id -> {
                                        try {
                                                return roverCardsDatabaseUtil.getRoverCardById(id);
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
