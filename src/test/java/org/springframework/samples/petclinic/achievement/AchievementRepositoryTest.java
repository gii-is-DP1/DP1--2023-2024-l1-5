package org.springframework.samples.petclinic.achievement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest()
public class AchievementRepositoryTest {

    private static final Integer PLAYER_ID = 1;

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
        Achievement result = achievementRepository.findByName("First Victory");
        assertNotNull(result);
    }

    @Test
    public void testFindUnlockedAchievementsByPlayerId() {
        List<Achievement> unlockedAchievements = achievementRepository.findUnlockedAchievementsByPlayerId(PLAYER_ID);
        assertNotNull(unlockedAchievements);
    }

    @Test
    public void testFindLockedAchievementsByPlayerId() {
        List<Achievement> lockedAchievements = achievementRepository.findLockedAchievementsByPlayerId(PLAYER_ID);
        assertNotNull(lockedAchievements);
    }

    @Test
    public void testUnlockedAndLockedAchievementsAreDistinct() {
        List<Achievement> unlockedAchievements = achievementRepository.findUnlockedAchievementsByPlayerId(PLAYER_ID);
        List<Achievement> lockedAchievements = achievementRepository.findLockedAchievementsByPlayerId(PLAYER_ID);
        assertNotNull(unlockedAchievements);
        assertNotNull(lockedAchievements);

        assertTrue(Collections.disjoint(unlockedAchievements, lockedAchievements));
    }
    
}
