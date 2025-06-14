package hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpaceLocation {
    private Long id;
    private String name;
    private LocationType type;
    private Integer resourceRichness;
}
