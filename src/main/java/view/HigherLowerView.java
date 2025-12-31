package view;

import model.HigherLowerModel;

import javax.swing.*;
import java.awt.*;

/**
 * View for the Higher/Lower card game.
 */
public class HigherLowerView extends JPanel implements HigherLowerModel.GameListener {
    
    private HigherLowerModel model;
    
    // UI Components
    private CardPanel currentCardPanel;
    private CardPanel previousCardPanel;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private JLabel remainingLabel;
    private JLabel messageLabel;
    private JButton higherButton;
    private JButton lowerButton;
    private JButton newGameButton;
    private JButton backButton;
    private JPanel gameOverPanel;
    
    public HigherLowerView() {
        setupUI();
    }
    
    public void setModel(HigherLowerModel model) {
        if (this.model != null) {
            this.model.removeListener(this);
        }
        this.model = model;
        this.model.addListener(this);
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Background
        JPanel mainPanel = StyleUtils.createBackgroundPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Top bar with title and back button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        
        backButton = StyleUtils.createStyledButton("← Menu", new Color(108, 117, 125));
        backButton.setPreferredSize(new Dimension(100, 40));
        
        JLabel titleLabel = StyleUtils.createTitleLabel("Higher / Lower");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 28));
        
        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(Box.createHorizontalStrut(100), BorderLayout.EAST); // Balance
        
        // Score panel
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        scorePanel.setOpaque(false);
        
        scoreLabel = new JLabel("0");
        highScoreLabel = new JLabel("0");
        remainingLabel = new JLabel("52");
        
        scorePanel.add(StyleUtils.createScorePanel("SCORE", scoreLabel));
        scorePanel.add(StyleUtils.createScorePanel("HIGH SCORE", highScoreLabel));
        scorePanel.add(StyleUtils.createScorePanel("CARDS LEFT", remainingLabel));
        
        // Card display area
        JPanel cardArea = StyleUtils.createFeltPanel();
        cardArea.setLayout(new GridBagLayout());
        cardArea.setPreferredSize(new Dimension(500, 280));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 30, 20, 30);
        
        // Previous card
        JPanel prevCardSection = new JPanel();
        prevCardSection.setOpaque(false);
        prevCardSection.setLayout(new BoxLayout(prevCardSection, BoxLayout.Y_AXIS));
        
        JLabel prevLabel = new JLabel("Previous");
        prevLabel.setFont(StyleUtils.BODY_FONT);
        prevLabel.setForeground(StyleUtils.TEXT_MUTED);
        prevLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        previousCardPanel = new CardPanel();
        previousCardPanel.setPreferredSize(new Dimension(130, 180));
        
        prevCardSection.add(prevLabel);
        prevCardSection.add(Box.createVerticalStrut(10));
        prevCardSection.add(previousCardPanel);
        
        gbc.gridx = 0;
        cardArea.add(prevCardSection, gbc);
        
        // VS label
        JLabel vsLabel = new JLabel("VS");
        vsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        vsLabel.setForeground(StyleUtils.SECONDARY_COLOR);
        gbc.gridx = 1;
        cardArea.add(vsLabel, gbc);
        
        // Current card
        JPanel currCardSection = new JPanel();
        currCardSection.setOpaque(false);
        currCardSection.setLayout(new BoxLayout(currCardSection, BoxLayout.Y_AXIS));
        
        JLabel currLabel = new JLabel("Current");
        currLabel.setFont(StyleUtils.BODY_FONT);
        currLabel.setForeground(StyleUtils.TEXT_MUTED);
        currLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        currentCardPanel = new CardPanel();
        currentCardPanel.setPreferredSize(new Dimension(130, 180));
        
        currCardSection.add(currLabel);
        currCardSection.add(Box.createVerticalStrut(10));
        currCardSection.add(currentCardPanel);
        
        gbc.gridx = 2;
        cardArea.add(currCardSection, gbc);
        
        // Message label
        messageLabel = new JLabel("Will the next card be Higher or Lower?");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(StyleUtils.TEXT_COLOR);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setOpaque(false);
        
        higherButton = StyleUtils.createSuccessButton("↑ HIGHER");
        higherButton.setPreferredSize(new Dimension(150, 55));
        
        lowerButton = StyleUtils.createDangerButton("↓ LOWER");
        lowerButton.setPreferredSize(new Dimension(150, 55));
        
        newGameButton = StyleUtils.createSecondaryButton("New Game");
        newGameButton.setPreferredSize(new Dimension(150, 55));
        
        buttonPanel.add(higherButton);
        buttonPanel.add(lowerButton);
        buttonPanel.add(newGameButton);
        
        // Game over overlay panel
        gameOverPanel = createGameOverPanel();
        gameOverPanel.setVisible(false);
        
        // Layout assembly
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        scorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(scorePanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(cardArea);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(messageLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(buttonPanel);
        
        // Wrap in scroll pane for smaller screens
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(centerPanel);
        
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
        
        // Use layered pane for game over overlay
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gameOverPanel, JLayeredPane.POPUP_LAYER);
        
        add(layeredPane, BorderLayout.CENTER);
    }
    
    private JPanel createGameOverPanel() {
        JPanel overlay = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 180));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlay.setOpaque(false);
        
        JPanel content = new JPanel();
        content.setBackground(new Color(25, 55, 109));
        content.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleUtils.SECONDARY_COLOR, 3),
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        
        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Georgia", Font.BOLD, 32));
        gameOverLabel.setForeground(StyleUtils.ACCENT_COLOR);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        content.add(gameOverLabel);
        
        overlay.add(content);
        return overlay;
    }
    
    @Override
    public void onGameStateChanged() {
        if (model == null) return;
        
        SwingUtilities.invokeLater(() -> {
            // Update cards
            currentCardPanel.setCard(model.getCurrentCard());
            previousCardPanel.setCard(model.getPreviousCard());
            
            // Update scores
            scoreLabel.setText(String.valueOf(model.getScore()));
            highScoreLabel.setText(String.valueOf(model.getHighScore()));
            remainingLabel.setText(String.valueOf(model.getRemainingCards()));
            
            // Update message
            messageLabel.setText(model.getLastMessage());
            
            // Update message color based on result
            if (model.getLastResult() != null) {
                switch (model.getLastResult()) {
                    case CORRECT -> messageLabel.setForeground(StyleUtils.SUCCESS_COLOR);
                    case WRONG -> messageLabel.setForeground(StyleUtils.ACCENT_COLOR);
                    case PUSH -> messageLabel.setForeground(StyleUtils.SECONDARY_COLOR);
                }
            } else {
                messageLabel.setForeground(StyleUtils.TEXT_COLOR);
            }
            
            // Handle game over
            boolean isOver = model.isGameOver();
            higherButton.setEnabled(!isOver);
            lowerButton.setEnabled(!isOver);
            gameOverPanel.setVisible(isOver);
        });
    }
    
    // Getters for controller
    public JButton getHigherButton() { return higherButton; }
    public JButton getLowerButton() { return lowerButton; }
    public JButton getNewGameButton() { return newGameButton; }
    public JButton getBackButton() { return backButton; }
}

