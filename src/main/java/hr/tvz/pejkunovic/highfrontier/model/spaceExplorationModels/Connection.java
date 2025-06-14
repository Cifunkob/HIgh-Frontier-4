package hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    Long id;
    SpaceLocation source;
    SpaceLocation destination;
    Integer thrustCost;
}
