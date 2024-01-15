package org.springframework.samples.petclinic.deck;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Integer> {

    List<Deck> findAll() throws DataAccessException;

    @Query("SELECT d FROM Deck d WHERE d.round.id = :roundId")
    Optional<Deck> findByRoundId(Integer roundId);
}