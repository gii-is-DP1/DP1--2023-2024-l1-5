package org.springframework.samples.petclinic.statistic;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;

@Repository
public interface PlayerStatisticRepository extends CrudRepository<PlayerStatistic, Integer>{

    List<PlayerStatistic> findAll() throws DataAccessException;

}
