package org.springframework.samples.petclinic.hand;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Repository
public interface HandRepository extends CrudRepository<Hand, Integer> {

    List<Hand> findAll() throws DataAccessException;

    Hand findByRoundId(Integer roundId);

    Hand findByPlayerId(Integer playerId);
}
