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

    @Test
    public void testFindPlayerCreatedGames() {

        //Caso 1: Comprobar que el jugador 1 ha creado partidas
        List<Game> playerCreatedGames1 = gameRepository.findPlayerCreatedGames(1);
        assertNotNull(playerCreatedGames1);
        assertTrue(playerCreatedGames1.size() > 0);

        //Caso 2: Comprobar que el jugador 3 no ha creado partidas
        List<Game> playerCreatedGames2 = gameRepository.findPlayerCreatedGames(3);
        assertNotNull(playerCreatedGames2);
        assertEquals(0, playerCreatedGames2.size());

        //Caso 3: Comprobar que se maneja correctamente al brindar un valor null
        List<Game> playerCreatedGames3 = gameRepository.findPlayerCreatedGames(null);
        assertTrue(playerCreatedGames3.isEmpty());
    }

    @Test
    public void testFindWaitingQuickGames() {

        // Caso 1: Juegos en espera con modo QUICK_PLAY y menos de 8 jugadores
        List<Game> waitingQuickGames1 = gameRepository.findWaitingQuickGames();
        assertNotNull(waitingQuickGames1);
        assertTrue(checkValuesOfGameList(waitingQuickGames1, GameMode.QUICK_PLAY));
        assertTrue(waitingQuickGames1.size() > 0);

    }

    @Test
    public void testFindWaitingCompetitiveGames() {
    
        // Caso 1: Juegos en espera con modo COMPETITIVE y menos de 8 jugadores
        List<Game> waitingCompetitiveGames1 = gameRepository.findWaitingCompetitiveGames();
        assertNotNull(waitingCompetitiveGames1);
        assertTrue(checkValuesOfGameList(waitingCompetitiveGames1, GameMode.COMPETITIVE));
        assertTrue(waitingCompetitiveGames1.size() > 0);
    }

    public Boolean checkValuesOfGameList(List<Game> games, GameMode GM){
        for(Game g : games){
            if(g.getGameMode() != GM && g.getStatus() != GameStatus.WAITING && g.getNumPlayers() >= 8)
                return false;
        }
        return true;
    }
    
}
