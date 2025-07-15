package hr.tvz.pejkunovic.highfrontier.model.cardmodels;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoverCard extends TechnologyCard {
    private Integer efficiency;

    public RoverCard(Long id, String name, Integer mass, Integer cost, Integer efficiency) {
        super(id, name, mass, cost);
        this.efficiency = efficiency;
    }
}
