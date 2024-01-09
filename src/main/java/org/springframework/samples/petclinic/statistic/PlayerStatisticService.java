package org.springframework.samples.petclinic.statistic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public PlayerStatistic update(PlayerStatistic playerStatistic){
        playerStatisticRepository.save(playerStatistic);
        return playerStatistic;
    }

    @Transactional
    public List<PlayerStatistic> getAllGameS(){
        return playerStatisticRepository.findAll();
    }

    @Transactional
    public PlayerStatistic getPSById(Integer id) throws DataAccessException{
        return playerStatisticRepository.findById(id).get();
    }
    
    
}
