package hr.tvz.pejkunovic.highfrontier.model;

import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.SpaceLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Outpost {
    private Long id;
    private SpaceLocation location;
    private Integer victoryPoints;
}
