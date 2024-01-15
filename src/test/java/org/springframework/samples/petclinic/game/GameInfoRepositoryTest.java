package org.springframework.samples.petclinic.game;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest()
public class GameInfoRepositoryTest {

    @Autowired
    GameInfoRepository gameInfoRepository;

    @Test
    public void testFindAll(){
        List<GameInfo> ls = gameInfoRepository.findAll();
        assertNotNull(ls);
    }

    @Test
    public void testFindByGameId(){
        GameInfo res = gameInfoRepository.findByGameId(1);
        assertNotNull(res);

    }
    
}
