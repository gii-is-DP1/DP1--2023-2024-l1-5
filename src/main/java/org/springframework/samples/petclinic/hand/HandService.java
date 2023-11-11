package org.springframework.samples.petclinic.hand;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.deck.Deck;
import org.springframework.samples.petclinic.round.Round;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

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

    @GetMapping
    public Hand getHandByRoundId(Integer roundId) {
        return handRepository.findByRoundId(roundId);
    }

    @GetMapping
    public Hand getHandByPlayerId(Integer playerId){
        return handRepository.findByPlayerId(playerId);
    }


    @Transactional
	public Hand updateHand(@Valid Hand hand, Integer idToUpdate,List<Card> ls, Round round) {
        Integer numCards = ls.size();
		Hand handToUpdate = getHandByPlayerId(idToUpdate);
        BeanUtils.copyProperties(hand, handToUpdate, "id","Player_id");
        handToUpdate.setNumCartas(numCards);
        handToUpdate.setCards(ls);
        handToUpdate.setRound(round);
        handRepository.save(handToUpdate);
        return handToUpdate;
	}    
}
