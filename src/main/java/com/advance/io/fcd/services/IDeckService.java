package com.advance.io.fcd.services;

import com.advance.io.fcd.models.Card;
import com.advance.io.fcd.models.Deck;

public interface IDeckService {

    Deck fetchDeck();

    String showHand(Card[] hand);
}
