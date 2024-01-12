package org.springframework.samples.petclinic.game;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.UserService;

@ExtendWith(MockitoExtension.class)
public class GameInfoServiceTests {

    @Mock
    GameInfoRepository repo;

    @Mock
    GameInfoService gameInfoService;

    @Mock
    UserService userService;

    @Mock
    PlayerService playerService;

    @Mock 
    GameService gameService;

    
    

    @Test
    public void testGetAllGameInfo() {
        gameInfoService = new GameInfoService(userService, playerService, repo, gameService);
        List<GameInfo> infoGames = new ArrayList<>();
        when(gameInfoService.findAllGameInfo()).thenReturn(infoGames);
        List<GameInfo> result = gameInfoService.findAllGameInfo();
        assertNotNull(result);
        assertEquals(infoGames, result);
        verify(repo, times(1)).findAll();

    }
    
    @Test
    public void testGetGameInfoById() {
        gameInfoService = new GameInfoService(userService, playerService, repo, gameService);
        GameInfo gi = new GameInfo();
        Integer id = 1;  
        when(repo.findById(id)).thenReturn(Optional.of(gi)); 

        assertEquals(gi, gameInfoService.findGameInfoById(id));
    }
    

    @Test
    public void testGetGameInfoByGameId() {
        gameInfoService = new GameInfoService(userService, playerService, repo, gameService);
        GameInfo gi = new GameInfo();
        Integer Id = 1;
        when(gameInfoService.findGameInfoByGameId(Id)).thenReturn(gi);
        GameInfo result = gameInfoService.findGameInfoByGameId(Id);
        assertEquals(gi, result);
        verify(repo, times(1)).findByGameId(Id);

    }




    @Test
    public void testUpdateGameInfo(){
        GameInfo gi = new GameInfo();
        Integer Id = 1;
        gi.setId(Id);
        gi.setNumPlayers(1);
        GameInfo updated = new GameInfo();
        when(repo.findById(1)).thenReturn(Optional.of(gi));
        when(gameInfoService.saveGameInfo(gi)).thenReturn(gi);
        GameInfo result = gameInfoService.updateGameInfo(gi.getId());
        assertNotEquals(gi, result);

    }
}
