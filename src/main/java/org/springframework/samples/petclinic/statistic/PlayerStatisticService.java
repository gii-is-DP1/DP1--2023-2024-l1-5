package org.springframework.samples.petclinic.statistic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PlayerStatisticService {

    PlayerStatisticRepository playerStatisticRepository;

    @Autowired
    public PlayerStatisticService(PlayerStatisticRepository playerStatisticRepository){
        this.playerStatisticRepository = playerStatisticRepository;
    }

    @Transactional
    public PlayerStatistic save(PlayerStatistic playerStatistic){
        return playerStatisticRepository.save(playerStatistic);
    }

    @Transactional
    public void saveGameStatistic(PlayerStatistic gameStatistic){
        playerStatisticRepository.save(gameStatistic);
    }

    @Transactional
    public List<PlayerStatistic> getAllGameS(){
        return playerStatisticRepository.findAll();
    }

    @Transactional
    public PlayerStatistic findById(Integer id){
        return playerStatisticRepository.findById(id).get();
    }

    
    
}
