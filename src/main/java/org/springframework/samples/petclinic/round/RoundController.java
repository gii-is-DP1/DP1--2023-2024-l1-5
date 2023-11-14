package org.springframework.samples.petclinic.round;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.clinicowner.ClinicOwner;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameDTO;
import org.springframework.samples.petclinic.game.GameMode;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.hand.HandService;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.samples.petclinic.round.exceptions.WaitingGameException;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rounds")
@Tag(name = "Rounds", description = "The Round management API")
@SecurityRequirement(name = "bearerAuth")
public class RoundController {
    private final GameService gameService;
    private final UserService userService;
    private final PlayerService playerService;
    private final RoundService roundService;
    private final DeckService deckService;
    private final CardService cardService;
    private final HandService handService;
    private static final String PLAYER_AUTH = "PLAYER";

    @Autowired
    public RoundController(RoundService roundService, UserService userService, GameService gameService,
            PlayerService playerService, DeckService deckService, CardService cardService, HandService handService) {
        this.roundService = roundService;
        this.userService = userService;
        this.gameService = gameService;
        this.playerService = playerService;
        this.deckService = deckService;
        this.cardService = cardService;
        this.handService = handService;
    }

    @GetMapping
    public ResponseEntity<List<Round>> getAllRounds() {
        return new ResponseEntity<>(roundService.getAllRounds(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Round> getRoundById(Integer id) {
        Optional<Round> r = roundService.getRoundById(id);
        if (!r.isPresent())
            throw new ResourceNotFoundException("Round", "id", id);
        return new ResponseEntity<>(r.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Round> createRound(@Valid @RequestBody RoundRequest roundRequest) throws DataAccessException {
        // POR AHORA NO SE TIENE EN CUENTA SI ES COMPETITIVO O NO

        User user = userService.findCurrentUser();
        Round newRound = new Round();
        Game newGame = new Game(); 
        Round savedRound;
        BeanUtils.copyProperties(roundRequest, newRound, "id");
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)) {
            Player player = playerService.findPlayerByUser(user);
            boolean hasWaitingGame = gameService.getWaitingGame(player) != null;
            if (!hasWaitingGame) {
                throw new WaitingGameException("No hay ninguna partida en espera");
            } else {
                newRound.setRoundMode(roundRequest.getRoundMode());
                Optional<Game> waitingGame = gameService.getWaitingGame(player);
                if (waitingGame.isPresent()) {
                    newRound.setGame(waitingGame.get());
                    savedRound = roundService.saveRound(newRound, waitingGame.get());
                } else {
                    throw new WaitingGameException("No hay ninguna partida en espera");
                }
            }
        } else {
            savedRound = roundService.saveRound(newRound, newGame);
        }
        return new ResponseEntity<>(savedRound, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Round> updateRound(@PathVariable("id") Integer id, @Valid @RequestBody RoundRequestPUT roundRequest) {
        Optional<Round> r = roundService.getRoundById(id);
        if (!r.isPresent())
            throw new ResourceNotFoundException("Round", "id", id);
        Round round = r.get();
        BeanUtils.copyProperties(roundRequest, round, "id");
        Round savedRound = this.roundService.saveRound(round, round.getGame());
        
        return new ResponseEntity<>(savedRound, HttpStatus.OK);
    }

    // @PutMapping("/shuffle")
    // public void create(@Valid @RequestParam(required = true) int round_id) throws
    // Exception {

    // User user = userService.findCurrentUser();
    // if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)) {
    // Optional<Round> optionalRound = roundService.getRoundById(round_id);
    // if (optionalRound.isPresent()) {
    // Round round = optionalRound.get();
    // Integer roundId = round.getId();
    // RoundMode roundMode = round.getRoundMode();
    // Game game = round.getGame();
    // GameMode gameMode = game.getGameMode();
    // GameDTO gameDTO = new GameDTO(game);
    // List<Integer> ls = gameDTO.getPlayerList();
    // List<Card> cards = cardService.getAllCards();
    // Map<Integer, List<Card>> hands = roundService.distribute(cards, gameMode,
    // roundMode, ls);
    // for (Integer key : hands.keySet()) {
    // if (key == 0) {
    // Deck deck = deckService.getDeckByRoundId(roundId);
    // if (deck != null) {
    // List<Card> deckCards = hands.get(key);
    // if (deckCards != null) {
    // this.deckService.updateDeck(deck, round_id, deckCards, round);
    // }

    // } else {
    // throw new Exception("no existe deck");
    // }

    // } else {
    // Integer playerId = key;
    // Hand hand = handService.getHandByPlayerId(playerId);
    // if (hand != null) {
    // List<Card> handCards = hands.get(key);
    // hand.setCards(handCards);
    // if (hand.getPlayer().getId() != playerId) {
    // hand.setPlayer(playerService.getPlayerById(playerId).get());
    // }
    // if (hand.getRound().getId() != roundId) {
    // hand.setRound(round);
    // }
    // this.handService.updateHand(hand, hand.getId());
    // } else {
    // throw new Exception("no existe hand");
    // }

    // }
    // }

    // }
    // }
    // }

}
