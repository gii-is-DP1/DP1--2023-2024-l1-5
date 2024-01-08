package org.springframework.samples.petclinic.game;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.player.State;
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





@WebMvcTest(value = {GameInfoController.class},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class GameInfoControllerTest {

    @MockBean
    GameInfoService gameInfoService;
    @MockBean
    UserService userService;
    @MockBean
    PlayerService ps;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;
    
    private Player lucas;
    private GameInfo testGameInfo;
    private Game testGame;
    private Authorities auth;
    private User userLucas;
    private static final String BASE_URL= "/api/v1/gameInfo";
    private static final Integer TEST_PLAYER_ID_LUCAS= 51;
    private static final Integer TEST_USER_ID_LUCAS= 251;
    private static final Integer TEST_GAMEINFO_ID = 1;
    private static final Integer GAME_ID = 1;



    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        
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

        testGameInfo = new GameInfo();
        testGameInfo.setId(1);
        testGameInfo.setNumPlayers(1);

        testGame = new Game();
        testGame.setId(GAME_ID);
        testGame.setNumPlayers(1);

    }



    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetAllGames() throws Exception {
        when(gameInfoService.findAllGameInfo()).thenReturn(java.util.List.of(testGameInfo));

        mvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(testGameInfo.getId()))
                .andExpect(jsonPath("$[0].numPlayers").value(testGameInfo.getNumPlayers()));
    }



    
    // public List<GameInfoDTO> createDTO(List<GameInfo> games){
    //             List<GameInfoDTO> gameInfoDTOs = games.stream()
    //             .map(game -> new GameInfoDTO(game))
    //             .collect(Collectors.toList());
    //             return gameInfoDTOs;
    // }
    

    
    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetGameInfoById()throws Exception{
        when(gameInfoService.findGameInfoByGameId(GAME_ID)).thenReturn(testGameInfo);
        mvc.perform(get(BASE_URL+"/"+GAME_ID)).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testUpdateGameInfo() throws Exception {
        when(userService.findCurrentUser()).thenReturn(userLucas);
        when(ps.findPlayerByUser(userLucas)).thenReturn(lucas);
    
        when(gameInfoService.findGameInfoByGameId(GAME_ID)).thenReturn(testGameInfo);
        when(gameInfoService.saveGameInfo(any(GameInfo.class))).thenReturn(testGameInfo);
    
        mvc.perform(put(BASE_URL + "/ready/" + GAME_ID).with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numPlayers").value(0));
    }
    

    

    
}
