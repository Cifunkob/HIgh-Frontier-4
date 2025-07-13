package hr.tvz.pejkunovic.highfrontier.util;

import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.VictoryPointsPlayer;

import java.util.List;

public class VictoryPointsUtil {

    public static List<VictoryPointsPlayer> updateVictoryPoints(List<VictoryPointsPlayer> victoryPointsPlayers, Deployment deployment) {
        Double points = PointsCalculationUtil.calculatePoints(deployment);
        victoryPointsPlayers.stream().filter(
                victoryPointsPlayer -> victoryPointsPlayer.getPlayerId().
                        equals(deployment.getPlayerId())).forEach(victoryPointsPlayer ->
        {victoryPointsPlayer.setVictoryPoints(victoryPointsPlayer.getVictoryPoints()+points);});
    return victoryPointsPlayers;
    }
}
