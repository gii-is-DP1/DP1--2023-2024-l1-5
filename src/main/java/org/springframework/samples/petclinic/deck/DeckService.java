package org.springframework.samples.petclinic.deck;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

@Service
public class DeckService {

    DeckRepository deckRepository;
    RoundRepository roundRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository, RoundRepository roundRepository) {
        this.deckRepository = deckRepository;
        this.roundRepository = roundRepository;
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
    public Deck createDeck(Integer roundID) {
        Deck d = new Deck();
        d.setNumberOfCards(10);
        Round r = roundRepository.findById(roundID).get();
        d.setRound(r);
        return deckRepository.save(d);
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
        deckToUpdate.setRound(round);
        for (Card card : ls) {
            card.setDeck(deckToUpdate);
        }
        return saveDeck(deckToUpdate);
    }

}