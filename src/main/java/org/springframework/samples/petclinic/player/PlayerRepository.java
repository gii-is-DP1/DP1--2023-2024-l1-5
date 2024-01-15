package org.springframework.samples.petclinic.player;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.samples.petclinic.user.User;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    List<Player> findAll() throws DataAccessException;
    public Optional<Player> findByUser(User user);
    public Optional<Player> findByUserId(int id);

    Boolean existsByPlayerUsername(String playerUsername);

    @Query("SELECT p FROM Player p WHERE p.user.username = :username")
    public Optional<Player> findByUsername(String username);
    public List<Player> findPlayersById(int id);
    public Optional<Player> findPlayerById(Integer id);

}
