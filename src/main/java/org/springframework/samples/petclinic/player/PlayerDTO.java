package org.springframework.samples.petclinic.player;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    String playerUsername;
    State state;
    Set<Integer> friendsList;// ESto un Set de id no?
    List<GameDTO> gameDTOList;
    //Integer hand;//esto tmb

    public PlayerDTO(){}

    public PlayerDTO(Player p){
        this.playerUsername = p.getPlayerUsername();
        this.state = p.getState();
        Set<Player> pSet  = p.getFriendsList();
        Set<Integer> playerSet = new HashSet();
        for(Player pAux : pSet){
            playerSet.add(pAux.getId());
        }
        this.friendsList= playerSet;
         List<Game> gLs  = p.getGame_list();
         List<GameDTO> Ls = new ArrayList<>();
         for(Game g: gLs){
             Ls.add(new GameDTO(g));
         }
         this.gameDTOList = Ls;
         //this.hand = p.getHand().getId();
    }
    
}
