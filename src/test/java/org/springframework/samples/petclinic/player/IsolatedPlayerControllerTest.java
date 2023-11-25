package org.springframework.samples.petclinic.player;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IsolatedPlayerControllerTest {
    
    private static final String BASE_URL = "/api/v1/players";
    private static final Integer TEST_PLAYER_ID= 1;

    @Autowired
    @MockBean
    PlayerService playerService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
	private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private Player javier;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        javier = new Player();
        javier.setId(TEST_PLAYER_ID);
        javier.setFirstName("Javier");
        javier.setLastName("Milei");
        javier.setImage("image");
        javier.setState(State.ACTIVE);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void testUpdatePlayer() throws Exception {

        javier.setFirstName("UPDATED");
        javier.setLastName("CHANGED");

        when(this.playerService.getPlayerById(TEST_PLAYER_ID).get()).thenReturn(javier);
        when(this.playerService.updatePlayer(any(Player.class), any(Integer.class))).thenReturn(javier);

        mockMvc.perform(put(BASE_URL+"/{id}", TEST_PLAYER_ID).with(csrf()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(javier))).andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("UPDATED")).andExpect(jsonPath("$.lastName").value("CHANGED"));
    }
  
}
