package org.springframework.samples.petclinic.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;


    @Test
    public void testSavePlayers() {
        Player playerToSave = new Player();
        when(playerRepository.save(any(Player.class))).thenReturn(playerToSave);

        Player savedPlayer = playerService.savePlayer(playerToSave);

        assertNotNull(savedPlayer);
        assertEquals(playerToSave, savedPlayer);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    public void testGetAllPlayers() {
        List<Player> players = new ArrayList<>();
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.getAllPlayers();

        assertNotNull(result);
        assertEquals(players, result);
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    public void testGetPlayerById() {
        Integer playerId = 1;
        Player Player = new Player();
        when(playerService.getPlayerById(playerId)).thenReturn(Optional.of(Player));

        Player result = playerService.getPlayerById(playerId).orElse(null);

        assertEquals(Player, result);
        verify(playerRepository, times(1)).findById(playerId);
    }

    /*@Test
    public void testGetPlayerByUserId() {
        Integer userId = 200;
        Player Player = new Player();
        when(playerService.getPlayerByUserId(userId)).thenReturn(Player);

        Player result = playerService.getPlayerByUserId(userId);

        assertEquals(Player, result);
        verify(playerRepository, times(1)).findByUserId(userId);
    }*/

    @Test
    public void testUpdatePlayer() {
        Integer playerId = 1;
        Player player = new Player();
        when(playerService.getPlayerById(playerId)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.updatePlayer(player, playerId);

        assertNotNull(result);
        assertEquals(player, result);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    
}
