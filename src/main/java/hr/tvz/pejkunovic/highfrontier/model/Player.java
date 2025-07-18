package hr.tvz.pejkunovic.highfrontier.model;

import hr.tvz.pejkunovic.highfrontier.model.cardmodels.TechnologyCard;
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
        private Long id;
        private String name;
        private Integer fuel;
        private Integer water;
        private Long locationId;
        private List<TechnologyCard> techCards;
        private List<Deployment> outposts;
}
