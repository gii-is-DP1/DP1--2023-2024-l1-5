package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameInfoService {


    UserService userService;
    PlayerService playerService;
    GameService gameService;
    GameInfoRepository gameInfoRepository;

    @Autowired
    public GameInfoService(UserService userService, PlayerService playerService, GameInfoRepository gameInfoRepository, GameService gameService) {
        this.userService = userService;
        this.playerService = playerService;
        this.gameInfoRepository = gameInfoRepository;
        this.gameService = gameService;
    }
    
    @Transactional
    public GameInfo saveGameInfo(GameInfo gameInfo){
        return gameInfoRepository.save(gameInfo);
    }

    @Transactional(readOnly=true)
    public List<GameInfo> findAllGameInfo(){
        return gameInfoRepository.findAll();
    }

    @Transactional(readOnly=true)
    public GameInfo findGameInfoById(Integer id){
        return gameInfoRepository.findById(id).get();
    }

    @Transactional(readOnly=true)
    public GameInfo findGameInfoByGameId(Integer id){
        return gameInfoRepository.findByGameId(id);
    }

    @Transactional()
    public GameInfo updateGameInfo(Integer gameId){
        GameInfo gameInfo = gameInfoRepository.findByGameId(gameId);
        Integer numPlayers = gameInfo.getNumPlayers();
        gameInfo.setNumPlayers(numPlayers+1);
        return gameInfoRepository.save(gameInfo);
    }
}
