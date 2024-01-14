package org.springframework.samples.petclinic.game;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.game.exceptions.WaitingGamesNotFoundException;
import org.springframework.samples.petclinic.invitation.InvitationService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;




@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameInfoRepository gameInfoRepository;

    @Mock
    private UserService userService;

    @Mock
    private InvitationService invitationService;

    @Mock   
    private PlayerService playerService;

    @Mock
    private GameService gameService;

    @Test
    public void testGetAllGames() {
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
        List<Game> games = new ArrayList<>();
        when(gameRepository.findAll()).thenReturn(games);

        List<Game> result = gameService.getAllGames();

        assertNotNull(result);
        assertEquals(games, result);
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    public void testGetInProgressGames(){
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
        List<Game> ipGames = new ArrayList<>();
        when(gameRepository.findInProgressGames()).thenReturn(ipGames);
        List<Game> result = gameService.getInProgressGames();
        assertNotNull(result);
        assertEquals(ipGames, result);
        verify(gameRepository, times(1)).findInProgressGames();

    }

    @Test
    public void testGetWaitingGames(){
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
        List<Game> wGames = new ArrayList<>();
        when(gameRepository.findWaitingGames()).thenReturn(wGames);
        List<Game> result = gameService.getWaitingGames();
        assertNotNull(result);
        assertEquals(wGames, result);
        verify(gameRepository, times(1)).findWaitingGames();
    }

    @Test
    public void testGetFinalizedGames(){
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
        List<Game> fGames = new ArrayList<>();
        when(gameRepository.findFinalizedGames()).thenReturn(fGames);
        List<Game> result = gameService.getFinalizedGames();
        assertNotNull(result);
        assertEquals(fGames, result);
        verify(gameRepository, times(1)).findFinalizedGames();
    }
    
    @Test
    public void testGetGameById() {
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
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
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
        Player player = new Player();
        Integer playerId = 3;
        player.setId(playerId);

        //Case 1: Check that there is an active game
        when(gameRepository.findWaitingPlayerGame(playerId)).thenReturn(createGame(GameStatus.WAITING));
        when(gameRepository.findInProgressPlayerGame(playerId)).thenReturn(createGame(GameStatus.IN_PROGRESS));
        assertTrue(gameService.hasActiveGame(player));

        //Case 2: Check that there is no active game

        when(gameRepository.findWaitingPlayerGame(playerId)).thenReturn(null);
        when(gameRepository.findInProgressPlayerGame(playerId)).thenReturn(null);
        assertFalse(gameService.hasActiveGame(player));
    }

    private Game createGame(GameStatus status) {
        Game game = new Game();
        game.setId(1000);
        game.setStatus(status);
        return game;
    }

    

    @Test
    public void testGetNoRandomGame() {
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
        GameMode gm = GameMode.QUICK_PLAY;
        WaitingGamesNotFoundException exception = assertThrows(WaitingGamesNotFoundException.class, () -> {
            gameService.getRandomGame(gm.toString());
        });
        assertEquals("No se ha encontrado ninguna partida en espera", exception.getMessage());
    }

    @Test
    public void testGetRandomGame() {
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
        GameMode gm = GameMode.QUICK_PLAY;
        Game g = createGame(GameStatus.WAITING);
        g.setId(1);
        g.setGameMode(gm);
    
        List<Game> games = new ArrayList<>();
        games.add(g);
    
        // Simula el comportamiento del repositorio
        when(gameRepository.findWaitingQuickGames()).thenReturn(games);
    
        Optional<Game> result = gameService.getRandomGame(gm.toString());
    
        assertNotNull(result);
        assertEquals(g, result.get());
    }


    @Test
    public void testGetNoWaitingGame(){
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
         Player player = new Player();
          Optional<Game> result = gameService.getWaitingGame(player);
          assertNull(result);
        }

    @Test
    public void testGetWaitingGame() {
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
        Player player = new Player();
        player.setId(2);
        
        Game game = createGame(GameStatus.WAITING);
        game.setId(1);
        game.setPlayers(new ArrayList<>(List.of(player)));
        game.setWinner(player.getId());
        
        List<Game> playerGames = new ArrayList<>();
        playerGames.add(game);
        
        when(gameRepository.findPlayerCreatedGames(player.getId())).thenReturn(playerGames);
        
        Optional<Game> result = gameService.getWaitingGame(player);
        
        assertTrue(result.isPresent());
        assertEquals(game, result.get());
    }

     @Test
     public void testDeletePlayerFromGame() {
        gameService = new GameService(gameRepository, userService, playerService, gameInfoRepository,invitationService);
         Integer gameId = 1;
         Integer userId1 = 1;
         Integer userId2 = 2;
 
         List<Player> players = new ArrayList<>();

         Player player1 = new Player();
         User user1 = new User();
         user1.setId(userId1);
         player1.setUser(user1);
         players.add(player1);

         Player player2 = new Player();
         User user2 = new User();
         user2.setId(userId2);
         player2.setUser(user2);
         players.add(player2);


         Game game = new Game();
         game.setId(gameId);
         game.setNumPlayers(2);
         game.setCreator(player1);

         game.setPlayers(players);
 
         when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
 
         gameService.deletePlayerFromGame(gameId, userId1);
 
         assertEquals(1, game.getPlayers().size());
         assertEquals(1, game.getNumPlayers());
     }
}
