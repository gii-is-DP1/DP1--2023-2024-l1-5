package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {

    Integer id;
    GameMode gameMode;
    Integer numPlayers;
    Integer winner;
    Integer creator;
    Integer gameTime;
    GameStatus status;
    List<Player> playerList;
    List<Integer> roundList;

    public GameDTO(){}

    public GameDTO(Game g){
        this.id = g.getId();
        this.gameMode = g.getGameMode();
        this.numPlayers = g.getNumPlayers();
        this.winner = g.getWinner();
        this.creator = g.getCreator().getId();
        this.gameTime = g.getGameTime();
        this.status = g.getStatus();
        List<Player> pLs  = g.getPlayers();
        List<User> playerList2 = new ArrayList<>();
        for(Player p: pLs){
            playerList2.add(p.getUser());
        }
        this.playerList = g.getPlayers();
        List<Round> rLs  = g.getRounds();
        List<Integer> roundList = new ArrayList<>();
        for(Round r: rLs){
            roundList.add(r.getId());
        }
        this.roundList = roundList;
    }
    
}
