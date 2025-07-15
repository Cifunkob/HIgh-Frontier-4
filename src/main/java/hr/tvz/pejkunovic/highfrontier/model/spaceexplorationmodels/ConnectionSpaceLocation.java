package hr.tvz.pejkunovic.highfrontier.model.spaceexplorationmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionSpaceLocation {
    Long id;
    Long sourceId;
    Long destinationId;
    Integer thrustCost;
}
