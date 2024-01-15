package org.springframework.samples.petclinic.achievement;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AchievementServiceTest {

    private static final Integer TEST_ACHIEVEMENT_ID = 1;
    private static final Integer TEST_PLAYER_ID = 3;

    @Autowired
    private AchievementService achievementService;

    @Test
    public void testGetAllAchievements() {
        List<Achievement> achievements = achievementService.getAchievements();

        assertNotNull(achievements);
        assertTrue(achievements.size()>0);
    }

    @Test
    public void testGetAchievementById() {
        Achievement achievement = achievementService.getAchievementById(TEST_ACHIEVEMENT_ID);

        assertNotNull(achievement);
        assertEquals("Rookie Player", achievement.getName());
    }

    @Test
    public void testSaveAchievements() {
        Achievement achievementToSave = createValidAchievement();
        Achievement savedAchievement = achievementService.saveAchievement(achievementToSave);

        assertNotNull(savedAchievement);
        assertEquals(achievementToSave.getName(), savedAchievement.getName());
    }

    private Achievement createValidAchievement() {
        Achievement achievement = new Achievement();
        achievement.setId(100);
        achievement.setName("Test Achievement");
        achievement.setDescription("Test Description");
        achievement.setImageUrl("Test Image URL");
        achievement.setMetric(Metric.VICTORIES);
        achievement.setThreshold(10);
        return achievement;
    }

    @Test
    public void testGetUnlockedAchievementsByPlayerId() {
        achievementService.checkAndAssignAchievements(TEST_PLAYER_ID);
        List<Achievement> unlockedAchievements = achievementService.getUnlockedAchievementsByPlayerId(TEST_PLAYER_ID);

        assertNotNull(unlockedAchievements);
        assertNotEquals(0, unlockedAchievements.size());
    }

    @Test
    public void testGetLockedAchievementsByPlayerId() {
        achievementService.checkAndAssignAchievements(TEST_PLAYER_ID);
        List<Achievement> lockedAchievements = achievementService.getLockedAchievementsByPlayerId(TEST_PLAYER_ID);

        assertNotNull(lockedAchievements);
        assertNotEquals(0, lockedAchievements.size());
    }

    @Test
    public void testUnlockedAndLockedAchievementsAreDistinct() {
        achievementService.checkAndAssignAchievements(TEST_PLAYER_ID);

        List<Achievement> unlockedAchievements = achievementService.getUnlockedAchievementsByPlayerId(TEST_PLAYER_ID);
        List<Achievement> lockedAchievements = achievementService.getLockedAchievementsByPlayerId(TEST_PLAYER_ID);

        assertNotNull(unlockedAchievements);
        assertNotNull(lockedAchievements);

        assertTrue(Collections.disjoint(unlockedAchievements, lockedAchievements));
    }
    
}
