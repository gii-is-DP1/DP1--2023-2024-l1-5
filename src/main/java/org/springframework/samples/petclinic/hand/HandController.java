package org.springframework.samples.petclinic.hand;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hands")
@Tag(name = "Hands", description = "The Hand management API")
@SecurityRequirement(name = "bearerAuth")
public class HandController {

    private final HandService handService;
    private final UserService userService;
    private final CardService cardService;
    private final RoundService roundService;
    private final PlayerService playerService;
    private static final String PLAYER_AUTH = "PLAYER";

    @Autowired
    public HandController(HandService handService, UserService userService, CardService cardService,
            RoundService roundService, PlayerService playerService) {
        this.roundService = roundService;
        this.handService = handService;
        this.userService = userService;
        this.cardService = cardService;
        this.playerService = playerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Hand>> getAllHands() {
    return new ResponseEntity<>(handService.getAllHands(), HttpStatus.OK);
    }
    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<List<HandDTO>> getAllHands() {
    //     List<Hand> hands = handService.getAllHands(); // Obtener la lista de objetos Hand
    //     List<HandDTO> handDTOs = new ArrayList<>(); // Crear una lista de objetos HandDTO
    //     for (Hand hand : hands) { // Recorrer la lista de objetos Hand
    //         List<Card> cards = cardService.getCardsByHandId(hand.getId()); // Obtener la lista de objetos Card
    //         handDTOs.add(new HandDTO(hand, cards)); // Agregar un objeto HandDTO a la lista de objetos HandDTO
    //     }
    //     return new ResponseEntity<>(handDTOs, HttpStatus.OK);
    // }

    // @GetMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<HandDTO> getHandById(@PathVariable("id") Integer id) {
    //     Optional<Hand> hand = handService.getHandById(id);
    //     if (!hand.isPresent()) {
    //         throw new ResourceNotFoundException("Hand", "id", id);
    //     }
    //     List<Card> cards = cardService.getCardsByHandId(id);
    //     return new ResponseEntity<>(new HandDTO(hand.get(), cards), HttpStatus.OK);
    // }

    @GetMapping("/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hand> getHand2ById(@PathVariable("id") Integer id) {
        Optional<Hand> hand = handService.getHandById(id);
        if (!hand.isPresent()) {
            throw new ResourceNotFoundException("Hand", "id", id);
        }
        return new ResponseEntity<>(hand.get(), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Hand> createHand(Integer id,Integer pId) throws DataAccessException{
        Hand createdHand = handService.createHand(id, pId);
        return new ResponseEntity<>(createdHand,HttpStatus.CREATED);
    }




    // @PutMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<HandDTO> putHand(@PathVariable("id") int id,
    //         @RequestBody List<Integer> cardIds,
    //         @RequestParam("round") int roundId) {

    //     HandDTO handDTO = new HandDTO();
    //     handDTO.setId(id);
    //     handDTO.setNumCartas(cardIds.size());
    //     handDTO.setCards(cardIds);
    //     handDTO.setRound(roundId);
    //     // System.out.println("cards: " + cardIds);
    //     System.out.println("round: " + roundId);
    //     // Integer id = handDTO.getId();
    //     Hand oldHand = RestPreconditions.checkNotNull(handService.getHandById(id).get(), "hand",
    //             "id", id);
    //     User user = userService.findCurrentUser();
    //     Player player = playerService.findPlayerByUser(user);
    //     handDTO.setPlayer(player.getId());
    //     if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)) {
    //         Hand hand = handService.getHandById(id).get();
    //         hand.setNumCartas(handDTO.getNumCartas());
    //         List<Card> cards = new ArrayList<Card>();
    //         for (Integer i : handDTO.getCards()) {
    //             cards.add(cardService.getCardById(i));
    //         }
    //         hand.setCards(cards);
    //         Optional<Round> newRound = roundService.getRoundById(handDTO.getRound());
    //         if (newRound.isPresent()) {
    //             hand.setRound(newRound.get());
    //         } else {
    //             throw new ResourceNotFoundException("Round", "id", handDTO.getRound());
    //         }
    //         Hand result = this.handService.updateHand(hand, id);
    //         return new ResponseEntity<>(new HandDTO(result), HttpStatus.OK);
    //     } else {
    //         Hand result = this.handService.updateHand(oldHand, id);
    //         return new ResponseEntity<>(new HandDTO(result), HttpStatus.OK);
    //     }
    // }
}
