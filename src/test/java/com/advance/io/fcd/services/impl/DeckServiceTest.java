package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.FcdApplication;
import com.advance.io.fcd.models.Card;
import com.advance.io.fcd.models.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.CoreMatchers.startsWithIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest(classes = FcdApplication.class)
@ActiveProfiles("test")
public class DeckServiceTest {

    DeckService deckServiceService;

    @BeforeEach
    void setup() {
        deckServiceService = new DeckService();
    }

    @Test
    @DisplayName("DeckService - fetchDeck")
    void given_deckService_when_fetchDeck_expect_valid_deck(){
        Deck deck = deckServiceService.fetchDeck();

        assertThat(deck, notNullValue());
        assertThat(deck.getDeckOfCards().length, equalTo(52));
    }

    @Test
    @DisplayName("DeckService - showHand")
    void given_deckService_when_showHand_expect_valid_Evaluation(){
        Card[] hand = new Card[]{
                new Card(Card.CLUB, Card.JACK),
                new Card(Card.CLUB, 10),
                new Card(Card.CLUB, 9),
                new Card(Card.CLUB, 8),
                new Card(Card.CLUB, 7)
        };
        String handEvaluation = deckServiceService.showHand(hand);

        assertThat(handEvaluation, notNullValue());
        assertThat(handEvaluation, startsWithIgnoringCase("your hand:"));
    }
}
