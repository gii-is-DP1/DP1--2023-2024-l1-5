package org.springframework.samples.petclinic.player;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    private static final Integer PLAYER_ID = 1;
    private static final Integer USER_ID = 201;

    @Test
    public void testFindAll() {
        List<Player> players = playerRepository.findAll();
        assertNotNull(players);
        assertFalse(players.isEmpty());
    }

    @Test
    public void testFindByUserId() {
        Optional<Player> player = playerRepository.findByUserId(USER_ID);
        assertTrue(player.isPresent());
    }

    @Test
    public void testFindPlayerById() {
        Optional<Player> foundPlayer = playerRepository.findPlayerById(PLAYER_ID);
        assertTrue(foundPlayer.isPresent());
        assertEquals(PLAYER_ID, foundPlayer.get().getId());
    }

    @Test
    public void testSavePlayer() {
        Player newPlayer = new Player();
        newPlayer.setPlayerUsername("TestUsername");
        Player savedPlayer = playerRepository.save(newPlayer);

        assertNotNull(savedPlayer);
        assertNotNull(savedPlayer.getId());
        assertEquals("TestUsername", savedPlayer.getPlayerUsername());
    }

    @Test
    public void testUpdatePlayer() {
        Optional<Player> originalPlayer = playerRepository.findById(PLAYER_ID);
        assertTrue(originalPlayer.isPresent());

        Player playerToUpdate = originalPlayer.get();
        playerToUpdate.setPlayerUsername("UpdatedUsername");
        playerRepository.save(playerToUpdate);

        Optional<Player> updatedPlayer = playerRepository.findById(PLAYER_ID);
        assertTrue(updatedPlayer.isPresent());
        assertEquals("UpdatedUsername", updatedPlayer.get().getPlayerUsername());
    }

    @Test
    public void testDeletePlayer() {
        assertTrue(playerRepository.findById(PLAYER_ID).isPresent());

        playerRepository.deleteById(PLAYER_ID);

        assertFalse(playerRepository.findById(PLAYER_ID).isPresent());
    }

}
