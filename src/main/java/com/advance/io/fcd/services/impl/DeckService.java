package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.models.Card;
import com.advance.io.fcd.models.Deck;
import com.advance.io.fcd.services.IDeckService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <h3>Deck Service</h3>
 * This service implements IDeckService by overriding methods to fetch a fresh deck and
 * read cards on the player's hand
 *
 */
@Service
public class DeckService implements IDeckService {

    private final int NCARDS = 52;

    @Value("${poker.hand-size}")
    private int HAND_SIZE;

    @Value("${poker.show-suit-symbol}")
    private boolean useSuitSymbol;

    public DeckService() {}

    /**
     * This method initialises a fresh deck of cards ordered by rank.
     *
     * @return Deck
     */
    @Override
    public Deck fetchDeck() {
        Deck deck = new Deck();
        Card[] cards = new Card[ NCARDS ];
        int i = 0;

        for ( int suit = Card.DIAMOND; suit <= Card.SPADE; suit++ ) {
            for (int rank = 1; rank <= 13; rank++) {
                cards[i++] = new Card(suit, rank);
            }
        }

        deck.setDeckOfCards(cards);

        return deck;
    }

    /**
     * This method displays the cards the player has in hand
     *
     * @param hand predefined number of cards in hand.
     * @return description of cards.
     */
    @Override
    public String showHand(Card[] hand){
        StringBuffer buffer = new StringBuffer();
        for ( int i = 0; i < HAND_SIZE; i++ ) {
            buffer.append(hand[i].toString(useSuitSymbol)).append(" ");
        }
        return "Your Hand: " + buffer;
    }
    /*public String showHand(Card[] hand){
        StringBuffer buffer = new StringBuffer();
        for ( int i = 0; i < HAND_SIZE; i++ ) {
            buffer.append(hand[i]).append(" ");
        }
        return "Your Hand: " + buffer.toString();
    }*/
}
