package org.springframework.samples.petclinic.game;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.game.exceptions.ActiveGameException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.player.State;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;
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

@WebMvcTest(value = GameController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class GameControllerTests {

    private static final Integer TEST_PLAYER_ID_LUCAS = 51;
    private static final Integer TEST_USER_ID_LUCAS = 251;
    private static final Integer TEST_GAME_ID_1 = 33;
    private static final Integer TEST_GAME_ID_2 = 65;
    private static final Integer TEST_GAME_ID_3 = 23;
    private static final Integer TEST_GAME_ID_4 = 54;
    private static final Integer TEST_GAME_ID_5 = 12;

    private static final String BASE_URL = "/api/v1/games";
    private Game game1;
    private Game game2;
    private Game game3;
    private Game game4;
    private Player lucas;
    private User userLucas;
    private Authorities auth;
    private GameInfo gameInfo;


    @MockBean
    GameService gameService;

    @MockBean
    UserService userService;

    @MockBean
    PlayerService playerService;

    @MockBean
    RoundService roundService;

    @MockBean
    GameInfoService gameInfoService;

    @Autowired
	private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
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

        game1 = new Game();
        game1.setId(TEST_GAME_ID_1);
        game1.setStatus(GameStatus.WAITING);
        game1.setGameMode(GameMode.QUICK_PLAY);
        game1.setCreator(lucas);
        game1.setWinner(TEST_PLAYER_ID_LUCAS);
        game1.setPlayers(new java.util.ArrayList<Player>());
        game1.setRounds(new java.util.ArrayList<Round>());
        game1.setNumPlayers(2);
        game1.setGameTime(2);

        gameInfo = new GameInfo();
        gameInfo.setId(TEST_GAME_ID_5);
        gameInfo.setCreator(lucas);
        gameInfo.setGameMode(GameMode.QUICK_PLAY);
        gameInfo.setGameTime(2);
        gameInfo.setNumPlayers(2);
        gameInfo.setStatus(GameStatus.WAITING);
        gameInfo.setWinner(TEST_PLAYER_ID_LUCAS);
        gameInfo.setGame(game1);
        

        game2 = new Game();
        game2.setId(TEST_GAME_ID_2);
        game2.setStatus(GameStatus.WAITING);
        game2.setGameMode(GameMode.COMPETITIVE);
        game2.setCreator(lucas);
        game2.setWinner(TEST_PLAYER_ID_LUCAS);
        game2.setPlayers(new java.util.ArrayList<Player>());
        game2.setRounds(new java.util.ArrayList<Round>());
        game2.setNumPlayers(2);
        game2.setGameTime(2);

        game3 = new Game();
        game3.setId(TEST_GAME_ID_3);
        game3.setStatus(GameStatus.IN_PROGRESS);
        game3.setGameMode(GameMode.COMPETITIVE);
        game3.setCreator(lucas);
        game3.setWinner(TEST_PLAYER_ID_LUCAS);
        game3.setPlayers(new java.util.ArrayList<Player>());
        game3.setRounds(new java.util.ArrayList<Round>());
        game3.setNumPlayers(2);
        game3.setGameTime(2);

        game4 = new Game();
        game4.setId(TEST_GAME_ID_4);
        game4.setStatus(GameStatus.FINALIZED);
        game4.setGameMode(GameMode.COMPETITIVE);
        game4.setCreator(lucas);
        game4.setWinner(TEST_PLAYER_ID_LUCAS);
        game4.setPlayers(new java.util.ArrayList<Player>());
        game4.setRounds(new java.util.ArrayList<Round>());
        game4.setNumPlayers(2);
        game4.setGameTime(2);

    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testGetAllGames() throws Exception {

        when(this.gameService.getAllGames()).thenReturn(java.util.List.of(game1, game2));

        mockMvc.perform(get("/api/v1/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(2));

    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testgetRandomQuickGame() throws Exception {

        when(this.gameService.getRandomGame("QUICK_PLAY")).thenReturn(Optional.of(game1));

        mockMvc.perform(get(BASE_URL + "/quick/joinRandom"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(33))
                .andExpect(jsonPath("$.gameMode").value(GameMode.QUICK_PLAY.toString()));
    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testGetInProgressGames() throws Exception {
        when(this.gameService.getInProgressGames()).thenReturn(java.util.List.of(game3));

        mockMvc.perform(get(BASE_URL + "/inProgress"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].status").value(GameStatus.IN_PROGRESS.toString()));
    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testGetWaitingGames() throws Exception {

        when(this.gameService.getWaitingGames()).thenReturn(java.util.List.of(game1, game2));

        mockMvc.perform(get(BASE_URL + "/waiting"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].status").value(GameStatus.WAITING.toString()))
                .andExpect(jsonPath("$[1].status").value(GameStatus.WAITING.toString()));
    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testGetFinalizedGames() throws Exception {

        when(this.gameService.getFinalizedGames()).thenReturn(java.util.List.of(game4));
        mockMvc.perform(get(BASE_URL + "/finalized"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].status").value(GameStatus.FINALIZED.toString()));

    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testgetRandomCopetitiveGame() throws Exception {
        when(this.gameService.getRandomGame("COMPETITIVE")).thenReturn(Optional.of(game2));

        mockMvc.perform(get(BASE_URL + "/competitive/joinRandom"))
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "lucas", authorities = { "PLAYER" })
    public void testCantCreatequickGame() throws Exception {

        reset(gameService);
        reset(userService);
        reset(playerService);
        reset(gameInfoService);
        reset(roundService);

        when(userService.findCurrentUser()).thenReturn(userLucas);
        when(playerService.findPlayerByUser(userLucas)).thenReturn(lucas);
        when(gameService.hasActiveGame(any(Player.class))).thenReturn(true);
        when(gameService.saveGame(any(Game.class),any(Player.class))).thenThrow(new ActiveGameException("Player already has an active game"));
        when(gameInfoService.saveGameInfo(any(GameInfo.class))).thenReturn(gameInfo);
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameMode(GameMode.QUICK_PLAY);

        mockMvc.perform(post(BASE_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "player3", authorities = { "PLAYER" })
    public void testCreatequickGame() throws Exception {

        reset(gameService);
        reset(userService);
        reset(playerService);
        reset(gameInfoService);
        reset(roundService);

        when(userService.findCurrentUser()).thenReturn(userLucas);
        when(playerService.findPlayerByUser(userLucas)).thenReturn(lucas);
        when(gameService.hasActiveGame(any(Player.class))).thenReturn(false);
        when(gameService.saveGame(any(Game.class),any(Player.class))).thenReturn(game1);
        when(gameInfoService.saveGameInfo(any(GameInfo.class))).thenReturn(gameInfo);
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameMode(GameMode.QUICK_PLAY);

        mockMvc.perform(post(BASE_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameRequest)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameMode").value("QUICK_PLAY"));
    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testCreateCompetitiveGame() throws Exception {

        
        reset(gameService);
        reset(userService);
        reset(playerService);
        reset(gameInfoService);
        reset(roundService);
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameMode(GameMode.COMPETITIVE);
        game1.setGameMode(GameMode.COMPETITIVE);
        when(userService.findCurrentUser()).thenReturn(userLucas);
        when(playerService.findPlayerByUser(userLucas)).thenReturn(lucas);
        when(gameService.hasActiveGame(any(Player.class))).thenReturn(false);
        when(gameService.saveGame(any(Game.class),any(Player.class))).thenReturn(game1);
        when(gameInfoService.saveGameInfo(any(GameInfo.class))).thenReturn(gameInfo);
        

        mockMvc.perform(post(BASE_URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameRequest)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameMode").value("COMPETITIVE"));
    }

     @Test
    @WithMockUser(username = "player7", authorities = { "PLAYER" })
    public void testDeletePlayerFromGame() throws Exception {

        doNothing().when(gameService).deletePlayerFromGame(game1.getId(), userLucas.getId());

        mockMvc.perform(delete(BASE_URL +"/"+ game1.getId() + "/players/" + userLucas.getId())
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)) 
            .andExpect(jsonPath("$.message").value("Player deleted from the Game!"));}




    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
