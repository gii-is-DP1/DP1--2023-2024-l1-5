package org.springframework.samples.petclinic.player;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest()
public class PlayerRepositoryTest {

    @Autowired
    PlayerRepository playerRepository;

    @Test
    public void testFindAll() {
        List<Player> players = playerRepository.findAll();
        assertNotNull(players);
        assertTrue(players.size() > 0);
    }

    @Test
    public void testFindById(){
        Player result = playerRepository.findPlayerById(1).orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByUserId(){
        Player result = playerRepository.findByUserId(200).orElse(null);
        assertNotNull(result);
    }
    
}
