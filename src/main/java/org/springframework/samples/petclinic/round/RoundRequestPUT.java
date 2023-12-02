package org.springframework.samples.petclinic.round;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RoundRequestPUT {
    
    private Integer winner;

    private Integer loser;

    private Integer roundTime;

}