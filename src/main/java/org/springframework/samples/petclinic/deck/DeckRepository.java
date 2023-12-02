package org.springframework.samples.petclinic.deck;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Integer> {

    List<Deck> findAll() throws DataAccessException;

    Deck findByRoundId(Integer roundId);
}