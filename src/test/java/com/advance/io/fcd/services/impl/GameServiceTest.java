package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.FcdApplication;
import com.advance.io.fcd.models.RoundResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.CoreMatchers.startsWithIgnoringCase;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = FcdApplication.class)
@ActiveProfiles("test")
public class GameServiceTest {

    GameService gameService;
    PokerService pokerService;
    DealerService dealerService;
    DeckService deckService;

    @BeforeEach
    void setup() {
        pokerService = new PokerService();
        dealerService = new DealerService();
        deckService = new DeckService();

        gameService = new GameService(pokerService,dealerService,deckService);
    }

    @Test
    @DisplayName("all services")
    void all_services_integration() {
        RoundResult roundResult = gameService.play(true);

        assertThat(roundResult.getHand(), startsWithIgnoringCase("your hand:"));
        assertThat(roundResult.getHandStrength(), startsWithIgnoringCase("You have:"));
    }

}
