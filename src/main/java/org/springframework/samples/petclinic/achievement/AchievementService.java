package org.springframework.samples.petclinic.achievement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

@Service
public class AchievementService {
        
    AchievementRepository achievementRepository;
    GameService gameService;
    PlayerService playerService;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, GameService gameService, PlayerService playerService) {
        this.achievementRepository=achievementRepository;
        this.gameService=gameService;
        this.playerService=playerService;
    }

    @Transactional(readOnly = true)    
    public List<Achievement> getAchievements(){
        return achievementRepository.findAll();
    }
    
    @Transactional(readOnly = true)    
    public Achievement getAchievementById(int id){
        Optional<Achievement> result = achievementRepository.findById(id);
        return result.orElseThrow(() -> new ResourceNotFoundException("Achievement with id " + id + " not found!"));
    }

    @Transactional
    public Achievement saveAchievement(@Valid Achievement newAchievement) {
        return achievementRepository.save(newAchievement);
    }
    
    @Transactional
    public void deleteAchievementById(int id){
        achievementRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementByName(String name){
        return achievementRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Achievement> getUnlockedAchievementsByPlayerId(Integer playerId){
        return achievementRepository.findUnlockedAchievementsByPlayerId(playerId);
    }

    @Transactional(readOnly = true)
    public List<Achievement> getLockedAchievementsByPlayerId(Integer playerId){
        return achievementRepository.findLockedAchievementsByPlayerId(playerId);
    }

    @Transactional
    public void checkAndAssignAchievements(Integer playerId) {
        Player player = playerService.getPlayerById(playerId).get();
        Integer userId = player.getUser().getId();
        Integer gamesPlayed = gameService.getNumGamesByPlayerId(playerId);
        Integer victories = gameService.getNumGamesWinByPlayerId(userId); 
        Integer totalPlayTime = gameService.getTimesGamesWinByPlayerId(playerId);
        List<Achievement> achievements = achievementRepository.findAll();
        for (Achievement achievement : achievements) {
            if (achievement.getMetric().equals(Metric.GAMES_PLAYED)) {
                if (gamesPlayed >= achievement.getThreshold()) {
                    assignAchievement(achievement, player);
                }
            } else if (achievement.getMetric().equals(Metric.VICTORIES)) {
                if (victories >= achievement.getThreshold()) {
                    assignAchievement(achievement, player);
                }
            } else if (achievement.getMetric().equals(Metric.TOTAL_PLAY_TIME)) {
                if (totalPlayTime >= achievement.getThreshold()) {
                    assignAchievement(achievement, player);
                }
            }
        }
    }

    public void assignAchievement(Achievement achievement, Player player) {
        List<Player> players = achievement.getPlayers();
        if (!players.contains(player)) {
            players.add(player);
            achievement.setPlayers(players);
            saveAchievement(achievement);
        }
    }

}
