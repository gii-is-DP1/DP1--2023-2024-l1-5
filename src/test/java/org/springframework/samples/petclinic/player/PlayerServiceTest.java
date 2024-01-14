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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerService playerService;

    private static final Integer PLAYER_ID = 2;
    private static final Integer USER_ID = 202;

    @Test
    public void testSavePlayer() {
        playerService = new PlayerService(playerRepository);
        Player player = new Player();
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player savedPlayer = playerService.savePlayer(player);

        assertNotNull(savedPlayer);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    public void testGetAllPlayers() {
        playerService = new PlayerService(playerRepository);
        when(playerRepository.findAll()).thenReturn(new ArrayList<>());

        List<Player> players = playerService.getAllPlayers();
        assertNotNull(players);
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    public void testGetPlayerById() {
        playerService = new PlayerService(playerRepository);
        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.of(new Player()));

        Optional<Player> result = playerService.getPlayerById(PLAYER_ID);

        assertTrue(result.isPresent());
        verify(playerRepository, times(1)).findById(PLAYER_ID);
    }

    @Test
    public void testGetPlayerByUserId() {
        playerService = new PlayerService(playerRepository);
        Player expectedPlayer = new Player();
        expectedPlayer.setId(1);
        expectedPlayer.setPlayerUsername("TestUsername");

        when(playerRepository.findByUserId(USER_ID)).thenReturn(Optional.of(expectedPlayer));

        Player resultPlayer = playerService.getPlayerByUserId(USER_ID);

        assertNotNull(resultPlayer);
        assertEquals(expectedPlayer.getPlayerUsername(), resultPlayer.getPlayerUsername());
        verify(playerRepository, times(1)).findByUserId(USER_ID);
    }

    @Test
    public void testUpdatePlayer() {
        playerService = new PlayerService(playerRepository);
        Player existingPlayer = new Player();
        existingPlayer.setId(PLAYER_ID);
        existingPlayer.setPlayerUsername("OriginalUsername");

        Player updateInfo = new Player();
        updateInfo.setPlayerUsername("UpdatedUsername");

        when(playerRepository.findById(PLAYER_ID)).thenReturn(Optional.of(existingPlayer));
        when(playerRepository.save(any(Player.class))).thenReturn(existingPlayer);

        Player updatedPlayer = playerService.updatePlayer(updateInfo, PLAYER_ID);

        assertNotNull(updatedPlayer);
        assertEquals("UpdatedUsername", updatedPlayer.getPlayerUsername());
        verify(playerRepository, times(1)).save(any(Player.class));
    }


}