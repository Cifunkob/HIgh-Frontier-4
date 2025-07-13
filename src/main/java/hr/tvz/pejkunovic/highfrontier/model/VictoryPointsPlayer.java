package hr.tvz.pejkunovic.highfrontier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VictoryPointsPlayer implements Serializable {
    private Long playerId;
    private Double victoryPoints;

}
