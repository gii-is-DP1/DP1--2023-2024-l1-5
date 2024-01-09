package org.springframework.samples.petclinic.statistic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerSDTO {
    
    Integer id;
    Integer numberG;
    Integer winN;
    Integer loseN;
    Integer puntosC;
    Integer maxD;
    Integer minD;
    Integer playerId;

    public PlayerSDTO(){}

    public PlayerSDTO(PlayerStatistic p){
        this.id = p.getId();
        this.numberG = p.getNumber_of_games();
        this.winN = p.getWin_number();
        this.loseN = p.getLose_number();
        this.puntosC = p.getCompetitive_points();
        this.maxD = p.getMax_duration();
        this.minD = p.getMin_duration();
        this.playerId = p.getPlayer().getId();
    }
}
