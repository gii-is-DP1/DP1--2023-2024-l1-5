package org.springframework.samples.petclinic.round;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameDTO;
import org.springframework.samples.petclinic.game.GameMode;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.hand.HandService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.round.exceptions.WaitingGameException;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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
    public ResponseEntity<Round> getRoundById(@PathVariable("id") Integer id) {
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

    // @PutMapping("/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public ResponseEntity<Round> updateRound(@PathVariable("id") Integer id, @Valid @RequestBody RoundRequestPUT roundRequest) {
    //     Optional<Round> r = roundService.getRoundById(id);
    //     if (!r.isPresent())
    //         throw new ResourceNotFoundException("Round", "id", id);
    //     Round round = r.get();
    //     BeanUtils.copyProperties(roundRequest, round, "id");
    //     Round savedRound = this.roundService.saveRound(round, round.getGame());
        
    //     return new ResponseEntity<>(savedRound, HttpStatus.OK);
    // }

    @PutMapping("/shuffle")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody @Valid int roundId) throws Exception {

        User user = userService.findCurrentUser();
        if (user.hasAnyAuthority(PLAYER_AUTH).equals(true)) {
            Optional<Round> optionalRound = roundService.getRoundById(roundId);
            if (optionalRound.isPresent()) {
                Round round = optionalRound.get();
                RoundMode roundMode = round.getRoundMode();
                Game game = round.getGame();
                GameMode gameMode = game.getGameMode();
                GameDTO gameDTO = new GameDTO(game);
                List<Player> ls = gameDTO.getPlayerList();
                List<Integer> lsId = new ArrayList<>();
                for (Player player : ls) {
                    lsId.add(player.getId());
                }
                List<Card> cards = cardService.get16LastCards();
                List<Card> cardsPlus16 = new ArrayList<>();
                for (Card c : cards) {
                    Card toAdd = cardService.createNewCard(c.getId());
                    cardsPlus16.add(toAdd);
                }
                Map<Integer, List<Card>> hands = roundService.distribute(cardsPlus16, gameMode, roundMode, lsId);
                for (Integer key : hands.keySet()) {
                    if (key == 0) {
                        List<Card> deckCards = hands.get(key);
                        Deck deck0 = new Deck();
                        deck0.setNumberOfCards(deckCards.size());
                        deck0.setRound(round);
                        deck0.setCards(deckCards);
                        deckService.saveDeck(deck0);
                    } else {
                        Integer pId = key;
                        Player player = playerService.getPlayerById(pId).get();
                        Hand createHand1 = new Hand();
                        List<Card> handCards = hands.get(key);
                        createHand1.setCards(handCards);
                        createHand1.setNumCartas(handCards.size());
                        createHand1.setPlayer(player);
                        createHand1.setRound(round);
                        handService.saveHand(createHand1);

                    }
                }
            }

        }
    }
}

