package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.models.Card;
import com.advance.io.fcd.models.Deck;
import com.advance.io.fcd.models.RoundResult;
import org.springframework.stereotype.Service;

/**
 * <h3>Game Service</h3>
 * Service to play a new game or continue the current game using the same deck.
 *
 */
@Service
public class GameService {

    PokerService pokerService;
    DealerService dealerService;
    DeckService deckRepository;

    Deck deck;

    public GameService(PokerService pokerService, DealerService dealerService, DeckService deckService) {
        this.pokerService = pokerService;
        this.dealerService = dealerService;
        this.deckRepository = deckService;
        deck = deckService.fetchDeck();
    }

    /**
     * Method to play the game or continue the current game.
     *
     * @param newGame boolean to start a new game or deal from same deck.
     * @return RoundResult
     */
    public RoundResult play(boolean newGame) {

        if (newGame) {
            deck = deckRepository.fetchDeck();
            Card[] ShuffledCards = dealerService.shuffle(deck.getDeckOfCards());
            deck.setDeckOfCards(ShuffledCards);
            dealerService.resetCurrentCard();
        }
        Card[] hand = dealerService.dealHand(deck);

        return new RoundResult(deckRepository.showHand(hand), "You have: " + pokerService.handStrength(hand).getDescription());
    }
}
