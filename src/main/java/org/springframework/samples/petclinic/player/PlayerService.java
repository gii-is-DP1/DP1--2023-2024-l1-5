package org.springframework.samples.petclinic.player;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.exceptions.FriendshipExistsException;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.user.User;

@Service
public class PlayerService {

    PlayerRepository playerRepository;
	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}
	@Transactional
    public Player savePlayer(Player player) {
        playerRepository.save(player);
        return player;
    }
    @Transactional(readOnly=true)
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }
    @Transactional(readOnly=true)
    public Optional<Player> getPlayerById(Integer id){
        return playerRepository.findById(id);
    }
    @Transactional(readOnly=true)
    public Player findPlayerByUser(User user) throws DataAccessException{
        return playerRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Player", "ID", user.getId()));
    }
    @Transactional(readOnly=true)
    public Player getPlayerByUserId(Integer id) throws DataAccessException{
        return playerRepository.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException("Player", "ID", id));
    }
    @Transactional(readOnly=true)
    public Player getPlayerByUsername(String username) throws DataAccessException{
        return playerRepository.findByUsername(username).orElseThrow(() -> new FriendshipExistsException("No se ha encontrado el usuario con username: " + username));
    }

    @Transactional
	public Player updatePlayer(Player player, int id) throws DataAccessException {
		Player toUpdate = getPlayerById(id).orElse(null);
		BeanUtils.copyProperties(player, toUpdate, "id", "user");
		playerRepository.save(toUpdate);
		return toUpdate;
	}

    public Boolean existsPlayerUser(String playerUsername) {
		return playerRepository.existsByPlayerUsername(playerUsername);
	}

}
