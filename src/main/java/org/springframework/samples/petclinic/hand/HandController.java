package org.springframework.samples.petclinic.hand;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
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

@RestController
@RequestMapping("/api/v1/hands")
@Tag(name = "Hands", description = "The Hand management API")
@SecurityRequirement(name = "bearerAuth")
public class HandController {

    private final HandService handService;
    private final CardService cardService;

    @Autowired
    public HandController(HandService handService, CardService cardService) {
        this.handService = handService;
        this.cardService = cardService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Hand>> getAllHands() {
    return new ResponseEntity<>(handService.getAllHands(), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hand> getHand2ById(@PathVariable("id") Integer id) {
        Optional<Hand> hand = handService.getHandById(id);
        if (!hand.isPresent()) {
            throw new ResourceNotFoundException("Hand", "id", id);
        }
        return new ResponseEntity<>(hand.get(), HttpStatus.OK);
    }

    @GetMapping("/player/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hand> getHandByPlayerId(@PathVariable("id") Integer id) {
        Hand hand = handService.getHandByPlayerId(id);
        if (hand == null) {
            throw new ResourceNotFoundException("Hand", "id", id);
        }
        return new ResponseEntity<>(hand, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Hand> createHand(Integer id,Integer pId) throws DataAccessException{
        Hand createdHand = handService.createHand(id, pId);
        return new ResponseEntity<>(createdHand,HttpStatus.CREATED);
    }

    @PutMapping("/round/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hand> updateHand(@PathVariable("playerId") Integer playerId, @RequestParam("cardId") Integer cardId) {
        Hand hand = handService.getHandByPlayerId(playerId);
        List<Card> cards = hand.getCards();
        Card newHandCard = cardService.getCardById(cardId);
        cards.add(0,newHandCard);
        hand.setCards(cards);
        hand.setNumCartas(cards.size());
        handService.saveHand(hand);
        return new ResponseEntity<>(hand, HttpStatus.OK);
    }


    @GetMapping("/round/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HandDTO>> getRoundHands(@PathVariable("id") Integer id) {
        List<Hand> hands = handService.getHandByRoundId(id);
        if (hands == null) {
            throw new ResourceNotFoundException("Hand", "id", id);
        }
        List<HandDTO> handDTOs = hands.stream().map(hand -> new HandDTO(hand,hand.getCards())).collect(Collectors.toList());
        return new ResponseEntity<>(handDTOs, HttpStatus.OK);
    }

}
