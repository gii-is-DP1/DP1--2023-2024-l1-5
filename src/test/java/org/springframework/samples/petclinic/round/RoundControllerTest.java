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
//    private Card card1,card2,card3,card5, card6, card7,card8,card9,card10,car11,card12,card13,card14,card15,card16;
    private List<Card> cards;
//    private Symbol dolphin, glasses, thunder, ghost, snowman, shot, exclamation, zebra, pencil, hammer, cactus, cat, turtle, apple, babyBottle, spider, yinYan, ladybug, igloo, interrogation, clover, scissors, key, cheese, eye, music, dog, heart, clown, bird, water;
    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

//Creacion de simbolos
        Symbol dolphin = new Symbol();
        dolphin.setName(Name.DOLPHIN);

        Symbol ghost = new Symbol();
        ghost.setName(Name.GHOST);

        Symbol glasses = new Symbol();
        glasses.setName(Name.GLASSES);

        Symbol thunder = new Symbol();
        thunder.setName(Name.THUNDER);

        Symbol snowman = new Symbol();
        snowman.setName(Name.SNOWMAN);

        Symbol shot = new Symbol();
        shot.setName(Name.SHOT);

        Symbol exclamation = new Symbol();
        exclamation.setName(Name.EXCLAMATION);

        Symbol zebra = new Symbol();
        zebra.setName(Name.ZEBRA);

        Symbol pencil = new Symbol();
        pencil.setName(Name.PENCIL);

        Symbol hammer = new Symbol();
        hammer.setName(Name.HAMMER);

        Symbol cactus = new Symbol();
        cactus.setName(Name.CACTUS);

        Symbol cat = new Symbol();
        cat.setName(Name.CAT);

        Symbol turtle = new Symbol();
        turtle.setName(Name.TURTLE);

        Symbol apple = new Symbol();
        apple.setName(Name.APPLE);

        Symbol babyBottle = new Symbol();
        babyBottle.setName(Name.BABY_BOTTLE);

        Symbol spider = new Symbol();
        spider.setName(Name.SPIDER);

        Symbol yinYan = new Symbol();
        yinYan.setName(Name.YIN_YAN);

        Symbol ladybug = new Symbol();
        ladybug.setName(Name.LADYBUG);

        Symbol igloo = new Symbol();
        igloo.setName(Name.IGLOO);

        Symbol interrogation = new Symbol();
        interrogation.setName(Name.INTERROGATION);

        Symbol clover = new Symbol();
        clover.setName(Name.CLOVER);

        Symbol scissors = new Symbol();
        scissors.setName(Name.SCISSORS);

        Symbol key = new Symbol();
        key.setName(Name.KEY);

        Symbol cheese = new Symbol();
        cheese.setName(Name.CHEESE);

        Symbol eye = new Symbol();
        eye.setName(Name.EYE);

        Symbol music = new Symbol();
        music.setName(Name.MUSIC);

        Symbol dog = new Symbol();
        dog.setName(Name.DOG);

        Symbol heart = new Symbol();
        heart.setName(Name.HEART);

        Symbol clown = new Symbol();
        clown.setName(Name.CLOWN);

        Symbol bird = new Symbol();
        bird.setName(Name.BIRD);

        Symbol water = new Symbol();
        water.setName(Name.WATER);

        //Creacion de cartas
        cards = new ArrayList<>();

        // Carta 1
        Card card1 = new Card();
        cards.add(card1);
        cards.get(0).setSymbols(Arrays.asList(dolphin, glasses, thunder, ghost, snowman, shot));

        // Carta 2
        Card card2 = new Card();
        cards.add(card2);;
        cards.get(1).setSymbols(Arrays.asList(exclamation, ghost, hammer, zebra, cactus, pencil));

        // Carta 3
        Card card3 = new Card();
        cards.add(card3);
        cards.get(2).setSymbols(Arrays.asList(apple, glasses, babyBottle, cat, pencil, turtle));

        // Carta 4
        Card card4 = new Card();
        cards.add(card4);
        cards.get(3).setSymbols(Arrays.asList(babyBottle, ladybug, hammer, snowman, spider, yinYan));

        // Carta 5
        Card card5 = new Card();
        cards.add(card5);
        cards.get(4).setSymbols(Arrays.asList(glasses, clover, interrogation, exclamation, igloo, spider));

        // Carta 6
        Card card6 = new Card();
        cards.add(card6);
        cards.get(5).setSymbols(Arrays.asList(scissors, zebra, apple, ladybug, shot, igloo));

        // Carta 7
        Card card7 = new Card();
        cards.add(card7);
        cards.get(6).setSymbols(Arrays.asList(key, interrogation, turtle, hammer, cheese, shot));

        // Carta 8
        Card card8 = new Card();
        cards.add(card8);
        cards.get(7).setSymbols(Arrays.asList(cactus, ladybug, eye, music, glasses, cheese));

        // Carta 9
        Card card9 = new Card();
        cards.add(card9);
        cards.get(8).setSymbols(Arrays.asList(turtle, clover, zebra, snowman, music, dog));

        // Carta 10
        Card card10 = new Card();
        cards.add(card10);
        cards.get(9).setSymbols(Arrays.asList(snowman, cactus, apple, interrogation, heart, clown));

        // Carta 11
        Card card11 = new Card();
        cards.add(card11);
        cards.get(10).setSymbols(Arrays.asList(exclamation, turtle, bird, thunder, heart, ladybug));

        // Carta 12
        Card card12 = new Card();
        cards.add(card12);
        cards.get(11).setSymbols(Arrays.asList(water, clover, apple, thunder, hammer, eye));

        // Carta 13
        Card card13 = new Card();
        cards.add(card13);
        cards.get(12).setSymbols(Arrays.asList(interrogation, eye, bird, zebra, babyBottle, dolphin));

        // Carta 14
        Card card14 = new Card();
        cards.add(card14);
        cards.get(13).setSymbols(Arrays.asList(cactus, bird, cat, shot, yinYan, clover));

        // Carta 15
        Card card15 = new Card();
        cards.add(card15);
        cards.get(14).setSymbols(Arrays.asList(key, scissors, eye, snowman, cat, exclamation));

        // Carta 16
        Card card16 = new Card();
        cards.add(card16);
        cards.get(15).setSymbols(Arrays.asList(bird, scissors, glasses, dog, clown, hammer));

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

    @Test
    @WithMockUser(username = "lucas", authorities = {"PLAYER"})
    public void testShuffleCreatesHandsForEachPlayer() throws Exception {
        // Simular respuestas de otros servicios
        List<Card> mockCards = cards;
        when(cs.get16LastCards()).thenReturn(mockCards);
        for (Player player : Arrays.asList(lucas, guille)) {
            when(ps.getPlayerById(player.getId())).thenReturn(Optional.of(player));
        }
        when(rs.getRoundById(1)).thenReturn(Optional.of(round));
        when(us.findCurrentUser()).thenReturn(userLucas);

        // Realizar acción
        mvc.perform(put("/api/v1/rounds/shuffle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(1))
                .with(csrf()))
                .andExpect(status().isOk());

        // Verificar que se llame a saveHand para cada jugador
        verify(hs, times(2)).saveHand(any(Hand.class));
    }
}

