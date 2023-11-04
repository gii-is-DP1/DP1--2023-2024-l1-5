package org.springframework.samples.petclinic.deck;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;



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

    @PostMapping
    public Deck createDeck(Deck deck) {
        return deckRepository.save(deck);
    }

}
