package org.springframework.samples.petclinic.achievement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest()
public class AchievementRepositoryTest {

    @Autowired
    AchievementRepository achievementRepository;

    @Test
    public void testFindAll() {
        List<Achievement> achievements = achievementRepository.findAll();
        assertNotNull(achievements);
        assertTrue(achievements.size() > 0);
    }

    @Test
    public void testFindByName(){
        Achievement result = achievementRepository.findByName("Professional");
        assertNotNull(result);
    }
    
}
