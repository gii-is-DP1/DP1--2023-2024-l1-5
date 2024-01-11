package org.springframework.samples.petclinic.card;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.symbol.Name;
import org.springframework.samples.petclinic.symbol.Symbol;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CardService.class))
public class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Test
    public void testUniqueSymbolInEveryCardPair() {
        List<Card> allCards = cardService.getAllCards();
        for (int i = 0; i < allCards.size(); i++) {
            for (int j = i + 1; j < allCards.size(); j++) {
                Card card1 = allCards.get(i);
                Card card2 = allCards.get(j);
                List<Symbol> symbolsCard1 = card1.getSymbols();
                List<Symbol> symbolsCard2 = card2.getSymbols();
                Set<Name> commonSymbols = symbolsCard1.stream()
                    .map(Symbol::getName)
                    .distinct()
                    .filter(symbolName -> symbolsCard2.stream().anyMatch(s -> s.getName() == symbolName))
                    .collect(Collectors.toSet());

                assertEquals(1, commonSymbols.size(), "Las cartas " + card1.getId() + " y " + card2.getId() + " deben tener exactamente un símbolo en común");
            }
        }
    }

    @Test
    public void testEachCardHasSixSymbols() {
        List<Card> allCards = cardService.getAllCards(); 
        for (Card card : allCards) {
            List<Symbol> symbols = card.getSymbols();
            assertEquals(6, symbols.size(), "La carta con ID " + card.getId() + " debe tener exactamente 6 símbolos");
        }
    }

}
