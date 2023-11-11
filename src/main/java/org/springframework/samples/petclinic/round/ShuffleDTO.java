package org.springframework.samples.petclinic.round;


import org.springframework.samples.petclinic.game.GameMode;
import org.springframework.samples.petclinic.game.Game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShuffleDTO {

    RoundMode roundMode;
    GameMode gameMode;

    public ShuffleDTO(){}

    public ShuffleDTO(Game g, Round r){

        this.roundMode = r.getRoundMode();
        this.gameMode = g.getGameMode();

    }


    
}
