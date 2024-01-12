package org.springframework.samples.petclinic.round;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.friendship.Friendship;
import org.springframework.samples.petclinic.friendship.FriendshipService;
import org.springframework.samples.petclinic.friendship.FriendshipStatus;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameMode;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.player.State;
import org.springframework.samples.petclinic.symbol.Name;
import org.springframework.samples.petclinic.symbol.Symbol;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.hand.Hand;
import org.springframework.samples.petclinic.hand.HandService;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.deck.DeckService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@WebMvcTest(value = { RoundController.class}, 
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class RoundControllerTest {

    @MockBean
    PlayerService ps;

    @MockBean
    UserService us;

    @MockBean
    RoundService rs;

    @MockBean
    GameService gs;

    @MockBean
    CardService cs;

    @MockBean
    DeckService ds;

    @MockBean
    HandService hs;

    @MockBean
    FriendshipService fs;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mvc;

    private Player lucas;
    private Player guille;
//    private Game game;
    private Round round;
    private Authorities auth;
    private User userLucas;
    private User userGuille;
    private Friendship testFriendship;
    private static final Integer TEST_PLAYER_ID_LUCAS= 51;
    private static final Integer TEST_PLAYER_ID_GUILLE= 52;
    private static final Integer TEST_USER_ID_LUCAS= 251;
    private static final Integer TEST_USER_ID_GUILLE= 252;

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

        testFriendship = new Friendship();
        testFriendship.setId(1);
        testFriendship.setUser_source(lucas);
        testFriendship.setUser_dst(guille);
        testFriendship.setStatus(FriendshipStatus.ACCEPTED);

        round = new Round();
        round.setId(1);
        round.setRoundMode(RoundMode.PIT);
    
        Game game = new Game();
        game.setId(1);
        game.setGameMode(GameMode.QUICK_PLAY);
        List<Player> ls = new ArrayList<>();
        ls.add(guille);
        ls.add(lucas);
        game.setPlayers(ls);
    
        round.setGame(game);

    }
    
    @Test
    @WithMockUser(username = "lucas", authorities = {"PLAYER"})
    public void testShuffle() throws Exception {
        when(rs.getRoundById(1)).thenReturn(Optional.of(round));
        when(us.findCurrentUser()).thenReturn(userLucas);

        mvc.perform(put("/api/v1/rounds/shuffle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(1))
                .with(csrf()))
                .andExpect(status().isOk());

        verify(us, times(1)).findCurrentUser();
        verify(rs, times(1)).getRoundById(1);
    }
}

