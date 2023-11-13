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

    Integer id;
    String playerUsername;
    State state;
    Set<Integer> friendsList;// ESto un Set de id no?
    //Integer hand;//esto tmb

    public PlayerDTO(){}

    public PlayerDTO(Player p){
        
        this.id = p.getId();
        this.playerUsername = p.getPlayerUsername();
        this.state = p.getState();
        Set<Player> pSet  = p.getFriendsList();
        Set<Integer> playerSet = new HashSet();
        for(Player pAux : pSet){
            playerSet.add(pAux.getId());
        }
        this.friendsList= playerSet;
         //this.hand = p.getHand().getId();
    }
    
}
