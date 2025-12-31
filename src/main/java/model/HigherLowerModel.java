package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the Higher/Lower card game.
 * Contains all game state and logic.
 */
public class HigherLowerModel {
    
    public enum Guess { HIGHER, LOWER }
    public enum Result { CORRECT, WRONG, PUSH }
    
    private final Deck deck;
    private final boolean includeJokers;
    private Card currentCard;
    private Card previousCard;
    private int score;
    private int streak;
    private int highScore;
    private boolean gameOver;
    private Result lastResult;
    private String lastMessage;
    
    private final List<GameListener> listeners = new ArrayList<>();
    
    public HigherLowerModel(boolean includeJokers) {
        this.includeJokers = includeJokers;
        this.deck = new Deck(includeJokers);
        this.highScore = 0;
    }
    
    public HigherLowerModel() {
        this(false);
    }
    
    public void startNewGame() {
        deck.resetAndShuffle();
        currentCard = deck.draw();
        previousCard = null;
        score = 0;
        streak = 0;
        gameOver = false;
        lastResult = null;
        lastMessage = "Make your first guess!";
        notifyListeners();
    }
    
    public void makeGuess(Guess guess) {
        if (gameOver) {
            return;
        }
        
        if (deck.isEmpty()) {
            gameOver = true;
            updateHighScore();
            lastMessage = "Amazing! You completed the entire deck!";
            lastResult = Result.CORRECT;
            notifyListeners();
            return;
        }
        
        previousCard = currentCard;
        currentCard = deck.draw();
        
        if (currentCard.isSameValueAs(previousCard)) {
            lastResult = Result.PUSH;
            lastMessage = "PUSH! Cards are equal. Continue without scoring.";
        } else {
            boolean nextIsHigher = currentCard.isHigherThan(previousCard);
            boolean guessedCorrectly = (guess == Guess.HIGHER && nextIsHigher) ||
                                       (guess == Guess.LOWER && !nextIsHigher);
            
            if (guessedCorrectly) {
                lastResult = Result.CORRECT;
                score++;
                streak++;
                lastMessage = getStreakMessage();
            } else {
                lastResult = Result.WRONG;
                gameOver = true;
                updateHighScore();
                lastMessage = "Wrong! Game Over!";
            }
        }
        
        notifyListeners();
    }
    
    private String getStreakMessage() {
        if (streak >= 10) return "INCREDIBLE! " + streak + " in a row!";
        if (streak >= 7) return "AMAZING! " + streak + " in a row!";
        if (streak >= 5) return "Great streak! " + streak + " in a row!";
        if (streak >= 3) return "Nice! " + streak + " in a row!";
        return "Correct! +1 point!";
    }
    
    private void updateHighScore() {
        if (score > highScore) {
            highScore = score;
        }
    }
    
    // Listener pattern for MVC
    public interface GameListener {
        void onGameStateChanged();
    }
    
    public void addListener(GameListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyListeners() {
        for (GameListener listener : listeners) {
            listener.onGameStateChanged();
        }
    }
    
    // Getters
    public Card getCurrentCard() { return currentCard; }
    public Card getPreviousCard() { return previousCard; }
    public int getScore() { return score; }
    public int getStreak() { return streak; }
    public int getHighScore() { return highScore; }
    public boolean isGameOver() { return gameOver; }
    public int getRemainingCards() { return deck.remainingCards(); }
    public boolean hasJokers() { return includeJokers; }
    public Result getLastResult() { return lastResult; }
    public String getLastMessage() { return lastMessage; }
}

