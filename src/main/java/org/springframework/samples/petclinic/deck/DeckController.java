package org.springframework.samples.petclinic.deck;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/decks")
@Tag(name = "Decks", description = "The Deck management API")
@SecurityRequirement(name = "bearerAuth")
public class DeckController {

    private final DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
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
    
}
