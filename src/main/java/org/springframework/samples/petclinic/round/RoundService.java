package org.springframework.samples.petclinic.round;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RoundService {
    RoundRepository roundRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }
    @Transactional
    public Round saveRound(Round round) {
        roundRepository.save(round);
        return round;
    }

    @Transactional(readOnly=true)
    public List<Round> getAllRounds() {
        return roundRepository.findAll();
    }
    
    @Transactional(readOnly=true)
    public Optional<Round> getRoundById(Integer id) {
        return roundRepository.findById(id);
    }
}
