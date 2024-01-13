package org.springframework.samples.petclinic.game;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.player.PlayerRepository;

@DataJpaTest()
public class GameRepositoryTests {

    private static final int TEST_PLAYER_ID = 1;
    private static final int TEST_PLAYER_ID_12 = 12;

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

    @Test
    public void findGamesByPlayerIdTest() {
        List<Game> games = gameRepository.findGamesByPlayerId(TEST_PLAYER_ID);
        assertTrue(games.size() == 1);
    }

    @Test
    public void findGamesByPlayerIdTestNegative() {
        List<Game> games = gameRepository.findGamesByPlayerId(TEST_PLAYER_ID_12);
        assertTrue(games.size() == 0);
    }

    @Test
    public void findNumGamesByPlayerIdTest() {
        Integer numGames = gameRepository.findNumGamesByPlayerId(TEST_PLAYER_ID);
        assertTrue(numGames == 1);
    }

    @Test
    public void findNumGamesByPlayerIdTestNegative() {
        Integer numGames = gameRepository.findNumGamesByPlayerId(TEST_PLAYER_ID_12);
        assertTrue(numGames == 0);
    }

    @Test
    public void findNumGamesWinByPlayerIdTest() {
        Integer numGamesWin = gameRepository.findNumGamesWinByPlayerId(TEST_PLAYER_ID);
        assertTrue(numGamesWin == 0);
    }

    @Test
    public void findNumGamesWinByPlayerIdTestNegative() {
        Integer numGamesWin = gameRepository.findNumGamesWinByPlayerId(TEST_PLAYER_ID_12);
        assertTrue(numGamesWin == 0);
    }

    @Test
    public void findTimeGamesByPlayerIdTest() {
        Integer timeGames = gameRepository.findTimeGamesByPlayerId(TEST_PLAYER_ID);
        assertTrue(timeGames == 100);
    }

    @Test
    public void findTimeGamesByPlayerIdTestNegative() {
        Integer timeGames = gameRepository.findTimeGamesByPlayerId(TEST_PLAYER_ID_12);
        assertEquals(timeGames, null);
    }

    @Test
    public void findMaxTimeGamesByPlayerIdTest() {
        Integer maxTimeGames = gameRepository.findMaxTimeGamesByPlayerId(TEST_PLAYER_ID);
        assertTrue(maxTimeGames == 100);
    }

    @Test
    public void findMaxTimeGamesByPlayerIdTestNegative() {
        Integer maxTimeGames = gameRepository.findMaxTimeGamesByPlayerId(TEST_PLAYER_ID_12);
        assertEquals(maxTimeGames, null);
    }

    @Test
    public void findMinTimeGamesByPlayerIdTest() {
        Integer minTimeGames = gameRepository.findMinTimeGamesByPlayerId(TEST_PLAYER_ID);
        assertTrue(minTimeGames == 100);
    }

    @Test
    public void findMinTimeGamesByPlayerIdTestNegative() {
        Integer minTimeGames = gameRepository.findMinTimeGamesByPlayerId(TEST_PLAYER_ID_12);
        assertEquals(minTimeGames, null);
    }

    @Test
    public void findAvgTimeGamesByPlayerIdTest() {
        Double avgTimeGames = gameRepository.findAvgTimeGamesByPlayerId(TEST_PLAYER_ID);
        assertTrue(avgTimeGames == 100);
    }

    @Test
    public void findAvgTimeGamesByPlayerIdTestNegative() {
        Double avgTimeGames = gameRepository.findAvgTimeGamesByPlayerId(TEST_PLAYER_ID_12);
        assertEquals(avgTimeGames, null);
    }
}
