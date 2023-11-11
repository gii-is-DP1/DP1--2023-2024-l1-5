package org.springframework.samples.petclinic.round;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.game.GameMode;
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

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Transactional
    public Round saveRound(Round round) {
        roundRepository.save(round);
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
        //Integer numCards = lsCards.size();
        Integer numPlayer = lsPlayers.size();

        if ("COMPETITIVE" == gameMode.toString()) {
            if ("PIT" == roundMode.toString()) {

                List<Card> cartasAleatorias = new ArrayList<>(lsCards);
                Collections.shuffle(cartasAleatorias);
                List<Card> deckCard = cartasAleatorias.subList(0, 1);
                hands.put(0, deckCard);
                cartasAleatorias.removeAll(deckCard);
                Integer numCards = cartasAleatorias.size();
                int cartasPorJugador = numCards / numPlayer;

                for (Integer i = 0; i < numPlayer; i++) {
                    List<Card> playerCards = cartasAleatorias.subList(0, cartasPorJugador);
                    Integer idPlayer = lsPlayers.get(i);
                    cartasAleatorias.removeAll(playerCards);
                    hands.put(idPlayer, playerCards);
                }
                return hands;
            } else {
                List<Card> cartasAleatorias = new ArrayList<>(lsCards);
                Collections.shuffle(cartasAleatorias);

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
                List<Card> cartasAleatorias = new ArrayList<>(lsCards);
                Collections.shuffle(cartasAleatorias);
                List<Card> deckCard = cartasAleatorias.subList(0, 1);
                hands.put(0, deckCard);
                cartasAleatorias.removeAll(deckCard);
                 Integer numCards = cartasAleatorias.size();
                int cartasPorJugador = numCards / numPlayer;
                int cartasRestantes = numCards % numPlayer;
                for (Integer i = 0; i < numPlayer; i++) {
                    Integer cartasParaEstePlayer = cartasPorJugador + (cartasRestantes > 0 ? 1 : 0);
                    List<Card> playerCards = cartasAleatorias.subList(0, cartasParaEstePlayer);
                    Integer idPlayer = lsPlayers.get(i);
                    cartasAleatorias.removeAll(playerCards);
                    hands.put(idPlayer, playerCards);
                }
                return hands;
            } else {
                List<Card> cartasAleatorias = new ArrayList<>(lsCards);
                Collections.shuffle(cartasAleatorias);

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

     public Map<Integer, List<Card>> repartir2(List<Card> lsCards, GameMode gameMode, RoundMode roundMode,
            List<Integer> lsPlayers) {
        Map<Integer, List<Card>> hands = new HashMap<>();
        Integer numCards = lsCards.size();
        Integer numPlayers = lsPlayers.size();

        List<Card> cartasAleatorias = new ArrayList<>(lsCards);
        Collections.shuffle(cartasAleatorias);

        BiFunction<Integer, Integer, List<Card>> obtenerMano = (idPlayer, cartasParaEstePlayer) -> {
            List<Card> playerCards = cartasAleatorias.subList(0, cartasParaEstePlayer);
            cartasAleatorias.removeAll(playerCards);
            hands.put(idPlayer, playerCards);
            return playerCards;
        };

        if ("COMPETITIVE" == gameMode.toString() && "PIT" == roundMode.toString()) {
            hands.put(0, obtenerMano.apply(0, 1));
            int cartasPorJugador = numCards / numPlayers;
            for (Integer i = 0; i < numPlayers; i++) {
                obtenerMano.apply(lsPlayers.get(i), cartasPorJugador);
            }
        } else if ("COMPETITIVE" == gameMode.toString()) {
            for (Integer i = 0; i < numPlayers; i++) {
                obtenerMano.apply(lsPlayers.get(i), 1);
            }
            hands.put(0, cartasAleatorias);
        } else if ("PIT" == roundMode.toString()) {
            hands.put(0, obtenerMano.apply(0, 1));
            int cartasPorJugador = numCards / numPlayers;
            int cartasRestantes = numCards % numPlayers;
            for (Integer i = 0; i < numPlayers; i++) {
                Integer cartasParaEstePlayer = cartasPorJugador + (cartasRestantes > 0 ? 1 : 0);
                obtenerMano.apply(lsPlayers.get(i), cartasParaEstePlayer);
            }
        } else {
            for (Integer i = 0; i < numPlayers; i++) {
                obtenerMano.apply(lsPlayers.get(i), 1);
            }
            hands.put(0, cartasAleatorias);
        }

        return hands;
    }
}
