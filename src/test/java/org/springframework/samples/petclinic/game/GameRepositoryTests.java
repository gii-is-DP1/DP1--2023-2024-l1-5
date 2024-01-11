package org.springframework.samples.petclinic.game;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.player.PlayerRepository;

@DataJpaTest()
public class GameRepositoryTests {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Test
    public void testFindAll() {
        List<Game> games = gameRepository.findAll();
        assertNotNull(games);
        assertTrue(games.size() > 0);
    }
    
    //Case 1: Checking that player 1 has created games
    @Test
    public void testFindPlayerCreatedGames() {

        List<Game> playerCreatedGames1 = gameRepository.findPlayerCreatedGames(1);
        assertNotNull(playerCreatedGames1);
        assertTrue(playerCreatedGames1.size() > 0);
    }

    //Case 2: Check that player 3 has not created games
    @Test
    public void testGamesCreatedByPlayersIsEmpty(){
        List<Game> playerCreatedGames2 = gameRepository.findPlayerCreatedGames(7);
        assertNotNull(playerCreatedGames2);
        assertEquals(0, playerCreatedGames2.size());
    }
        
    //Case 3: Checking for correct handling when providing a null value
    @Test
    public void testFindGamesCreatedByNullPlayers(){
        List<Game> playerCreatedGames3 = gameRepository.findPlayerCreatedGames(null);
        assertTrue(playerCreatedGames3.isEmpty());
    }

    @Test
    public void testFindWaitingQuickGames() {

        // Case 1: Standby games with QUICK_PLAY mode and less than 8 players
        List<Game> waitingQuickGames1 = gameRepository.findWaitingQuickGames();
        assertNotNull(waitingQuickGames1);
        assertTrue(checkValuesOfGameList(waitingQuickGames1, GameMode.QUICK_PLAY));
        assertTrue(waitingQuickGames1.size() > 0);

    }

    @Test
    public void testFindWaitingCompetitiveGames() {
    
        // Case 1: Games on standby with COMPETITIVE mode and less than 8 players
        List<Game> waitingCompetitiveGames1 = gameRepository.findWaitingCompetitiveGames();
        assertNotNull(waitingCompetitiveGames1);
        assertTrue(checkValuesOfGameList(waitingCompetitiveGames1, GameMode.COMPETITIVE));
        assertTrue(waitingCompetitiveGames1.size() == 0);
    }

    @Test
    public void testFindInProgressGames() {

        List<Game> inProgressGames = gameRepository.findInProgressGames();
        assertNotNull(inProgressGames);
        assertTrue(inProgressGames.size() > 0);
    }

    @Test
    public void testFindFinalizedGames() {

        List<Game> finalizedGames = gameRepository.findFinalizedGames();
        assertNotNull(finalizedGames);
        assertTrue(finalizedGames.size() > 0);
    }

    @Test
    public void testFindWaitingGames() {

        List<Game> waitingGames = gameRepository.findWaitingGames();
        assertNotNull(waitingGames);
        assertTrue(waitingGames.size() > 0);
    }

    public Boolean checkValuesOfGameList(List<Game> games, GameMode GM){
        for(Game g : games){
            if(g.getGameMode() != GM && g.getStatus() != GameStatus.WAITING && g.getNumPlayers() >= 8)
                return false;
        }
        return true;
    }
    
}
