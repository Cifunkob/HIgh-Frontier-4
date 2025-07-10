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

    public MotorCard(Long id, String name, Integer mass, Integer cost, Integer thrust, Integer isp) {
        super(id, name, mass, cost);
        this.thrust = thrust;
        this.isp = isp;
    }

    @Override
    public String toString() {
        return "MotorCard{" +
                "thrust=" + thrust +
                ", isp=" + isp +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", mass=" + mass +
                ", cost=" + cost +
                '}';
    }
}