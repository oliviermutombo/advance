package com.advance.io.fcd.enums;

/**
 * <h3>Enum listing standard poker hand strength</h3>
 */
public enum HandStrength {
    HIGH_CARD("High cards"),
    ONE_PAIR("One pair"),
    TWO_PAIR("Two pair"),
    THREE_OF_A_KIND("Three of a kind"),
    STRAIGHT("Straight"),
    FLUSH("Flush"),
    FULL_HOUSE("Full house"),
    FOUR_OF_A_KIND("Four of a kind"),
    STRAIGHT_FLUSH("Straight flush");

    private String description;

    private HandStrength(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
