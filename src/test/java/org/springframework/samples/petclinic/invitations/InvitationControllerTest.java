package org.springframework.samples.petclinic.invitations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.invitation.Invitation;
import org.springframework.samples.petclinic.invitation.InvitationController;
import org.springframework.samples.petclinic.invitation.InvitationService;
import org.springframework.samples.petclinic.invitation.InvitationState;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.State;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@WebMvcTest(value = {InvitationController.class},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class InvitationControllerTest {

    @MockBean
    private InvitationService invitationService;

    @MockBean
    private UserService userService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    private Player lucas;
    private User userLucas;
    private Invitation testInvitation;
    private static final String BASE_URL = "/api/v1/invitations";
    private static final Integer TEST_PLAYER_ID_LUCAS = 51;
    private static final Integer TEST_USER_ID_LUCAS = 251;
    private static final Integer TEST_GAME_ID = 1;
    private static final Integer TEST_INVITATION_ID = 1;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
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

        testInvitation = new Invitation();
        testInvitation.setId(TEST_INVITATION_ID);
        testInvitation.setSource_user("lucas");
        testInvitation.setDestination_user("guille");
        testInvitation.setInvitation_state(InvitationState.PENDING);
        testInvitation.setGame(new Game());
    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetAllInvitations() throws Exception {
        when(invitationService.getAllInvitations()).thenReturn(List.of(testInvitation));

        mvc.perform(get(BASE_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.size()").value(1));
    }

}

