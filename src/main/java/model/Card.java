package model;

import java.util.Objects;

/**
 * Represents a playing card with a rank and suit.
 * Cards are immutable and can be compared by their rank values.
 */
public class Card implements Comparable<Card> {
    private final Rank rank;
    private final Suit suit;
    private final boolean isJoker;

    /**
     * Creates a standard playing card with the given rank and suit.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = Objects.requireNonNull(rank, "Rank cannot be null");
        this.suit = Objects.requireNonNull(suit, "Suit cannot be null");
        this.isJoker = false;
    }

    /**
     * Private constructor for creating Joker cards.
     */
    private Card(boolean isJoker) {
        this.rank = null;
        this.suit = null;
        this.isJoker = isJoker;
    }

    /**
     * Creates a Joker card.
     */
    public static Card createJoker() {
        return new Card(true);
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isJoker() {
        return isJoker;
    }

    /**
     * Gets the numeric value of the card for comparison.
     * Jokers have the highest value (15).
     */
    public int getValue() {
        if (isJoker) {
            return 15;
        }
        return rank.getValue();
    }

    /**
     * Compares this card to another card by value.
     */
    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.getValue(), other.getValue());
    }

    public boolean isHigherThan(Card other) {
        return this.compareTo(other) > 0;
    }

    public boolean isLowerThan(Card other) {
        return this.compareTo(other) < 0;
    }

    public boolean isSameValueAs(Card other) {
        return this.compareTo(other) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        if (isJoker && card.isJoker) return true;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        if (isJoker) return Objects.hash(true);
        return Objects.hash(rank, suit);
    }

    @Override
    public String toString() {
        if (isJoker) {
            return "JOKER";
        }
        return rank.getSymbol() + suit.getSymbol();
    }

    public String toDetailedString() {
        if (isJoker) {
            return "Joker";
        }
        return rank.getSymbol() + " of " + suit.getDisplayName();
    }
}

