package org.springframework.samples.petclinic.round;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundRepository extends CrudRepository<Round, Integer>{
    List<Round> findAll() throws DataAccessException;
    
}
