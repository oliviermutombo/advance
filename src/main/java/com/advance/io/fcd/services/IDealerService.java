package com.advance.io.fcd.services;

import com.advance.io.fcd.models.Card;
import com.advance.io.fcd.models.Deck;

public interface IDealerService {

    Card[] shuffle(Card[] cards);

    Card[] dealHand(Deck deck);
}
