package org.springframework.samples.petclinic.card;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    @Transactional
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Card getCardById(Integer id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Card> getCardsByDeckId(Integer deckId) {
        return cardRepository.findByDeckId(deckId);
    }

    @Transactional
    public List<Card> getCardsByHandId(Integer handId) {
        return cardRepository.findByHandId(handId);
    }
}
