package org.springframework.samples.petclinic.friendship;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = { FriendshipController.class}, 
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class FriendshipControllerTest {

    @MockBean
    PlayerService ps;

    @MockBean
    UserService us;

    @MockBean
    FriendshipService fs;
    @Autowired
	private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mvc;

    private Player lucas;
    private Player guille;
    private Player alvaro;
    private Authorities auth;
    private User userLucas;
    private User userGuille;
    private User userAlvaro;
    private Friendship testFriendship;
    private static final String BASE_URL="/api/v1/friendship";
    private static final Integer TEST_PLAYER_ID_LUCAS= 51;
    private static final Integer TEST_PLAYER_ID_GUILLE= 52;
    private static final Integer TEST_PLAYER_ID_ALVARO= 53;
    private static final Integer TEST_USER_ID_LUCAS= 251;
    private static final Integer TEST_USER_ID_GUILLE= 252;
    private static final Integer TEST_USER_ID_ALVARO= 253;

    @BeforeEach
    public void setup(){
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

        guille = new Player();
        guille.setId(TEST_PLAYER_ID_GUILLE);
        guille.setFirstName("Guille");
        guille.setLastName("Gomez");
        guille.setImage("image");
        guille.setState(State.ACTIVE);
        userGuille = new User();
        userGuille.setAuthority(auth);
        userGuille.setId(TEST_USER_ID_GUILLE);
        userGuille.setUsername("guille");
        userGuille.setPassword("guille");
        guille.setUser(userGuille);

        alvaro = new Player();
        alvaro.setId(TEST_PLAYER_ID_ALVARO);
        alvaro.setFirstName("Alvaro");
        alvaro.setLastName("Garcia");
        alvaro.setImage("image");
        alvaro.setState(State.ACTIVE);
        userAlvaro = new User();
        userAlvaro.setAuthority(auth);
        userAlvaro.setId(TEST_USER_ID_ALVARO);
        userAlvaro.setUsername("alvaro");
        userAlvaro.setPassword("alvaro");
        alvaro.setUser(userAlvaro);
        
        testFriendship = new Friendship();
        testFriendship.setId(1);
        testFriendship.setUser_source(lucas);
        testFriendship.setUser_dst(guille);
        testFriendship.setStatus(FriendshipStatus.ACCEPTED);


    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetAllRequests() throws Exception {

        when(this.fs.getAllFriendships()).thenReturn(java.util.List.of(testFriendship));

        mvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetAcceptedRequestByPlayerId() throws Exception {

        when(this.fs.getAcceptedFriendshipsByPlayerId(TEST_PLAYER_ID_LUCAS)).thenReturn(java.util.List.of(testFriendship));

        mvc.perform(get(BASE_URL+"/accepted/"+TEST_PLAYER_ID_LUCAS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetFriends() throws Exception {

        when(this.fs.getFriends(TEST_PLAYER_ID_LUCAS,"ALL")).thenReturn(java.util.List.of(guille));

        mvc.perform(get(BASE_URL+"/friends/"+TEST_PLAYER_ID_LUCAS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetFriendsPlaying() throws Exception {

        when(this.fs.getFriends(TEST_PLAYER_ID_LUCAS,"PLAYING")).thenReturn(java.util.List.of(guille));

        mvc.perform(get(BASE_URL+"/friends/playing/"+TEST_PLAYER_ID_LUCAS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testGetFriendsOnline() throws Exception {

        when(this.fs.getFriends(TEST_PLAYER_ID_LUCAS,"ACTIVE")).thenReturn(java.util.List.of(guille));

        mvc.perform(get(BASE_URL+"/friends/online/"+TEST_PLAYER_ID_LUCAS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(1));
    }


    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testCreateFriendship() throws Exception {
        // Crear los objetos necesarios para la amistad
        reset(fs);

        Friendship newFriendship = new Friendship();
        newFriendship.setId(2);
        newFriendship.setUser_source(lucas);
        newFriendship.setUser_dst(alvaro);
        newFriendship.setStatus(FriendshipStatus.WAITING);

        // Configurar el comportamiento esperado para el servicio FriendshipService
        when(us.findCurrentUser()).thenReturn(userLucas);
        when(ps.getPlayerByUsername("alvaro")).thenReturn(alvaro);
        when(ps.findPlayerByUser(userLucas)).thenReturn(lucas);
        when(fs.saveFriendship(any(Friendship.class), eq("POST"))).thenReturn(newFriendship);

        // Realizar la solicitud POST al endpoint para crear la amistad y realizar las verificaciones
        mvc.perform(post(BASE_URL + "/alvaro").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newFriendship)))
                .andExpect(status().isCreated()) // Verificar que el estado de la respuesta sea 201
                .andExpect(jsonPath("$.status").value(FriendshipStatus.WAITING.toString())); // Verificar que el estado de la amistad sea WAITING
    }
         

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testAcceptFriendship() throws Exception {
        // Simula la autenticación del usuario
        when(us.findCurrentUser()).thenReturn(userLucas);
        when(ps.findPlayerByUser(userLucas)).thenReturn(lucas);

        // Configura el estado inicial de la amistad y simula su obtención por ID
        testFriendship.setStatus(FriendshipStatus.WAITING);
        testFriendship.setUser_dst(lucas);
        when(fs.getFriendshipById(testFriendship.getId())).thenReturn(testFriendship);

        // Simula la aceptación de la solicitud de amistad
        testFriendship.setStatus(FriendshipStatus.ACCEPTED);
        when(fs.saveFriendship(any(Friendship.class), eq("PUT"))).thenReturn(testFriendship);

        
        mvc.perform(put(BASE_URL + "/acceptRequest/" + testFriendship.getId()).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testFriendship)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(FriendshipStatus.ACCEPTED.toString()));
    }

    @Test
    @WithMockUser(username = "lucas", authorities = "PLAYER")
    public void testRejectFriendship() throws Exception {
        // Simula la autenticación del usuario
        when(us.findCurrentUser()).thenReturn(userLucas);
        when(ps.findPlayerByUser(userLucas)).thenReturn(lucas);

        // Configura el estado inicial de la amistad y simula su obtención por ID
        testFriendship.setStatus(FriendshipStatus.WAITING);
        testFriendship.setUser_dst(lucas);
        when(fs.getFriendshipById(testFriendship.getId())).thenReturn(testFriendship);

        // Simula el rechazo de la solicitud de amistad
        testFriendship.setStatus(FriendshipStatus.DENIED);
        when(fs.saveFriendship(any(Friendship.class), eq("PUT"))).thenReturn(testFriendship);

        // Realiza la solicitud PUT para rechazar la amistad y verifica la respuesta
        mvc.perform(put(BASE_URL + "/rejectRequest/" + testFriendship.getId()).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testFriendship)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(FriendshipStatus.DENIED.toString()));
    }
}