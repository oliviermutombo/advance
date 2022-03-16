package com.advance.io.fcd.exceptions;

/**
 * <h3>Out of cards exception</h3>
 * Custom exception class to class to catch out of cards exceptions when deck is empty.
 * Aim is to deal with this exception differently from other exceptions.
 */
public class OutOfCardsException extends RuntimeException{

    /**
     * constructor
     */
    public OutOfCardsException() {
        super("Deck out of cards!");
    }
}
