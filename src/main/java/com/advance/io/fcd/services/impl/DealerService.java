package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.exceptions.OutOfCardsException;
import com.advance.io.fcd.models.Card;
import com.advance.io.fcd.models.Deck;
import com.advance.io.fcd.services.IDealerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <h3>Dealer Service</h3>
 * This service implements IDealerService by overriding methods to shuffle a deck and deal cards.
 *
 */
@Service
public class DealerService implements IDealerService {

    private final int NCARDS = 52;

    @Value("${poker.hand-size}")
    private int handSize;

    private int currentCard = 0;

    public DealerService() {
    }

    public void resetCurrentCard() {
        this.currentCard = 0;
    }

    /**
     * This method implements a simple shuffling algorithm by swapping two random cards a thousand times.
     *
     * @param cards Deck of cards.
     * @return Card[] Shuffled deck.
     */
    @Override
    public Card[] shuffle(Card[] cards) {
        int i, j, k;
        int cardCount = cards.length;

        for ( k = 0; k < 1000; k++ ) {
            i = (int) ( cardCount * Math.random() );
            j = (int) ( cardCount * Math.random() );

            Card tmp = cards[i];
            cards[i] = cards[j];
            cards[j] = tmp;
        }

        return cards;
    }

    /**
     * This method deals a predefined number of cards to a single player and
     * throws OutOfCardsException when deck does not have enough cards.
     *
     * @param deck Deck of cards
     * @throws OutOfCardsException OutOfCardsException
     * @return hand Predefined number of cards
     */
    @Override
    public Card[] dealHand(Deck deck) {
        Card[] hand = new Card[handSize];
        for(int i = 0; i< handSize; i++){
            hand[i] = deal(deck);
            if(hand[i] == null){
                return null;
            }
        }
        return hand;
    }

    private Card deal(Deck deck) {
        if ( currentCard < NCARDS ) {
            return ( deck.getDeckOfCards()[ currentCard++ ] );
        } else {
            throw new OutOfCardsException();
        }
    }
}
