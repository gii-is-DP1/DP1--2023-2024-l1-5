package org.springframework.samples.petclinic.round;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.game.GameMode;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;

@ExtendWith(MockitoExtension.class)
public class RoundServiceTest {

    @Mock
    private  RoundService roundService;

    @Mock
    private  RoundRepository roundRepository;

    @Mock
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        roundService = new RoundService(roundRepository, gameService);
    }

    @Test
    public void testGetAll(){
        List<Round> rounds = new ArrayList<>();
        when(roundService.getAllRounds()).thenReturn(rounds);
        List<Round> result = roundService.getAllRounds();
        assertNotNull(result);
        assertEquals(rounds, result);
        verify(roundRepository, times(1)).findAll();
    }

    @Test
    public void testGetRoundById(){
        when(roundRepository.findById(1)).thenReturn(Optional.of(new Round()));
        Optional<Round> result = roundService.getRoundById(1);
        assertTrue(result.isPresent());
        verify(roundRepository, times(1)).findById(1);
    }


    @Test
    public void testDistributeQuickPlayPit() {

        GameMode gameMode = GameMode.QUICK_PLAY;
        RoundMode roundMode = RoundMode.PIT;
        List<Integer> players = createLsPlayers();
        List<Card> cards = createCards();

        Map<Integer, List<Card>> result = roundService.distribute(cards, gameMode, roundMode, players);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(0)); 
    }
        @Test
    public void testDistributeQuickPlayIT() {

        GameMode gameMode = GameMode.QUICK_PLAY;
        RoundMode roundMode = RoundMode.INFERNAL_TOWER;
        List<Integer> players = createLsPlayers();
        List<Card> cards = createCards();

        Map<Integer, List<Card>> result = roundService.distribute(cards, gameMode, roundMode, players);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(0)); 
    }
    @Test
    public void testDistributeCompetitivePit() {
        GameMode gameMode = GameMode.COMPETITIVE;
        RoundMode roundMode = RoundMode.PIT;
        List<Integer> players = createLsPlayers();
        List<Card> cards = createCards();

        Map<Integer, List<Card>> result = roundService.distribute(cards, gameMode, roundMode, players);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(0)); 

    }
    @Test
    public void testDistributeCompetitiveIT() {
        GameMode gameMode = GameMode.COMPETITIVE;
        RoundMode roundMode = RoundMode.INFERNAL_TOWER;
        List<Integer> players = createLsPlayers();
        List<Card> cards = createCards();

        Map<Integer, List<Card>> result = roundService.distribute(cards, gameMode, roundMode, players);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.containsKey(0)); 

    }

    public List<Card> createCards(){
        List<Card> ls = new ArrayList<>();
        Card card1 = new Card();
        Card card2 = new Card();
        card1.setId(1);card2.setId(2);
        ls.add(card2);ls.add(card1);
        return ls;
    }

    public List<Integer> createLsPlayers(){
        Player player1 = new Player();
        Player player2 = new Player();
        List<Integer> ls = new ArrayList<>();
        player1.setId(1);player2.setId(2);
        ls.add(player2.getId());ls.add(player1.getId());
        return ls;
    }
    

    
}
