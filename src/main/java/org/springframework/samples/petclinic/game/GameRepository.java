package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT g FROM Game g WHERE g.status = 'IN_PROGRESS'")
    List<Game> findInProgressGames() throws DataAccessException;

    @Query("SELECT g FROM Game g JOIN g.players p WHERE p.id = :playerId AND g.status = 'IN_PROGRESS'")
    Game findWaitingPlayerGame(Integer playerId) throws DataAccessException;

    @Query("SELECT g FROM Game g JOIN g.players p WHERE p.id = :playerId AND g.status = 'WAITING'")
    Game findInProgressPlayerGame(Integer playerId) throws DataAccessException;

    @Query("SELECT g FROM Game g WHERE g.status = 'FINALIZED'")
    List<Game> findFinalizedGames() throws DataAccessException;

    @Query("SELECT g FROM Game g WHERE g.status = 'WAITING'")
    List<Game> findWaitingGames() throws DataAccessException;

    @Query("SELECT DISTINCT g FROM Game g JOIN g.players p WHERE p.id = :playerId AND g.status = 'IN_PROGRESS'")
    List<Game> findPlayerGamesInProgress(@Param("playerId") Integer playerId) throws DataAccessException;

    @Query("SELECT DISTINCT g FROM Game g JOIN g.players p WHERE p.id = :playerId AND g.status = 'WAITING'")
    List<Game> findPlayerGamesWaiting(@Param("playerId") Integer playerId) throws DataAccessException;



}
