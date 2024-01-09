package org.springframework.samples.petclinic.deck;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

@Service
public class DeckService {

    DeckRepository deckRepository;
    RoundService roundService;

    @Autowired
    public DeckService(DeckRepository deckRepository, RoundService roundService) {
        this.deckRepository = deckRepository;
        this.roundService = roundService;
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
        Round r = roundService.getRoundById(roundID).get();
        d.setRound(r);
        return deckRepository.save(d);
    }

    @Transactional(readOnly = true)
    public Optional<Deck> getDeckByRoundId(Integer roundId) {
        return deckRepository.findByRoundId(roundId);
    }

    @Transactional
    public Deck updateDeck(@Valid Deck deck, Integer idToUpdate, List<Card> ls) {
        Integer numCards = ls.size();
        Deck deckToUpdate = getDeckByRoundId(idToUpdate).get();
        Round round = roundService.getRoundById(idToUpdate).get();
        BeanUtils.copyProperties(deck, deckToUpdate, "id");
        deckToUpdate.setNumberOfCards(numCards);
        deckToUpdate.setRound(round);
        deckToUpdate.setCards(ls);
        return saveDeck(deckToUpdate);
    }

}