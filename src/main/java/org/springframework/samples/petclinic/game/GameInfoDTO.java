package org.springframework.samples.petclinic.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameInfoDTO {

    Integer id;
    GameMode gameMode;
    Integer numPlayers;
    Integer winner;
    Integer creator;
    Integer gameTime;
    GameStatus status;
    Integer gameId;

    public GameInfoDTO(){}

    public GameInfoDTO(GameInfo g){
        this.id = g.getId();
        this.gameMode = g.getGameMode();
        this.numPlayers = g.getNumPlayers();
        this.winner = g.getWinner();
        this.creator = g.getCreator().getId();
        this.gameTime = g.getGameTime();
        this.status = g.getStatus();
        this.gameId = g.getGame().getId();
    }
    
}
