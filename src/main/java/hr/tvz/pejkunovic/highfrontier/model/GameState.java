package hr.tvz.pejkunovic.highfrontier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameState implements Serializable {
    private List<Deployment> deployments;
    private List<VictoryPointsPlayer> victoryPointsPlayers;
    private Integer turnNumber;
}
