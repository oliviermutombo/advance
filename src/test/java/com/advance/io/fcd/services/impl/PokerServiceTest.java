package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.FcdApplication;
import com.advance.io.fcd.enums.HandStrength;
import com.advance.io.fcd.models.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = FcdApplication.class)
@ActiveProfiles("test")
public class PokerServiceTest {

    PokerService pokerService;

    @BeforeEach
    void setup() {
        pokerService = new PokerService();
    }

    @Test
    @DisplayName("PokerService - Detect Straight Flush")
    void pokerServiceDetectStraightFlush() {
        Card[] hand = new Card[] {
                new Card(Card.SPADE, Card.KING),
                new Card(Card.SPADE, Card.QUEEN),
                new Card(Card.SPADE, Card.JACK),
                new Card(Card.SPADE, 10),
                new Card(Card.SPADE, 9)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.STRAIGHT_FLUSH));
    }

    @Test
    @DisplayName("PokerService - Detect Four of a Kind")
    void pokerServiceDetectDetectFourOfAKind() {
        Card[] hand = new Card[] {
                new Card(Card.SPADE, 5),
                new Card(Card.SPADE, Card.QUEEN),
                new Card(Card.DIAMOND, 5),
                new Card(Card.CLUB, 5),
                new Card(Card.HEART, 5)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.FOUR_OF_A_KIND));
    }

    @Test
    @DisplayName("PokerService - Detect Full House")
    void pokerServiceDetectDetectFullHouse() {
        Card[] hand = new Card[] {
                new Card(Card.SPADE, 5),
                new Card(Card.SPADE, Card.QUEEN),
                new Card(Card.DIAMOND, 5),
                new Card(Card.CLUB, 5),
                new Card(Card.HEART, Card.QUEEN)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.FULL_HOUSE));
    }

    @Test
    @DisplayName("PokerService - Detect Flush")
    void pokerServiceDetectDetectFlush() {
        Card[] hand = new Card[] {
                new Card(Card.HEART, 2),
                new Card(Card.HEART, Card.QUEEN),
                new Card(Card.HEART, 7),
                new Card(Card.HEART, 5),
                new Card(Card.HEART, Card.JACK)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.FLUSH));
    }

    @Test
    @DisplayName("PokerService - Detect Straight")
    void pokerServiceDetectDetectStraight() {
        Card[] hand = new Card[] {
                new Card(Card.HEART, 7),
                new Card(Card.CLUB, 6),
                new Card(Card.CLUB, 5),
                new Card(Card.DIAMOND, 4),
                new Card(Card.DIAMOND, 3)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.STRAIGHT));
    }

    @Test
    @DisplayName("PokerService - Detect Three of a kind")
    void pokerServiceDetectDetectThreeOfAkind() {
        Card[] hand = new Card[] {
                new Card(Card.HEART, 7),
                new Card(Card.CLUB, 6),
                new Card(Card.CLUB, 5),
                new Card(Card.DIAMOND, 5),
                new Card(Card.HEART, 5)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.THREE_OF_A_KIND));
    }

    @Test
    @DisplayName("PokerService - Detect Two pair")
    void pokerServiceDetectDetectTwoPair() {
        Card[] hand = new Card[] {
                new Card(Card.HEART, Card.QUEEN),
                new Card(Card.CLUB, 3),
                new Card(Card.SPADE, 3),
                new Card(Card.DIAMOND, 5),
                new Card(Card.HEART, 5)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.TWO_PAIR));
    }

    @Test
    @DisplayName("PokerService - Detect One pair")
    void pokerServiceDetectDetectOnePair() {
        Card[] hand = new Card[] {
                new Card(Card.HEART, Card.QUEEN),
                new Card(Card.CLUB, 2),
                new Card(Card.SPADE, 8),
                new Card(Card.DIAMOND, 5),
                new Card(Card.HEART, 5)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.ONE_PAIR));
    }

    @Test
    @DisplayName("PokerService - Detect High card")
    void pokerServiceDetectDetectHighCard() {
        Card[] hand = new Card[] {
                new Card(Card.SPADE, 4),
                new Card(Card.CLUB, 8),
                new Card(Card.DIAMOND, 7),
                new Card(Card.HEART, Card.JACK),
                new Card(Card.HEART, Card.KING)
        };
        assertThat(pokerService.handStrength(hand), equalTo(HandStrength.HIGH_CARD));
    }
}
