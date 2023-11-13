package org.springframework.samples.petclinic.hand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HandService {

    HandRepository handRepository;

    @Autowired
    public HandService(HandRepository handRepository) {
        this.handRepository = handRepository;
    }

    @Transactional
    public Hand saveHand(Hand hand) {
        return handRepository.save(hand);
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
    public Hand getHandByRoundId(Integer roundId) {
        return handRepository.findByRoundId(roundId);
    }

    @Transactional(readOnly = true)
    public Hand getHandByPlayerId(Integer playerId) {
        return handRepository.findByPlayerId(playerId);
    }

    @Transactional
    public Hand updateHand(Hand hand, Integer id) {
        Hand toUpdate = getHandById(id).get();
        BeanUtils.copyProperties(hand, toUpdate, "id");
        return saveHand(toUpdate);
    }
}
