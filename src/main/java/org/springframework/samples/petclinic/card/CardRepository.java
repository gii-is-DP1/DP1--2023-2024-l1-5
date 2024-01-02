package org.springframework.samples.petclinic.card;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>{
    
    List<Card> findAll() throws DataAccessException;

    // @Query("SELECT c FROM Card c WHERE c.deck.id = ?1")
    // List<Card> findByDeckId(Integer deckId) throws DataAccessException;

    // @Query("SELECT c FROM Card c WHERE c.hand.id = ?1")
    // List<Card> findByHandId(Integer handId) throws DataAccessException;


    @Query("SELECT c FROM Card c ORDER BY c.id LIMIT 16")
    List<Card> get16LastCards() throws DataAccessException;

}
