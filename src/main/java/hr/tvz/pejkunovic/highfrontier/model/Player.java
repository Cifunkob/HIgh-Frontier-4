package hr.tvz.pejkunovic.highfrontier.model;

import hr.tvz.pejkunovic.highfrontier.model.cardModels.TechnologyCard;
import hr.tvz.pejkunovic.highfrontier.model.spaceExplorationModels.SpaceLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
        private String name;
        private Integer fuel;
        private Integer water;
        private SpaceLocation location;
        private List<TechnologyCard> techCards;
        private List<Outpost> outposts;
}
