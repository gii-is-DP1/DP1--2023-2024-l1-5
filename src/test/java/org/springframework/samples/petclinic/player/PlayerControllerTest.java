package org.springframework.samples.petclinic.player;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;

@WebMvcTest(value = PlayerController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class PlayerControllerTest {
    
    private static final String BASE_URL = "/api/v1/players";
    private static final Integer TEST_PLAYER_ID_LUCAS= 51;
    private static final Integer TEST_PLAYER_ID_GUILLE= 52;
    private static final Integer TEST_USER_ID_LUCAS= 251;
    private static final Integer TEST_USER_ID_GUILLE= 252;
    private Player lucas;
    private Player guille;
    private User userLucas;
    private User userGuille;

    @MockBean
    PlayerService playerService;

    @MockBean
    UserService userService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

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
        lucas.setUser(userLucas);

        guille = new Player();
        guille.setId(TEST_PLAYER_ID_GUILLE);
        guille.setFirstName("Guille");
        guille.setLastName("Gomez");
        guille.setImage("image");
        guille.setState(State.ACTIVE);
        userGuille = new User();
        userGuille.setId(TEST_USER_ID_GUILLE);
        userGuille.setUsername("guille");
        userGuille.setPassword("guille");
        guille.setUser(userGuille);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void testGetAllPlayers() throws Exception {

        when(this.playerService.getAllPlayers()).thenReturn(java.util.List.of(lucas, guille));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void testGetPlayerById() throws Exception {

        when(this.playerService.getPlayerById(TEST_PLAYER_ID_LUCAS)).thenReturn(java.util.Optional.of(lucas));

        mockMvc.perform(get(BASE_URL + "/" + TEST_PLAYER_ID_LUCAS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(TEST_PLAYER_ID_LUCAS))
                .andExpect(jsonPath("$.firstName").value("Lucas"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void testGetPlayerByUserId() throws Exception {

        when(this.playerService.getPlayerByUserId(TEST_USER_ID_LUCAS)).thenReturn(lucas);

        mockMvc.perform(get(BASE_URL + "/user/" + TEST_USER_ID_LUCAS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(TEST_PLAYER_ID_LUCAS))
                .andExpect(jsonPath("$.firstName").value("Lucas"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"admin"})
    public void testCreatePlayer() throws Exception {
        Player newPlayer = new Player();
        newPlayer.setFirstName("NewPlayer");
        newPlayer.setLastName("NewPlayer");
        newPlayer.setImage("image");
        newPlayer.setState(State.ACTIVE);

        when(playerService.savePlayer(any(Player.class))).thenReturn(newPlayer);

        mockMvc.perform(post("/api/v1/players").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newPlayer)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName").value("NewPlayer"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"admin"}) 
    public void testUpdatePlayer() throws Exception {
        guille.setFirstName("UPDATED");
        guille.setLastName("CHANGED");

        when(playerService.getPlayerById(TEST_PLAYER_ID_GUILLE)).thenReturn(java.util.Optional.of(guille));
        when(playerService.updatePlayer(any(Player.class), any(Integer.class))).thenReturn(guille);

        mockMvc.perform(put("/api/v1/players/" + TEST_PLAYER_ID_GUILLE).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(guille)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("UPDATED"))
            .andExpect(jsonPath("$.lastName").value("CHANGED"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"admin"}) 
	public void shouldDeletePlayer() throws Exception {
		when(this.playerService.getPlayerByUserId(TEST_USER_ID_LUCAS)).thenReturn(lucas);
		
	    doNothing().when(this.playerService).deletePlayer(TEST_USER_ID_LUCAS);
	    mockMvc.perform(delete(BASE_URL + "/{id}", TEST_USER_ID_LUCAS).with(csrf()))
	         .andExpect(status().isOk());
	}
}
