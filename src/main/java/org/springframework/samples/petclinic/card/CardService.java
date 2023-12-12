package org.springframework.samples.petclinic.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        return cardRepository.save(card);
    }

    @Transactional
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Card getCardById(Integer id) throws DataAccessException {
        return cardRepository.findById(id).get();
    }
    @Transactional
    public Card createNewCard(Integer cardId) throws DataAccessException {
        Card newCard = new Card();
        Card toCopy= cardRepository.findById(cardId).get();
        newCard.setId(cardId+16);
        newCard.setImage(toCopy.getImage());
        toCopy.getSymbols().size();
        List<Symbol> symbols = toCopy.getSymbols();
        newCard.setSymbols(symbols);
        return saveCard(newCard);
    }

    public List<Card> get16LastCards() {
        return cardRepository.get16LastCards();
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
