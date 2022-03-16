package com.advance.io.fcd.models;

/**
 * <h3>Deck model</h3>
 * Deck object class. Contains cards
 *
 */
public class Deck {
    private Card[] deckOfCards;

    public Card[] getDeckOfCards() {
        return deckOfCards;
    }

    public void setDeckOfCards(Card[] deckOfCards) {
        this.deckOfCards = deckOfCards;
    }
}
