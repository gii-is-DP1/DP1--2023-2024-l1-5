package org.springframework.samples.petclinic.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testSaveGame() {
        Game gameToSave = new Game();
        when(gameRepository.save(any(Game.class))).thenReturn(gameToSave);

        Game savedGame = gameService.saveGame(gameToSave);

        assertNotNull(savedGame);
        assertEquals(gameToSave, savedGame);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    public void testGetAllGames() {
        List<Game> games = new ArrayList<>();
        when(gameRepository.findAll()).thenReturn(games);

        List<Game> result = gameService.getAllGames();

        assertNotNull(result);
        assertEquals(games, result);
        verify(gameRepository, times(1)).findAll();
    }
    
    @Test
    public void testGetGameById() {
        Integer gameId = 1;
        Game game = new Game();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.getGameById(gameId);

        assertTrue(result.isPresent());
        assertEquals(game, result.get());
        verify(gameRepository, times(1)).findById(gameId);
    }

    @Test
    public void testHasActiveGame() {

        Player player = new Player();
        Integer playerId = 1;
        player.setId(playerId);

        List<Game> playerGames = new ArrayList<>();

        //Case 1: Check that there is an active game
        playerGames.add(createGame(GameStatus.WAITING));
        playerGames.add(createGame(GameStatus.IN_PROGRESS));
        when(gameRepository.findPlayerCreatedGames(playerId)).thenReturn(playerGames);
        assertTrue(gameService.hasActiveGame(player));

        //Case 2: Check that there is no active game
        playerGames.clear();
        playerGames.add(createGame(GameStatus.FINALIZED));
        when(gameRepository.findPlayerCreatedGames(playerId)).thenReturn(playerGames);
        assertFalse(gameService.hasActiveGame(player));
    }

    private Game createGame(GameStatus status) {
        Game game = new Game();
        game.setStatus(status);
        return game;
    }
}
