package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.hand.HandRepository;
import org.springframework.samples.petclinic.symbol.Symbol;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CardService {

    CardRepository cardRepository;
    HandRepository handRepository;

    @Autowired
    public CardService(CardRepository cardRepository, HandRepository handRepository) {
        this.cardRepository = cardRepository;
        this.handRepository = handRepository;
    }

    @Transactional
    public Card saveCard(Card card) throws DataAccessException {
        cardRepository.save(card);
        return card;
    }

    @Transactional
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Transactional
    public List<Card> getCardsPlus16Id(){
        List<Card> cards = cardRepository.get16LastCards();
        for (Card card : cards) {
            card.setId(card.getId()+16);
        }
        return cards;      
    }

    @Transactional(readOnly = true)
    public Card getCardById(Integer id) throws DataAccessException {
        return cardRepository.findById(id).get();
    }
    @Transactional
    public Card createNewCard(Integer cardId) {
        Card newCard = new Card();
        Card toCopy= cardRepository.findById(cardId).get();
        newCard.setId(cardId+16);
        newCard.setImage(toCopy.getImage());
        newCard.setSymbols(toCopy.getSymbols());
        newCard.setDeck(toCopy.getDeck());
       // newCard.setHand(toCopy.getHand());
        return saveCard(newCard);
    }


    @Transactional
    public Card updateCard(Integer cardId,Integer handId) throws DataAccessException {
        Card toUpdate = cardRepository.findById(cardId).get();
        //Hand hand = handRepository.findById(handId).get();
        //toUpdate.setHand(hand);
        return saveCard(toUpdate);
    }


    // @Transactional
    // public List<Card> getCardsByDeckId(Integer deckId) {
    //     return cardRepository.findByDeckId(deckId);
    // }

    // @Transactional
    // public List<Card> getCardsByHandId(Integer handId) {
    //     return cardRepository.findByHandId(handId);
    // }

    @Transactional
    public void deleteCard(Integer id) {
        cardRepository.deleteById(id);
    }
}
