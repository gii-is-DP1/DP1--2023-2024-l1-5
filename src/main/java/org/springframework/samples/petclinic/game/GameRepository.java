package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {

    List<Game> findAll() throws DataAccessException;

    @Query("SELECT g FROM Game g WHERE g.creator.id = :creator")
    List<Game> findPlayerCreatedGames(Integer creator) throws DataAccessException;

    @Query("SELECT g FROM Game g WHERE g.status = 'WAITING' AND g.gameMode = 'QUICK_PLAY' AND g.numPlayers < 8")
    List<Game> findWaitingQuickGames() throws DataAccessException;

    @Query("SELECT g FROM Game g WHERE g.status = 'WAITING' AND g.gameMode = 'COMPETITIVE' AND g.numPlayers < 8")
    List<Game> findWaitingCompetitiveGames() throws DataAccessException;

}
