package org.springframework.samples.petclinic.card;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import java.util.List;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>{
    
    List<Card> findAll() throws DataAccessException;
}
