package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.FcdApplication;
import com.advance.io.fcd.models.Card;
import com.advance.io.fcd.models.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = FcdApplication.class)
@ActiveProfiles("test")
public class DealerServiceTest {

    DealerService dealerService;
    DeckService deckService;
    Deck deck;

    @Value("${poker.hand-size}")
    private int handSize;

    @BeforeEach
    void setup() {
        dealerService = new DealerService();
        ReflectionTestUtils.setField(dealerService, "handSize", handSize);
        deckService = new DeckService();
        deck = deckService.fetchDeck();
    }

    @Test
    @DisplayName("DealerService - shuffle")
    void given_a_deck_when_shuffled_expect_cards_to_have_shuffled() {

        //deep clone hack (cardsFromDeck & cardsFromDeckNotShuffled contain same card numbers in same order)
        Card[] cardsFromDeck = deck.getDeckOfCards();
        Card[] cardsFromDeckNotShuffled = deckService.fetchDeck().getDeckOfCards();

        Card[] cardsFromDeckShuffled = dealerService.shuffle(cardsFromDeckNotShuffled);

        assertThat(cardsFromDeckShuffled, not(equalTo(cardsFromDeck)));
    }

    @Test
    @DisplayName("DealerService - dealHand")
    void given_a_deck_when_dealt_expect_correct_number_of_cards() {
        Card[] hand = dealerService.dealHand(deck);
        assertThat(hand.length, equalTo(handSize));
    }

}
