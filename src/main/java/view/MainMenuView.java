package view;

import javax.swing.*;
import java.awt.*;

/**
 * Main menu view for the card game application.
 */
public class MainMenuView extends JPanel {
    
    private JButton higherLowerButton;
    private JButton higherLowerJokersButton;
    private JButton blackjackButton;
    private JButton rulesButton;
    private JButton exitButton;
    
    public MainMenuView() {
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setOpaque(false);
        
        // Main container
        JPanel mainPanel = StyleUtils.createBackgroundPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Title section
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 40, 0));
        
        // Card symbols decoration
        JLabel decorLabel = new JLabel("♠ ♥ ♣ ♦");
        decorLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 40));
        decorLabel.setForeground(StyleUtils.TEXT_MUTED);
        decorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = StyleUtils.createTitleLabel("CARD GAMES");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = StyleUtils.createSubtitleLabel("Test your luck and skill!");
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(decorLabel);
        titlePanel.add(Box.createVerticalStrut(15));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        
        // Button section
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 60, 0));
        
        higherLowerButton = StyleUtils.createPrimaryButton("Higher / Lower");
        higherLowerButton.setPreferredSize(new Dimension(280, 55));
        higherLowerButton.setMaximumSize(new Dimension(280, 55));
        higherLowerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        higherLowerJokersButton = StyleUtils.createPrimaryButton("Higher / Lower + Jokers");
        higherLowerJokersButton.setPreferredSize(new Dimension(280, 55));
        higherLowerJokersButton.setMaximumSize(new Dimension(280, 55));
        higherLowerJokersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // blackjackButton = StyleUtils.createSecondaryButton("Blackjack");
        // blackjackButton.setPreferredSize(new Dimension(280, 55));
        // blackjackButton.setMaximumSize(new Dimension(280, 55));
        // blackjackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        rulesButton = StyleUtils.createStyledButton("Game Rules", new Color(108, 117, 125));
        rulesButton.setPreferredSize(new Dimension(280, 55));
        rulesButton.setMaximumSize(new Dimension(280, 55));
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        exitButton = StyleUtils.createDangerButton("Exit");
        exitButton.setPreferredSize(new Dimension(280, 55));
        exitButton.setMaximumSize(new Dimension(280, 55));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonPanel.add(higherLowerButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(higherLowerJokersButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(rulesButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(exitButton);
        
        // Center the buttons
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(buttonPanel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        
        // Footer
        JLabel footerLabel = new JLabel("Press a button to start playing");
        footerLabel.setFont(StyleUtils.BODY_FONT);
        footerLabel.setForeground(StyleUtils.TEXT_MUTED);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    // Getters for controllers
    public JButton getHigherLowerButton() { return higherLowerButton; }
    public JButton getHigherLowerJokersButton() { return higherLowerJokersButton; }
    // public JButton getBlackjackButton() { return blackjackButton; }
    public JButton getRulesButton() { return rulesButton; }
    public JButton getExitButton() { return exitButton; }
}

