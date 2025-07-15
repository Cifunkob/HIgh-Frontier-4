package hr.tvz.pejkunovic.highfrontier.util;

import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.GameState;
import hr.tvz.pejkunovic.highfrontier.model.VictoryPointsPlayer;

import java.util.List;

public class GameStateUtils {

    private GameStateUtils() {}
    public static GameState createCurrentGameState(List<Deployment> deployments, List<VictoryPointsPlayer> victoryPointsPlayers, Integer turnNumber) {
        GameState gameStateToSave = new GameState();

        gameStateToSave.setDeployments(deployments);
        gameStateToSave.setVictoryPointsPlayers(victoryPointsPlayers);
        gameStateToSave.setTurnNumber(turnNumber);
        return gameStateToSave;
    }
}
