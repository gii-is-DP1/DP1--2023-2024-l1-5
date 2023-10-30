package org.springframework.samples.petclinic.hand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping
    public Hand createHand(Hand hand) {
        return handRepository.save(hand);
    }
    
}
