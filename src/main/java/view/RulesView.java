package view;

import javax.swing.*;
import java.awt.*;

/**
 * View displaying game rules.
 */
public class RulesView extends JPanel {
    
    private JButton backButton;
    
    public RulesView() {
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = StyleUtils.createBackgroundPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));
        
        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        
        backButton = StyleUtils.createStyledButton("← Menu", new Color(108, 117, 125));
        backButton.setPreferredSize(new Dimension(100, 40));
        
        JLabel titleLabel = StyleUtils.createTitleLabel("Game Rules");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 28));
        
        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(Box.createHorizontalStrut(100), BorderLayout.EAST);
        
        // Rules content
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // Higher/Lower rules
        JPanel higherLowerPanel = createRuleSection(
            "HIGHER / LOWER",
            new String[]{
                "• A card is revealed from a shuffled deck",
                "• Guess if the next card will be HIGHER or LOWER",
                "• Card values: 2-10 (face value), J=11, Q=12, K=13, A=14",
                "• Jokers (if enabled) have the highest value (15)",
                "• Correct guess: +1 point, continue playing",
                "• Equal cards: PUSH - continue without scoring",
                "• Wrong guess: Game Over!",
                "• Goal: Achieve the highest score possible!"
            },
            StyleUtils.PRIMARY_LIGHT
        );
        
        // Blackjack rules
        JPanel blackjackPanel = createRuleSection(
            "BLACKJACK",
            new String[]{
                "• You and the dealer each receive 2 cards",
                "• One dealer card remains face-down",
                "• Card values: 2-10 (face value), J/Q/K=10, A=1 or 11",
                "• HIT: Draw another card",
                "• STAND: Keep your current hand",
                "• Going over 21 = BUST (automatic loss)",
                "• Dealer must hit on 16 or less, stand on 17+",
                "• Closest to 21 without busting wins!"
            },
            StyleUtils.SECONDARY_COLOR
        );
        
        contentPanel.add(higherLowerPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(blackjackPanel);
        
        // Scroll pane for content
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(scrollPane);
        
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createRuleSection(String title, String[] rules, Color accentColor) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 2),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMaximumSize(new Dimension(600, 400));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 22));
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));
        
        for (String rule : rules) {
            JLabel ruleLabel = new JLabel(rule);
            ruleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            ruleLabel.setForeground(StyleUtils.TEXT_COLOR);
            ruleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(ruleLabel);
            panel.add(Box.createVerticalStrut(8));
        }
        
        return panel;
    }
    
    public JButton getBackButton() { return backButton; }
}

