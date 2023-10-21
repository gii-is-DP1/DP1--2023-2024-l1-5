package org.springframework.samples.petclinic.game;

import java.util.List;

import org.springframework.dao.DataAccessException;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {

    // @Query("SELECT game_mode FROM GAMES WHERE game.id = :id")
    List<Game> findAll() throws DataAccessException;
}
