package controller;

import model.BlackjackModel;
import model.HigherLowerModel;
import view.*;

import javax.swing.*;
import java.awt.*;

/**
 * Main controller that manages navigation between different game views.
 * Implements the MVC pattern by coordinating models, views, and sub-controllers.
 */
public class MainController {
    
    private final JFrame mainFrame;
    private final CardLayout cardLayout;
    private final JPanel containerPanel;
    
    // Views
    private final MainMenuView mainMenuView;
    private final HigherLowerView higherLowerView;
    private final HigherLowerView higherLowerJokersView;
    private final BlackjackView blackjackView;
    private final RulesView rulesView;
    
    // Controllers
    private HigherLowerController higherLowerController;
    private HigherLowerController higherLowerJokersController;
    // private BlackjackController blackjackController;
    
    // View names for CardLayout
    private static final String MENU_VIEW = "menu";
    private static final String HIGHER_LOWER_VIEW = "higherLower";
    private static final String HIGHER_LOWER_JOKERS_VIEW = "higherLowerJokers";
    private static final String BLACKJACK_VIEW = "blackjack";
    private static final String RULES_VIEW = "rules";
    
    public MainController() {
        // Initialize main frame
        mainFrame = new JFrame("Card Games");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 700);
        mainFrame.setMinimumSize(new Dimension(700, 600));
        mainFrame.setLocationRelativeTo(null);
        
        // Setup card layout for view switching
        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);
        
        // Initialize views
        mainMenuView = new MainMenuView();
        higherLowerView = new HigherLowerView();
        higherLowerJokersView = new HigherLowerView();
        blackjackView = new BlackjackView();
        rulesView = new RulesView();
        
        // Add views to container
        containerPanel.add(mainMenuView, MENU_VIEW);
        containerPanel.add(higherLowerView, HIGHER_LOWER_VIEW);
        containerPanel.add(higherLowerJokersView, HIGHER_LOWER_JOKERS_VIEW);
        containerPanel.add(blackjackView, BLACKJACK_VIEW);
        containerPanel.add(rulesView, RULES_VIEW);
        
        mainFrame.add(containerPanel);
        
        // Setup controllers and navigation
        setupControllers();
        setupMenuListeners();
    }
    
    private void setupControllers() {
        // Higher/Lower without jokers
        HigherLowerModel higherLowerModel = new HigherLowerModel(false);
        higherLowerController = new HigherLowerController(
            higherLowerModel, 
            higherLowerView, 
            this::showMenu
        );
        
        // Higher/Lower with jokers
        HigherLowerModel higherLowerJokersModel = new HigherLowerModel(true);
        higherLowerJokersController = new HigherLowerController(
            higherLowerJokersModel, 
            higherLowerJokersView, 
            this::showMenu
        );
        
    
    }
    
    private void setupMenuListeners() {
        // Higher/Lower button
        mainMenuView.getHigherLowerButton().addActionListener(e -> {
            higherLowerController.startGame();
            cardLayout.show(containerPanel, HIGHER_LOWER_VIEW);
        });
        
        // Higher/Lower with Jokers button
        mainMenuView.getHigherLowerJokersButton().addActionListener(e -> {
            higherLowerJokersController.startGame();
            cardLayout.show(containerPanel, HIGHER_LOWER_JOKERS_VIEW);
        });
        
        // Rules button
        mainMenuView.getRulesButton().addActionListener(e -> {
            cardLayout.show(containerPanel, RULES_VIEW);
        });
        
        // Rules back button
        rulesView.getBackButton().addActionListener(e -> {
            showMenu();
        });
        
        // Exit button
        mainMenuView.getExitButton().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                mainFrame,
                "Are you sure you want to exit?",
                "Exit Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
    
    private void showMenu() {
        cardLayout.show(containerPanel, MENU_VIEW);
    }
    
    public void start() {
        SwingUtilities.invokeLater(() -> {
            mainFrame.setVisible(true);
        });
    }
}

