package org.springframework.samples.petclinic.round;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameMode;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.game.GameStatus;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.hand.HandService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.player.State;
import org.springframework.samples.petclinic.symbol.Name;
import org.springframework.samples.petclinic.symbol.Symbol;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(value = { RoundController.class }, 
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class RoundControllerTest {

    @MockBean
    RoundService roundService;

    @MockBean
    UserService userService;

    @MockBean
    GameService gameService;

    @MockBean
    PlayerService playerService;

    @MockBean
    DeckService deckService;

    @MockBean
    CardService cardService;

    @MockBean
    HandService handService;



    @Autowired
	private ObjectMapper objectMapper;


    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mvc;

    private Player lucas;
    private User userLucas;
    private Game testGame;
    private Authorities auth;
    private Round testRound;
    private Card card;
    private Card card2;
    private Card card3;
    private Symbol symbol;
    private Deck deck;
    private Hand hand;
    private static final String BASE_URL = "/api/v1/rounds";
    private static final Integer TEST_PLAYER_ID_LUCAS = 51;
    private static final Integer TEST_USER_ID_LUCAS = 251;
    private static final Integer ROUND_ID = 1;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        auth = new Authorities();
        auth.setId(1);
        auth.setAuthority("PLAYER");
        lucas = new Player();
        lucas.setId(TEST_PLAYER_ID_LUCAS);
        lucas.setFirstName("Lucas");
        lucas.setLastName("Antonanzas");
        lucas.setImage("image");
        lucas.setState(State.ACTIVE);
        userLucas = new User();
        userLucas.setId(TEST_USER_ID_LUCAS);
        userLucas.setUsername("lucas");
        userLucas.setPassword("lucas");
        userLucas.setAuthority(auth);
        lucas.setUser(userLucas);

        testGame = new Game();
        testGame.setId(1);
        testGame.setGameMode(GameMode.COMPETITIVE);
        testGame.setNumPlayers(2);
        testGame.setCreator(lucas);
        testGame.setGameTime(0);
        testGame.setStatus(GameStatus.WAITING);
        testGame.setWinner(null);
        testGame.setRounds(null);

        symbol = new Symbol();
        symbol.setName(Name.APPLE);

        card = new Card();
        card.setId(1);
        card.setImage("image");
        card.setSymbols(java.util.List.of(symbol));

        card2 = new Card();
        card2.setId(2);
        card2.setImage("image");
        card2.setSymbols(java.util.List.of(symbol));

        card3 = new Card();
        card3.setId(3);
        card3.setImage("image");
        card3.setSymbols(java.util.List.of(symbol));

        testRound = new Round();
        testRound.setId(1);
        testRound.setGame(testGame);
        testRound.setRoundMode(RoundMode.INFERNAL_TOWER);
        testRound.setWinner(null);

        deck = new Deck();
        deck.setId(1);
        deck.setNumberOfCards(16);
        deck.setRound(testRound);
        deck.setCards(java.util.List.of(card, card2));

        hand = new Hand();
        hand.setId(1);
        hand.setCards(java.util.List.of(card3));
        hand.setNumCartas(1);
        hand.setPlayer(lucas);
        hand.setRound(testRound);


    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetAllRounds() throws Exception {
        when(roundService.getAllRounds()).thenReturn(java.util.List.of(testRound));

        mvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetRoundById() throws Exception {
        when(this.roundService.getRoundById(ROUND_ID)).thenReturn(java.util.Optional.of(testRound));
        mvc.perform(get(BASE_URL+"/"+ROUND_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testCreateRound() throws Exception{
        RoundRequest roundRequest = new RoundRequest();
        roundRequest.setRoundMode(RoundMode.INFERNAL_TOWER);

        when(userService.findCurrentUser()).thenReturn(userLucas);
        when(playerService.findPlayerByUser(userLucas)).thenReturn(lucas);
        when(gameService.getWaitingGame(any(Player.class))).thenReturn(java.util.Optional.of(testGame));
        when(roundService.saveRound(any(Round.class), any(Game.class))).thenReturn(testRound);

        mvc.perform(post(BASE_URL).with(csrf())
        
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roundRequest)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1));

    }

}


