package org.springframework.samples.petclinic.deck;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/decks")
@Tag(name = "Decks", description = "The Deck management API")
@SecurityRequirement(name = "bearerAuth")
public class DeckController {

    private final DeckService deckService;
    private final CardService cardService;

    @Autowired
    public DeckController(DeckService deckService, CardService cardService) {
        this.deckService = deckService;
        this.cardService = cardService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Deck>> getAllDecks() {
        return new ResponseEntity<>(deckService.getAllDecks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Deck> getDeckById(@PathVariable("id")Integer id) {
        Optional<Deck> deck = deckService.getDeckById(id);
        if (!deck.isPresent()) {
            throw new ResourceNotFoundException("Deck" , "id" , id );
        }
        return new ResponseEntity<>(deck.get(), HttpStatus.OK);
    }


    @GetMapping("/round/{roundId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Deck> getDeckByRoundId(@PathVariable("roundId") Integer roundId) {
        Deck deck = deckService.getDeckByRoundId(roundId).get();
        return new ResponseEntity<>(deck, HttpStatus.OK);
    }
    

    @PutMapping("/round/{roundId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Deck> updatePitDeck(@PathVariable("roundId") Integer roundId, @RequestParam("cardId") Integer cardId) {
        Deck deck = deckService.getDeckByRoundId(roundId).get();
        List<Card> cards = deck.getCards();
        Card newDeckCard = cardService.getCardById(cardId);
        cards.add(0,newDeckCard);
        deck.setCards(cards);
        deckService.saveDeck(deck);
        return new ResponseEntity<>(deck, HttpStatus.OK);
    }
    
}