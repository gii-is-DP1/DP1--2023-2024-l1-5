package org.springframework.samples.petclinic.hand;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandRepository extends CrudRepository<Hand, Integer> {

    List<Hand> findAll() throws DataAccessException;

    @Query ("SELECT hand FROM Hand hand WHERE hand.player.id =:id")
    Hand findHandByPlayerId(Integer id) throws DataAccessException;

    // Hand findByRoundId(Integer roundId);

    // Hand findByPlayerId(Integer playerId);
}
