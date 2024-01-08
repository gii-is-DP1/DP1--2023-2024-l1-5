package org.springframework.samples.petclinic.round;



import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.deck.DeckService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.hand.HandService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    private WebApplicationContext context;

    @Autowired
    MockMvc mvc;

    private Player lucas;
    private Player guille;
    private User userLucas;
    private User userGuille;
    private Game testGame;
    private Authorities auth;
    private Round testRound;
    private static final String BASE_URL = "/api/v1/rounds";
    private static final Integer TEST_PLAYER_ID_LUCAS = 51;
    private static final Integer TEST_PLAYER_ID_GUILLE = 52;
    private static final Integer TEST_USER_ID_LUCAS = 251;
    private static final Integer TEST_USER_ID_GUILLE = 252;
    private static final Integer ROUND_ID = 1;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        auth = new Authorities();
        auth.setId(1);
        auth.setAuthority("PLAYER");
        lucas = new Player();
        lucas.setId(TEST_PLAYER_ID_LUCAS);
        lucas.setFirstName("Lucas");
        lucas.setLastName("Antonanzas");
        lucas.setImage("image");
        userLucas = new User();
        userLucas.setId(TEST_USER_ID_LUCAS);
        userLucas.setUsername("lucas");
        userLucas.setPassword("lucas");
        lucas.setUser(userLucas);

        guille = new Player();
        guille.setId(TEST_PLAYER_ID_GUILLE);
        guille.setFirstName("Guille");
        guille.setLastName("Gomez");
        guille.setImage("image");
        userGuille = new User();
        userGuille.setId(TEST_USER_ID_GUILLE);
        userGuille.setUsername("guille");
        userGuille.setPassword("guille");
        guille.setUser(userGuille);

        testGame = new Game();
        testGame.setId(1);

        testRound = new Round();
        testRound.setId(1);
        testRound.setGame(testGame);
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

}


