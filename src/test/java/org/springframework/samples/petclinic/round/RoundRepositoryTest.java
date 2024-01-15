package org.springframework.samples.petclinic.round;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest()
public class RoundRepositoryTest {

    @Autowired
    RoundRepository roundRepository;


    @Test
    public void testFindAll(){
        List<Round> rounds = roundRepository.findAll();
        assertNotNull(rounds);
        assertFalse(rounds.isEmpty());
    }
}
