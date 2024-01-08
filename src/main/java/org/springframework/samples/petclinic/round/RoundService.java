package org.springframework.samples.petclinic.round;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameMode;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.round.exceptions.WaitingGameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;


@Service
public class RoundService {
    RoundRepository roundRepository;
    GameService gameService;

    @Autowired
    public RoundService(RoundRepository roundRepository, GameService gameService) {
        this.roundRepository = roundRepository;
        this.gameService = gameService;
    }

    @Transactional
    public Round saveRound(Round round, Game game) {
        GameMode gameMode = game.getGameMode();
        if(game.getRounds() == null || game.getRounds().size() == 0){
            roundRepository.save(round);
        }else if(gameMode.equals(GameMode.COMPETITIVE) && (game.getRounds().size()<5)){
            roundRepository.save(round);
        } else if(gameMode.equals(GameMode.QUICK_PLAY) && (game.getRounds().size()<1)){
            roundRepository.save(round);
        } else {
            throw new WaitingGameException("No puedes crear más rondas para este modo de juego");
        }
        return round;
    }

    @Transactional(readOnly = true)
    public List<Round> getAllRounds() {
        return roundRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Round> getRoundById(Integer id) {
        return roundRepository.findById(id);
    }

    @Transactional
    public Map<Integer, List<Card>> distribute(List<Card> lsCards, GameMode gameMode, RoundMode roundMode,
            List<Integer> lsPlayers) {
        Map<Integer, List<Card>> hands = new HashMap<>();
        // Integer numCards = lsCards.size();
        Integer numPlayer = lsPlayers.size();
        List<Card> cartasAleatorias = new ArrayList<>(lsCards);
        Collections.shuffle(cartasAleatorias);
        if ("COMPETITIVE" == gameMode.toString()) {
            if ("PIT" == roundMode.toString()) {
                // List<Card> deckCard = cartasAleatorias.subList(0, 1);
                List<Card> deckCard = new ArrayList<>(cartasAleatorias.subList(0, 1));

                hands.put(0, deckCard);
                cartasAleatorias.removeAll(deckCard);
                Integer numCards = cartasAleatorias.size();
                Integer cartasPorJugador = numCards / numPlayer;

                for (Integer i = 0; i < numPlayer; i++) {
                    // List<Card> playerCards = cartasAleatorias.subList(0, cartasPorJugador);
                    List<Card> playerCards = new ArrayList<>(cartasAleatorias.subList(0, cartasPorJugador));

                    Integer idPlayer = lsPlayers.get(i);
                    cartasAleatorias.removeAll(playerCards);
                    hands.put(idPlayer, playerCards);
                }
                return hands;
            } else {
                for (Integer i = 0; i < numPlayer; i++) {
                    List<Card> playerCards = cartasAleatorias.subList(0, 1);
                    Integer idPlayer = lsPlayers.get(i);
                    cartasAleatorias.removeAll(playerCards);
                    hands.put(idPlayer, playerCards);
                }
                List<Card> deckCards = cartasAleatorias;
                hands.put(0, deckCards);
                return hands;
            }
        } else {
            if ("PIT" == roundMode.toString()) {
                List<Card> deckCard = new ArrayList<>(cartasAleatorias.subList(0, 1));
                hands.put(0, deckCard);
                cartasAleatorias.removeAll(deckCard);
                Integer numCards = cartasAleatorias.size();
                int cartasPorJugador = numCards / numPlayer;
                int cartasRestantes = numCards % numPlayer;
                for (Integer i = 0; i < numPlayer; i++) {
                    Integer cartasParaEstePlayer = cartasPorJugador + (cartasRestantes > 0 ? 1 : 0);
                    cartasRestantes = 0;
                    List<Card> playerCards = new ArrayList<>(cartasAleatorias.subList(0, cartasParaEstePlayer));
                    Integer idPlayer = lsPlayers.get(i);
                    cartasAleatorias.removeAll(playerCards);
                    hands.put(idPlayer, playerCards);
                }
                return hands;
            } else {
                for (Integer i = 0; i < numPlayer; i++) {
                    List<Card> playerCards = cartasAleatorias.subList(0, 1);
                    Integer idPlayer = lsPlayers.get(i);
                    cartasAleatorias.removeAll(playerCards);
                    hands.put(idPlayer, playerCards);
                }
                List<Card> deckCards = cartasAleatorias;
                hands.put(0, deckCards);
                return hands;
            }

        }
    }
}
