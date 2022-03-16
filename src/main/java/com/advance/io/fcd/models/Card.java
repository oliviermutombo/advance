package com.advance.io.fcd.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <h3>Card model</h3>
 * Card object class. Sets cards ranks and suits.
 */
public class Card {
    public static final int SPADE = 4;
    public static final int HEART = 3;
    public static final int CLUB = 2;
    public static final int DIAMOND = 1;

    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int ACE = 14;

    // * , Diamonds , Club , Heart , Spade
    private static String[] Suit = {"*", String.valueOf('\u2666'), String.valueOf('\u2663'), String.valueOf('\u2764'), String.valueOf('\u2660')};

    private static final String[] Rank = {"*", "*", "2", "3", "4","5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private byte cardSuit;
    private byte cardRank;

    public Card(int suit, int rank) {
        if (rank == 1)
            cardRank = 14;
        else
            cardRank = (byte) rank;

        cardSuit = (byte) suit;
    }

    public int suit() {
        return (cardSuit);
    }

    public String suitStr() {
        return (Suit[cardSuit]);
    }

    public int rank() {
        return (cardRank);
    }

    public String toString(boolean useSymbol) {
        if(!useSymbol) {
            Suit = new String[]{"*", "D", "C", "H", "S"};
        }
        return (Rank[cardRank] + Suit[cardSuit]);
    }
}
