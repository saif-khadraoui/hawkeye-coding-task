package view;

import model.BlackjackModel;
import model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * View for the Blackjack card game.
 */
public class BlackjackView extends JPanel implements BlackjackModel.GameListener {
    
    private BlackjackModel model;
    
    // UI Components
    private JPanel dealerCardsPanel;
    private JPanel playerCardsPanel;
    private JLabel dealerValueLabel;
    private JLabel playerValueLabel;
    private JLabel messageLabel;
    private JButton hitButton;
    private JButton standButton;
    private JButton newGameButton;
    private JButton backButton;
    private JPanel resultOverlay;
    private JLabel resultLabel;
    
    public BlackjackView() {
        setupUI();
    }
    
    public void setModel(BlackjackModel model) {
        this.model = model;
        this.model.addListener(this);
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Background
        JPanel mainPanel = StyleUtils.createBackgroundPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        
        backButton = StyleUtils.createStyledButton("← Menu", new Color(108, 117, 125));
        backButton.setPreferredSize(new Dimension(100, 40));
        
        JLabel titleLabel = StyleUtils.createTitleLabel("Blackjack");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 28));
        
        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(Box.createHorizontalStrut(100), BorderLayout.EAST);
        
        // Game area
        JPanel gameArea = StyleUtils.createFeltPanel();
        gameArea.setLayout(new BoxLayout(gameArea, BoxLayout.Y_AXIS));
        gameArea.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Dealer section
        JPanel dealerSection = new JPanel();
        dealerSection.setOpaque(false);
        dealerSection.setLayout(new BoxLayout(dealerSection, BoxLayout.Y_AXIS));
        
        JPanel dealerHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dealerHeader.setOpaque(false);
        JLabel dealerLabel = new JLabel("DEALER");
        dealerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dealerLabel.setForeground(StyleUtils.TEXT_COLOR);
        dealerValueLabel = new JLabel("");
        dealerValueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dealerValueLabel.setForeground(StyleUtils.SECONDARY_COLOR);
        dealerHeader.add(dealerLabel);
        dealerHeader.add(Box.createHorizontalStrut(20));
        dealerHeader.add(dealerValueLabel);
        
        dealerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, -30, 0));
        dealerCardsPanel.setOpaque(false);
        dealerCardsPanel.setPreferredSize(new Dimension(500, 190));
        
        dealerSection.add(dealerHeader);
        dealerSection.add(Box.createVerticalStrut(10));
        dealerSection.add(dealerCardsPanel);
        dealerSection.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Divider
        JLabel divider = new JLabel("─────────────────────────────────");
        divider.setForeground(new Color(255, 255, 255, 50));
        divider.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Player section
        JPanel playerSection = new JPanel();
        playerSection.setOpaque(false);
        playerSection.setLayout(new BoxLayout(playerSection, BoxLayout.Y_AXIS));
        
        JPanel playerHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerHeader.setOpaque(false);
        JLabel playerLabel = new JLabel("YOUR HAND");
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerLabel.setForeground(StyleUtils.TEXT_COLOR);
        playerValueLabel = new JLabel("");
        playerValueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerValueLabel.setForeground(StyleUtils.SECONDARY_COLOR);
        playerHeader.add(playerLabel);
        playerHeader.add(Box.createHorizontalStrut(20));
        playerHeader.add(playerValueLabel);
        
        playerCardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, -30, 0));
        playerCardsPanel.setOpaque(false);
        playerCardsPanel.setPreferredSize(new Dimension(500, 190));
        
        playerSection.add(playerCardsPanel);
        playerSection.add(Box.createVerticalStrut(10));
        playerSection.add(playerHeader);
        playerSection.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gameArea.add(dealerSection);
        gameArea.add(Box.createVerticalStrut(20));
        gameArea.add(divider);
        gameArea.add(Box.createVerticalStrut(20));
        gameArea.add(playerSection);
        
        // Message
        messageLabel = new JLabel("Hit or Stand?");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        messageLabel.setForeground(StyleUtils.TEXT_COLOR);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        
        hitButton = StyleUtils.createSuccessButton("HIT");
        hitButton.setPreferredSize(new Dimension(130, 55));
        
        standButton = StyleUtils.createDangerButton("STAND");
        standButton.setPreferredSize(new Dimension(130, 55));
        
        newGameButton = StyleUtils.createSecondaryButton("New Game");
        newGameButton.setPreferredSize(new Dimension(130, 55));
        
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(newGameButton);
        
        // Result overlay
        resultOverlay = createResultOverlay();
        resultOverlay.setVisible(false);
        
        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        gameArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(gameArea);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(messageLabel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(buttonPanel);
        
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(centerPanel);
        
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
        
        // Layered pane for overlay
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(resultOverlay, JLayeredPane.POPUP_LAYER);
        
        add(layeredPane, BorderLayout.CENTER);
    }
    
    private JPanel createResultOverlay() {
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
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        
        resultLabel = new JLabel("RESULT");
        resultLabel.setFont(new Font("Georgia", Font.BOLD, 28));
        resultLabel.setForeground(StyleUtils.SECONDARY_COLOR);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        content.add(resultLabel);
        overlay.add(content);
        return overlay;
    }
    
    private void updateCards() {
        dealerCardsPanel.removeAll();
        playerCardsPanel.removeAll();
        
        if (model == null) return;
        
        // Dealer cards
        List<Card> dealerHand = model.getDealerHand();
        boolean showAll = model.isGameOver() || model.hasPlayerStood();
        
        for (int i = 0; i < dealerHand.size(); i++) {
            CardPanel cardPanel = new CardPanel(dealerHand.get(i), !showAll && i == 1);
            cardPanel.setPreferredSize(new Dimension(100, 150));
            dealerCardsPanel.add(cardPanel);
        }
        
        // Player cards
        for (Card card : model.getPlayerHand()) {
            CardPanel cardPanel = new CardPanel(card, false);
            cardPanel.setPreferredSize(new Dimension(100, 150));
            playerCardsPanel.add(cardPanel);
        }
        
        dealerCardsPanel.revalidate();
        dealerCardsPanel.repaint();
        playerCardsPanel.revalidate();
        playerCardsPanel.repaint();
    }
    
    @Override
    public void onGameStateChanged() {
        if (model == null) return;
        
        SwingUtilities.invokeLater(() -> {
            updateCards();
            
            // Update values
            if (model.isGameOver() || model.hasPlayerStood()) {
                dealerValueLabel.setText("(" + model.getDealerValue() + ")");
            } else {
                dealerValueLabel.setText("(?)");
            }
            playerValueLabel.setText("(" + model.getPlayerValue() + ")");
            
            // Update buttons and message
            boolean isOver = model.isGameOver();
            hitButton.setEnabled(!isOver);
            standButton.setEnabled(!isOver);
            
            if (isOver) {
                messageLabel.setText(model.getResultMessage());
                resultLabel.setText(model.getResultMessage());
                
                switch (model.getResult()) {
                    case WIN -> {
                        messageLabel.setForeground(StyleUtils.SUCCESS_COLOR);
                        resultLabel.setForeground(StyleUtils.SUCCESS_COLOR);
                    }
                    case LOSE -> {
                        messageLabel.setForeground(StyleUtils.ACCENT_COLOR);
                        resultLabel.setForeground(StyleUtils.ACCENT_COLOR);
                    }
                    case PUSH -> {
                        messageLabel.setForeground(StyleUtils.SECONDARY_COLOR);
                        resultLabel.setForeground(StyleUtils.SECONDARY_COLOR);
                    }
                    default -> messageLabel.setForeground(StyleUtils.TEXT_COLOR);
                }
                
                resultOverlay.setVisible(true);
            } else {
                messageLabel.setText("Hit or Stand?");
                messageLabel.setForeground(StyleUtils.TEXT_COLOR);
                resultOverlay.setVisible(false);
            }
        });
    }
    
    // Getters for controller
    public JButton getHitButton() { return hitButton; }
    public JButton getStandButton() { return standButton; }
    public JButton getNewGameButton() { return newGameButton; }
    public JButton getBackButton() { return backButton; }
}

