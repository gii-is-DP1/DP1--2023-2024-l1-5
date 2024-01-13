package org.springframework.samples.petclinic.game;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingDTO {
    
    String username;
    Integer numGames;

    public RankingDTO(){}

    public RankingDTO(String username, Integer numGames){
        this.username = username;
        this.numGames = numGames;
    }


}
