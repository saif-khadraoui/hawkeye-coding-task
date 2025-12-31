package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the Simple Blackjack card game.
 * Contains all game state and logic.
 */
public class BlackjackModel {
    
    public enum GameResult { WIN, LOSE, PUSH, IN_PROGRESS }
    
    private final Deck deck;
    private final List<Card> playerHand;
    private final List<Card> dealerHand;
    private boolean gameOver;
    private boolean playerBusted;
    private boolean dealerBusted;
    private boolean playerStood;
    private GameResult result;
    private String resultMessage;
    
    private final List<GameListener> listeners = new ArrayList<>();
    
    public BlackjackModel() {
        this.deck = new Deck(false);
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
    }
    
    public void startNewGame() {
        deck.resetAndShuffle();
        playerHand.clear();
        dealerHand.clear();
        gameOver = false;
        playerBusted = false;
        dealerBusted = false;
        playerStood = false;
        result = GameResult.IN_PROGRESS;
        resultMessage = "";
        
        // Deal initial cards
        playerHand.add(deck.draw());
        dealerHand.add(deck.draw());
        playerHand.add(deck.draw());
        dealerHand.add(deck.draw());
        
        // Check for natural blackjack
        if (getPlayerValue() == 21) {
            stand();
        }
        
        notifyListeners();
    }
    
    public void hit() {
        if (gameOver || playerStood) {
            return;
        }
        
        playerHand.add(deck.draw());
        
        if (getPlayerValue() > 21) {
            playerBusted = true;
            gameOver = true;
            result = GameResult.LOSE;
            resultMessage = "BUST! You went over 21!";
        } else if (getPlayerValue() == 21) {
            stand();
            return;
        }
        
        notifyListeners();
    }
    
    public void stand() {
        if (gameOver) {
            return;
        }
        
        playerStood = true;
        
        // Dealer's turn - hit on 16 or less
        while (getDealerValue() < 17) {
            dealerHand.add(deck.draw());
        }
        
        if (getDealerValue() > 21) {
            dealerBusted = true;
            result = GameResult.WIN;
            resultMessage = "Dealer BUSTS! You win!";
        } else {
            determineWinner();
        }
        
        gameOver = true;
        notifyListeners();
    }
    
    private void determineWinner() {
        int playerValue = getPlayerValue();
        int dealerValue = getDealerValue();
        
        if (playerValue > dealerValue) {
            result = GameResult.WIN;
            resultMessage = "YOU WIN! " + playerValue + " beats " + dealerValue + "!";
        } else if (dealerValue > playerValue) {
            result = GameResult.LOSE;
            resultMessage = "Dealer wins. " + dealerValue + " beats " + playerValue + ".";
        } else {
            result = GameResult.PUSH;
            resultMessage = "PUSH! It's a tie at " + playerValue + ".";
        }
    }
    
    public int getHandValue(List<Card> hand) {
        int value = 0;
        int aces = 0;
        
        for (Card card : hand) {
            Rank rank = card.getRank();
            if (rank == Rank.ACE) {
                aces++;
                value += 11;
            } else if (rank == Rank.JACK || rank == Rank.QUEEN || rank == Rank.KING) {
                value += 10;
            } else {
                value += rank.getValue();
            }
        }
        
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        
        return value;
    }
    
    // Listener pattern for MVC
    public interface GameListener {
        void onGameStateChanged();
    }
    
    public void addListener(GameListener listener) {
        listeners.add(listener);
    }
    
    private void notifyListeners() {
        for (GameListener listener : listeners) {
            listener.onGameStateChanged();
        }
    }
    
    // Getters
    public List<Card> getPlayerHand() { return new ArrayList<>(playerHand); }
    public List<Card> getDealerHand() { return new ArrayList<>(dealerHand); }
    public int getPlayerValue() { return getHandValue(playerHand); }
    public int getDealerValue() { return getHandValue(dealerHand); }
    public boolean isGameOver() { return gameOver; }
    public boolean isPlayerBusted() { return playerBusted; }
    public boolean isDealerBusted() { return dealerBusted; }
    public boolean hasPlayerStood() { return playerStood; }
    public GameResult getResult() { return result; }
    public String getResultMessage() { return resultMessage; }
}

