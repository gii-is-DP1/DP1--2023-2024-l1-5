package org.springframework.samples.petclinic.achievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.samples.petclinic.achievement.Achievement;
import org.springframework.samples.petclinic.achievement.AchievementRepository;
import org.springframework.samples.petclinic.achievement.AchievementService;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {


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

        Achievement result = achievementService.getById(achievementId);

        assertEquals(achievement, result);
        verify(achievementRepository, times(1)).findById(achievementId);
    }
    
}
