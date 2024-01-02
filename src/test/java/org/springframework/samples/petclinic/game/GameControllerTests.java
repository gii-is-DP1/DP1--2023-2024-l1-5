package org.springframework.samples.petclinic.game;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTests {

    private static final String BASE_URL = "/api/v1/games";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Mock 
    GameService gameService;
    
    @Mock
    private UserService userService;

    @Mock
    private PlayerService playerService;
    @InjectMocks
    private GameController gameController;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void testGetAllGames() throws Exception {

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(7));
        
    }

    @Test
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void testgetRandomQuickGame() throws Exception{
         mockMvc.perform(get(BASE_URL+"/quick/joinRandom"))
         .andExpect(status().isOk());
    } 
    @Test
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void testGetInProgressGames() throws Exception{
         mockMvc.perform(get(BASE_URL+"/inProgress"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.size()").value(3));
    }
    @Test
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void testGetWaitingGames() throws Exception{
         mockMvc.perform(get(BASE_URL+"/waiting"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void testGetFinalizedGames() throws Exception{
         mockMvc.perform(get(BASE_URL+"/finalized"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.size()").value(2));
    }


    @Test
    @WithMockUser(username = "player1", authorities = {"PLAYER"})
    public void testgetRandomCopetitiveGame() throws Exception{
         mockMvc.perform(get(BASE_URL+"/competitive/joinRandom")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testCantCreatequickGame() throws Exception {

        User user = new User();
        GameRequest gameRequest = new GameRequest();
        when(userService.findCurrentUser()).thenReturn(user);
        gameRequest.setGameMode(GameMode.QUICK_PLAY); 
        mockMvc.perform(post(BASE_URL)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(gameRequest)))
        .andExpect(status().isConflict());
    }
    @Test
    @WithMockUser(username = "player7", authorities = { "PLAYER" })
    public void testCreatequickGame() throws Exception {

        User user = new User();
        GameRequest gameRequest = new GameRequest();
        when(userService.findCurrentUser()).thenReturn(user);
        gameRequest.setGameMode(GameMode.QUICK_PLAY); 
        mockMvc.perform(post(BASE_URL)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(gameRequest)))
        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "player1", authorities = { "PLAYER" })
    public void testCantCreatecompetitiveGame() throws Exception {

        User user = new User();
        GameRequest gameRequest = new GameRequest();
        when(userService.findCurrentUser()).thenReturn(user);
        gameRequest.setGameMode(GameMode.COMPETITIVE); 
        mockMvc.perform(post(BASE_URL)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(gameRequest)))
        .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "player3", authorities = { "PLAYER" })
    public void testCreateCompetitiveGame() throws Exception {

        User user = new User();
        GameRequest gameRequest = new GameRequest();
        when(userService.findCurrentUser()).thenReturn(user);
        gameRequest.setGameMode(GameMode.COMPETITIVE); 
        mockMvc.perform(post(BASE_URL)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(gameRequest)))
        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "player7", authorities = { "PLAYER" })
    public void testDeletePlayerFromGame() throws Exception {

        Game game = new Game();
        game.setId(1);
        game.setStatus(GameStatus.WAITING); 

        List<Player> players = new ArrayList<>();

        Player player1 = new Player();
        User user1 = new User();
        user1.setId(1);
        player1.setUser(user1);

        players.add(player1);
        game.setPlayers(players);

        Integer gameId = game.getId();
        Integer userId = player1.getUser().getId();

        // Simula el comportamiento esperado del servicio
        doNothing().when(gameService).deletePlayerFromGame(gameId, userId);

        // Realiza la petición DELETE y verifica el resultado
        mockMvc.perform(delete(BASE_URL +"/"+ gameId + "/players/" + userId)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    // @Test
    // @WithMockUser(username = "player3", authorities = { "PLAYER" })
    // public void testJoinQuickPlay() throws Exception {
       
    //     mockMvc.perform(put(BASE_URL+"/quick/joinRandom")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content("1")) 
    //             .andExpect(status().isOk());
    //     verify(gameService, times(1)).getRandomGame("QUICK_PLAY");
    // }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}


