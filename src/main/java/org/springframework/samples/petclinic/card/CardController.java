package org.springframework.samples.petclinic.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameDTO;
import org.springframework.samples.petclinic.game.GameMode;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.hand.HandService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundMode;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cards")
@Tag(name = "Cards", description = "The Cards management API")
@SecurityRequirement(name = "bearerAuth")
public class CardController {

    private final CardService cardService;
    private final UserService userService;
    private final RoundService roundService;
    private final DeckService deckService;
    private final HandService handService;
    private static final String PLAYER_AUTH = "PLAYER";



    @Autowired
    public CardController(CardService cardService, UserService userService, RoundService roundService, DeckService deckService, HandService handService) {
        this.cardService = cardService;
        this.userService = userService;
        this.roundService = roundService;
        this.deckService = deckService;
        this.handService = handService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Card>> getAllCards() {
        return new ResponseEntity<>(cardService.getAllCards(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Card> getCardById(@PathVariable("id") Integer id) {
        Card card = cardService.getCardById(id);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Card> createNewCard(@PathVariable("id") Integer cardId) {
        Card newCard = this.cardService.createNewCard(cardId);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    // @PutMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<Card> updateCard(Integer id,Integer hId) {
    //     return new ResponseEntity<>(cardService.updateCard(id,hId), HttpStatus.OK);
    // }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable("id") Integer id) {
        cardService.deleteCard(id);
    }

    
   



}

