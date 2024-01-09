package org.springframework.samples.petclinic.statistic;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PlayerSRequestPUT {

    private Integer numberG;

    private Integer winN;

    private Integer loseN;

    private Integer puntosC;

    private Integer maxD;

    private Integer minD;
    
}
