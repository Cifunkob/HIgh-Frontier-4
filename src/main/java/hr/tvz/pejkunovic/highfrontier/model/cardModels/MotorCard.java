package hr.tvz.pejkunovic.highfrontier.model.cardModels;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MotorCard extends TechnologyCard {
    private Integer thrust;
    private Integer isp;
}