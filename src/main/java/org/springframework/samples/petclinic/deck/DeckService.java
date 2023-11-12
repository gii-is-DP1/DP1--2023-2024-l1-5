package org.springframework.samples.petclinic.deck;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Service
public class DeckService {

    DeckRepository deckRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Transactional
    public Deck saveDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    @Transactional
    public List<Deck> getAllDecks() {
        return deckRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Deck> getDeckById(Integer id) {
        return deckRepository.findById(id);
    }

    @Transactional
    public Deck createDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    @Transactional(readOnly = true)
    public Deck getDeckByRoundId(Integer roundId) {
        return deckRepository.findByRoundId(roundId);
    }

    @Transactional
    public Deck updateDeck(@Valid Deck deck, Integer idToUpdate, List<Card> ls, Round round) {
        Integer numCards = ls.size();
        Deck deckToUpdate = getDeckByRoundId(idToUpdate);
        BeanUtils.copyProperties(deck, deckToUpdate, "id");
        deckToUpdate.setNumberOfCards(numCards);
        deckToUpdate.setCards(ls);
        deckToUpdate.setRound(round);
        return createDeck(deckToUpdate);
    }

}