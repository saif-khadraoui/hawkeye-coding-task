package model;

import java.awt.Color;

/**
 * Represents the four suits in a standard deck of playing cards.
 */
public enum Suit {
    HEARTS("♥", "Hearts", new Color(220, 53, 69)),
    DIAMONDS("♦", "Diamonds", new Color(220, 53, 69)),
    CLUBS("♣", "Clubs", new Color(33, 37, 41)),
    SPADES("♠", "Spades", new Color(33, 37, 41));

    private final String symbol;
    private final String displayName;
    private final Color color;

    Suit(String symbol, String displayName, Color color) {
        this.symbol = symbol;
        this.displayName = displayName;
        this.color = color;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

