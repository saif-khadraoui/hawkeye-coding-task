package controller;

import model.HigherLowerModel;
import view.HigherLowerView;

/**
 * Controller for the Higher/Lower game.
 * Connects the model and view, handling user interactions.
 */
public class HigherLowerController {
    
    private final HigherLowerModel model;
    private final HigherLowerView view;
    private final Runnable onBackToMenu;
    
    public HigherLowerController(HigherLowerModel model, HigherLowerView view, Runnable onBackToMenu) {
        this.model = model;
        this.view = view;
        this.onBackToMenu = onBackToMenu;
        
        view.setModel(model);
        setupListeners();
    }
    
    private void setupListeners() {
        // Higher button
        view.getHigherButton().addActionListener(e -> {
            model.makeGuess(HigherLowerModel.Guess.HIGHER);
        });
        
        // Lower button
        view.getLowerButton().addActionListener(e -> {
            model.makeGuess(HigherLowerModel.Guess.LOWER);
        });
        
        // New game button
        view.getNewGameButton().addActionListener(e -> {
            model.startNewGame();
        });
        
        // Back button
        view.getBackButton().addActionListener(e -> {
            onBackToMenu.run();
        });
    }
    
    public void startGame() {
        model.startNewGame();
    }
    
    public HigherLowerView getView() {
        return view;
    }
}

