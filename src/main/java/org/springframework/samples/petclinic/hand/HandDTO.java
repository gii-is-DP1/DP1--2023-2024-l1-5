package org.springframework.samples.petclinic.hand;


import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.petclinic.card.Card;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandDTO {

    Integer id;
    Integer numCartas;
    //List<Integer> cards;
    List<Card> cards;
    Integer round;
    Integer player;

    public HandDTO(){}

    public HandDTO(Hand h){

        this.id = h.getId();
        this.numCartas = h.getNumCartas();
        // List<Card> cLs  = h.getCards();
        // List<Integer> cardList = new ArrayList<>();
        // for(Card c: cLs){
        //     cardList.add(c.getId());
        // }
        // this.cards = cardList;
        this.cards = h.getCards();
        this.round = h.getRound().getId();
        this.player = h.getPlayer().getId();
    }
    
}
