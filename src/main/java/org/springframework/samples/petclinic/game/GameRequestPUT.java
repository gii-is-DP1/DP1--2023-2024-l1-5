package org.springframework.samples.petclinic.game;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class GameRequestPUT {

    private Integer winner;

    private Integer gameTime;

    private GameStatus status;

    private List<Integer> rounds;
    
    private List<Integer> players;

}
