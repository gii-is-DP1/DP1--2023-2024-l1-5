package org.springframework.samples.petclinic.achievement;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer>{
    
    List<Achievement> findAll();

    public Achievement findByName(String name);

    @Query("SELECT a FROM Achievement a JOIN a.players p WHERE p.id = :playerId")
    List<Achievement> findUnlockedAchievementsByPlayerId(@Param("playerId") Integer id);

    @Query("SELECT a FROM Achievement a WHERE a NOT IN (SELECT a FROM Achievement a JOIN a.players p WHERE p.id = :playerId)")
    List<Achievement> findLockedAchievementsByPlayerId(@Param("playerId") Integer id);


}

