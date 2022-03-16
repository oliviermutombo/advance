package com.advance.io.fcd.models;

/**
 * <h3>Round Result</h3>
 * Formats game round results to a friendly output.
 */
public class RoundResult {
    String hand;
    String handStrength;

    public RoundResult(String hand, String handStrength) {
        this.hand = hand;
        this.handStrength = handStrength;
    }

    public String getHand() {
        return hand;
    }

    public String getHandStrength() {
        return handStrength;
    }
}
