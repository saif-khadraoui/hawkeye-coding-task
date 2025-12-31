package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a deck of playing cards.
 * Supports standard 52-card deck with optional Jokers.
 */
public class Deck {
    private final List<Card> cards;
    private int currentIndex;
    private final Random random;
    private final boolean hasJokers;

    public Deck() {
        this(false);
    }

    public Deck(boolean includeJokers) {
        this.cards = new ArrayList<>();
        this.random = new Random();
        this.currentIndex = 0;
        this.hasJokers = includeJokers;
        initializeDeck();
    }

    private void initializeDeck() {
        cards.clear();
        
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        
        if (hasJokers) {
            cards.add(Card.createJoker());
            cards.add(Card.createJoker());
        }
        
        currentIndex = 0;
    }

    /**
     * Shuffles the deck using Fisher-Yates algorithm.
     */
    public void shuffle() {
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
        currentIndex = 0;
    }

    public Card draw() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(currentIndex++);
    }

    public Card peek() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(currentIndex);
    }

    public boolean isEmpty() {
        return currentIndex >= cards.size();
    }

    public int remainingCards() {
        return cards.size() - currentIndex;
    }

    public int totalCards() {
        return cards.size();
    }

    public void reset() {
        initializeDeck();
    }

    public void resetAndShuffle() {
        reset();
        shuffle();
    }

    public boolean hasJokers() {
        return hasJokers;
    }
}

