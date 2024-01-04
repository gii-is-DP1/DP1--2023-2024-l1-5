package org.springframework.samples.petclinic.game;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameInfoRepository extends CrudRepository<GameInfo, Integer> {
    List<GameInfo> findAll() throws DataAccessException;
    GameInfo findByGameId(Integer id) throws DataAccessException;
    
}
