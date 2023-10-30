package org.springframework.samples.petclinic.player;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.samples.petclinic.user.User;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    List<Player> findAll() throws DataAccessException;
    public Optional<Player> findByUser(User user);
    public Optional<Player> findByUserId(int id);
}
