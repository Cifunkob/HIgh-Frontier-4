package hr.tvz.pejkunovic.highfrontier.model.cardmodels;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class TechnologyCard {
    protected Long id;
    protected String name;
    protected Integer mass;
    protected Integer cost;


}
