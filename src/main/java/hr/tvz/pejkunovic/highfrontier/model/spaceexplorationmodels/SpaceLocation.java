package hr.tvz.pejkunovic.highfrontier.model.spaceexplorationmodels;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class SpaceLocation {
    private Long id;
    private String name;
    private LocationType type;
    private Integer resourceRichness;

    public SpaceLocation(Long id, String name, LocationType type, Integer resourceRichness) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.resourceRichness = resourceRichness;
    }
}
