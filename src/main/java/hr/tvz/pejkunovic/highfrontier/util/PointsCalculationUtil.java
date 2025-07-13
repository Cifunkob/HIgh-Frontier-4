package hr.tvz.pejkunovic.highfrontier.util;

import hr.tvz.pejkunovic.highfrontier.database.PlayerDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.RoverCardsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.SpaceLocationDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.database.VictoryPointsDatabaseUtil;
import hr.tvz.pejkunovic.highfrontier.model.Deployment;
import hr.tvz.pejkunovic.highfrontier.model.Player;
import hr.tvz.pejkunovic.highfrontier.model.cardModels.RoverCard;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.SpaceLocation;

import java.sql.SQLException;

public class PointsCalculationUtil {

    public static Double calculatePoints(Deployment deployment){
        try {
            RoverCard roverCard= RoverCardsDatabaseUtil.getRoverCardById(deployment.getRoverCardId());
            Player player= PlayerDatabaseUtil.getPlayerById(deployment.getPlayerId());
            SpaceLocation location= SpaceLocationDatabaseUtil.getSpaceLocationById(deployment.getSpaceLocationId());
            Double efficiencyFactor = roverCard.getEfficiency() / 100.0;
            Integer basePoints = 10;
            Double rawPoints = location.getResourceRichness() * efficiencyFactor * basePoints;
            PlayerDatabaseUtil.updatePlayerWater(player.getId(), player.getWater()+1);
            return (double) Math.max(1, Math.round(rawPoints));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
