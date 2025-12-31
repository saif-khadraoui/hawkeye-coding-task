package view;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for consistent styling across the application.
 */
public class StyleUtils {
    
    // Color scheme - Deep blue casino theme
    public static final Color PRIMARY_COLOR = new Color(25, 55, 109);
    public static final Color PRIMARY_LIGHT = new Color(45, 85, 149);
    public static final Color SECONDARY_COLOR = new Color(218, 165, 32); // Gold
    public static final Color ACCENT_COLOR = new Color(220, 53, 69); // Red
    public static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    public static final Color BACKGROUND_COLOR = new Color(15, 35, 75);
    public static final Color BACKGROUND_LIGHT = new Color(25, 55, 109);
    public static final Color TEXT_COLOR = new Color(255, 255, 255);
    public static final Color TEXT_MUTED = new Color(180, 190, 210);
    public static final Color FELT_GREEN = new Color(34, 87, 52);
    
    // Fonts
    public static final Font TITLE_FONT = new Font("Georgia", Font.BOLD, 36);
    public static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font BODY_FONT = new Font("Arial", Font.PLAIN, 16);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font SCORE_FONT = new Font("Consolas", Font.BOLD, 24);
    
    /**
     * Creates a styled button with gradient effect.
     */
    public static JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color topColor = baseColor.brighter();
                Color bottomColor = baseColor.darker();
                
                if (getModel().isPressed()) {
                    topColor = baseColor.darker();
                    bottomColor = baseColor;
                } else if (getModel().isRollover()) {
                    topColor = topColor.brighter();
                    bottomColor = baseColor;
                }
                
                GradientPaint gradient = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 50));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                
                g2d.dispose();
                
                // Draw text
                FontMetrics fm = g.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g.setColor(TEXT_COLOR);
                g.drawString(getText(), x, y);
            }
        };
        
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setPreferredSize(new Dimension(200, 50));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    /**
     * Creates a primary action button.
     */
    public static JButton createPrimaryButton(String text) {
        return createStyledButton(text, PRIMARY_LIGHT);
    }
    
    /**
     * Creates a secondary (gold) button.
     */
    public static JButton createSecondaryButton(String text) {
        return createStyledButton(text, SECONDARY_COLOR);
    }
    
    /**
     * Creates a success (green) button.
     */
    public static JButton createSuccessButton(String text) {
        return createStyledButton(text, SUCCESS_COLOR);
    }
    
    /**
     * Creates a danger (red) button.
     */
    public static JButton createDangerButton(String text) {
        return createStyledButton(text, ACCENT_COLOR);
    }
    
    /**
     * Creates a styled label.
     */
    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    /**
     * Creates a title label.
     */
    public static JLabel createTitleLabel(String text) {
        return createLabel(text, TITLE_FONT, SECONDARY_COLOR);
    }
    
    /**
     * Creates a subtitle label.
     */
    public static JLabel createSubtitleLabel(String text) {
        return createLabel(text, SUBTITLE_FONT, TEXT_COLOR);
    }
    
    /**
     * Creates the standard background panel.
     */
    public static JPanel createBackgroundPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, BACKGROUND_COLOR,
                    getWidth(), getHeight(), BACKGROUND_LIGHT
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Subtle pattern
                g2d.setColor(new Color(255, 255, 255, 5));
                for (int i = 0; i < getWidth(); i += 30) {
                    for (int j = 0; j < getHeight(); j += 30) {
                        if ((i / 30 + j / 30) % 2 == 0) {
                            g2d.fillRect(i, j, 30, 30);
                        }
                    }
                }
                
                g2d.dispose();
            }
        };
    }
    
    /**
     * Creates a casino felt table panel.
     */
    public static JPanel createFeltPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Felt background with gradient
                GradientPaint gradient = new GradientPaint(
                    0, 0, FELT_GREEN.brighter(),
                    0, getHeight(), FELT_GREEN.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 30, 30);
                
                // Border
                g2d.setColor(new Color(139, 90, 43));
                g2d.setStroke(new BasicStroke(8));
                g2d.drawRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 30, 30);
                
                // Inner gold line
                g2d.setColor(SECONDARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 25, 25);
                
                g2d.dispose();
            }
        };
    }
    
    /**
     * Creates a panel with score display styling.
     */
    public static JPanel createScorePanel(String title, JLabel valueLabel) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = createLabel(title, BODY_FONT, TEXT_MUTED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        valueLabel.setFont(SCORE_FONT);
        valueLabel.setForeground(SECONDARY_COLOR);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(valueLabel);
        
        return panel;
    }
}

