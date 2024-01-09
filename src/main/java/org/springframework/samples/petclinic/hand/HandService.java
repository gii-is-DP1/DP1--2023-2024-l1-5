package org.springframework.samples.petclinic.hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.samples.petclinic.round.RoundService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class HandService {

    HandRepository handRepository;
    RoundService roundService;
    PlayerService playerService;

    @Autowired
    public HandService(HandRepository handRepository, RoundService roundService, PlayerService playerService) {
        this.handRepository = handRepository;
        this.roundService = roundService;
        this.playerService = playerService;
    }

    @Transactional
    public Hand saveHand(Hand hand) throws DataAccessException {
        handRepository.save(hand);
        return hand;
    }

    @Transactional
    public Hand createHand(Integer roundId,Integer playerId) throws DataAccessException {
        Hand newHand = new Hand();
        newHand.setNumCartas(10);
        Round round = roundService.getRoundById(roundId).get();
        Player player = playerService.getPlayerById(playerId).get();
        newHand.setRound(round);
        newHand.setPlayer(player);
        newHand.setCards(new ArrayList<>());
        return saveHand(newHand);
    }

    @Transactional
    public List<Hand> getAllHands() {
        return handRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Hand> getHandById(Integer id) {
        return handRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Hand getHandByPlayerId(Integer playerId) {
        return handRepository.findHandByPlayerId(playerId);
    }

    @Transactional(readOnly = true)
    public List<Hand> getHandByRoundId(Integer roundId) {
        return handRepository.findByRoundId(roundId);
    }


    @Transactional
    public Hand updateHand(Hand hand, Integer id) {
        Hand toUpdate = getHandById(id).get();
        BeanUtils.copyProperties(hand, toUpdate, "id");
        return saveHand(toUpdate);
    }

    @Transactional
    public void deleteHandById(Integer id) {
        handRepository.deleteById(id);
    }
}
