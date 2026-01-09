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
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
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
        
        contentPanel.add(higherLowerPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        
        // Scroll pane for content
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createRuleSection(String title, String[] rules, Color accentColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dark semi-transparent background
                g2d.setColor(new Color(30, 50, 90, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Border
                g2d.setColor(accentColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Set preferred size to ensure visibility
        panel.setPreferredSize(new Dimension(550, 320));
        panel.setMaximumSize(new Dimension(600, 350));
        panel.setMinimumSize(new Dimension(400, 280));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        
        for (String rule : rules) {
            JLabel ruleLabel = new JLabel(rule);
            ruleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            ruleLabel.setForeground(StyleUtils.TEXT_COLOR);
            ruleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(ruleLabel);
            panel.add(Box.createVerticalStrut(10));
        }
        
        return panel;
    }
    
    public JButton getBackButton() { return backButton; }
}
