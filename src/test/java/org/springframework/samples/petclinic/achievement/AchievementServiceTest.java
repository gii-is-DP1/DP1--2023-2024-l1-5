package org.springframework.samples.petclinic.achievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    private static final Integer PLAYER_ID = 1;

    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementService achievementService;

    @Test
    public void testSaveAchievements() {
        Achievement achievementToSave = new Achievement();
        when(achievementRepository.save(any(Achievement.class))).thenReturn(achievementToSave);

        Achievement savedAchievement = achievementService.saveAchievement(achievementToSave);

        assertNotNull(savedAchievement);
        assertEquals(achievementToSave, savedAchievement);
        verify(achievementRepository, times(1)).save(any(Achievement.class));
    }

    @Test
    public void testGetAllAchievements() {
        List<Achievement> achievements = new ArrayList<>();
        when(achievementRepository.findAll()).thenReturn(achievements);

        List<Achievement> result = achievementService.getAchievements();

        assertNotNull(result);
        assertEquals(achievements, result);
        verify(achievementRepository, times(1)).findAll();
    }

    @Test
    public void testGetAchievementById() {
        Integer achievementId = 1;
        Achievement achievement = new Achievement();
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));

        Achievement result = achievementService.getAchievementById(achievementId);

        assertEquals(achievement, result);
        verify(achievementRepository, times(1)).findById(achievementId);
    }

    @Test
    public void testGetUnlockedAchievementsByPlayerId() {
        List<Achievement> mockAchievements = new ArrayList<>();
        when(achievementRepository.findUnlockedAchievementsByPlayerId(PLAYER_ID)).thenReturn(mockAchievements);

        List<Achievement> result = achievementService.getUnlockedAchievementsByPlayerId(PLAYER_ID);

        assertNotNull(result);
        verify(achievementRepository, times(1)).findUnlockedAchievementsByPlayerId(PLAYER_ID);
    }

    @Test
    public void testGetLockedAchievementsByPlayerId() {
        List<Achievement> mockAchievements = new ArrayList<>();
        when(achievementRepository.findLockedAchievementsByPlayerId(PLAYER_ID)).thenReturn(mockAchievements);

        List<Achievement> result = achievementService.getLockedAchievementsByPlayerId(PLAYER_ID);

        assertNotNull(result);
        verify(achievementRepository, times(1)).findLockedAchievementsByPlayerId(PLAYER_ID);
    }

    @Test
    public void testUnlockedAndLockedAchievementsAreDistinct() {
        List<Achievement> mockUnlockedAchievements = new ArrayList<>();
        List<Achievement> mockLockedAchievements = new ArrayList<>();

        when(achievementRepository.findUnlockedAchievementsByPlayerId(PLAYER_ID)).thenReturn(mockUnlockedAchievements);
        when(achievementRepository.findLockedAchievementsByPlayerId(PLAYER_ID)).thenReturn(mockLockedAchievements);

        List<Achievement> unlockedResult = achievementService.getUnlockedAchievementsByPlayerId(PLAYER_ID);
        List<Achievement> lockedResult = achievementService.getLockedAchievementsByPlayerId(PLAYER_ID);

        assertNotNull(unlockedResult);
        assertNotNull(lockedResult);

        assertTrue(Collections.disjoint(unlockedResult, lockedResult));
}
    
}
