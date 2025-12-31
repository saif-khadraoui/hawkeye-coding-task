package view;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom panel that renders a playing card with a beautiful design.
 */
public class CardPanel extends JPanel {
    
    private Card card;
    private boolean faceDown;
    private static final int CARD_WIDTH = 120;
    private static final int CARD_HEIGHT = 170;
    private static final int CORNER_RADIUS = 15;
    
    // Colors
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255);
    private static final Color CARD_BORDER = new Color(200, 200, 200);
    private static final Color CARD_BACK_PRIMARY = new Color(25, 55, 109);
    private static final Color CARD_BACK_SECONDARY = new Color(45, 85, 149);
    private static final Color JOKER_COLOR = new Color(138, 43, 226);
    
    public CardPanel() {
        this(null, false);
    }
    
    public CardPanel(Card card, boolean faceDown) {
        this.card = card;
        this.faceDown = faceDown;
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setOpaque(false);
    }
    
    public void setCard(Card card) {
        this.card = card;
        this.faceDown = false;
        repaint();
    }
    
    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
        repaint();
    }
    
    public Card getCard() {
        return card;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        
        int x = (getWidth() - CARD_WIDTH) / 2;
        int y = (getHeight() - CARD_HEIGHT) / 2;
        
        if (card == null) {
            drawEmptySlot(g2d, x, y);
        } else if (faceDown) {
            drawCardBack(g2d, x, y);
        } else {
            drawCardFace(g2d, x, y);
        }
        
        g2d.dispose();
    }
    
    private void drawEmptySlot(Graphics2D g2d, int x, int y) {
        RoundRectangle2D cardShape = new RoundRectangle2D.Float(x, y, CARD_WIDTH, CARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS);
        g2d.setColor(new Color(100, 100, 100, 50));
        g2d.fill(cardShape);
        g2d.setColor(new Color(150, 150, 150));
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0));
        g2d.draw(cardShape);
    }
    
    private void drawCardBack(Graphics2D g2d, int x, int y) {
        RoundRectangle2D cardShape = new RoundRectangle2D.Float(x, y, CARD_WIDTH, CARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS);
        
        // Card background with gradient
        GradientPaint gradient = new GradientPaint(x, y, CARD_BACK_PRIMARY, x + CARD_WIDTH, y + CARD_HEIGHT, CARD_BACK_SECONDARY);
        g2d.setPaint(gradient);
        g2d.fill(cardShape);
        
        // Pattern on back
        g2d.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 12; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.fillOval(x + 10 + i * 13, y + 10 + j * 13, 8, 8);
                }
            }
        }
        
        // Border
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(cardShape);
        
        // Inner border
        g2d.setColor(new Color(255, 215, 0, 150));
        g2d.draw(new RoundRectangle2D.Float(x + 8, y + 8, CARD_WIDTH - 16, CARD_HEIGHT - 16, CORNER_RADIUS - 5, CORNER_RADIUS - 5));
    }
    
    private void drawCardFace(Graphics2D g2d, int x, int y) {
        RoundRectangle2D cardShape = new RoundRectangle2D.Float(x, y, CARD_WIDTH, CARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS);
        
        // Shadow
        g2d.setColor(new Color(0, 0, 0, 30));
        g2d.fill(new RoundRectangle2D.Float(x + 3, y + 3, CARD_WIDTH, CARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS));
        
        // Card background
        g2d.setColor(CARD_BACKGROUND);
        g2d.fill(cardShape);
        
        // Border
        g2d.setColor(CARD_BORDER);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.draw(cardShape);
        
        if (card.isJoker()) {
            drawJoker(g2d, x, y);
        } else {
            drawStandardCard(g2d, x, y);
        }
    }
    
    private void drawStandardCard(Graphics2D g2d, int x, int y) {
        Color suitColor = card.getSuit().getColor();
        String rankSymbol = card.getRank().getSymbol();
        String suitSymbol = card.getSuit().getSymbol();
        
        // Top-left rank and suit
        g2d.setColor(suitColor);
        g2d.setFont(new Font("Arial", Font.BOLD, 22));
        g2d.drawString(rankSymbol, x + 10, y + 28);
        g2d.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        g2d.drawString(suitSymbol, x + 10, y + 50);
        
        // Bottom-right rank and suit (rotated)
        Graphics2D g2dRotated = (Graphics2D) g2d.create();
        g2dRotated.rotate(Math.PI, x + CARD_WIDTH / 2.0, y + CARD_HEIGHT / 2.0);
        g2dRotated.setColor(suitColor);
        g2dRotated.setFont(new Font("Arial", Font.BOLD, 22));
        g2dRotated.drawString(rankSymbol, x + 10, y + 28);
        g2dRotated.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        g2dRotated.drawString(suitSymbol, x + 10, y + 50);
        g2dRotated.dispose();
        
        // Center suit symbol
        g2d.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 60));
        FontMetrics fm = g2d.getFontMetrics();
        int centerX = x + (CARD_WIDTH - fm.stringWidth(suitSymbol)) / 2;
        int centerY = y + (CARD_HEIGHT + fm.getAscent()) / 2 - 10;
        g2d.drawString(suitSymbol, centerX, centerY);
    }
    
    private void drawJoker(Graphics2D g2d, int x, int y) {
        // Joker text
        g2d.setColor(JOKER_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("JOKER", x + 10, y + 25);
        
        // Joker symbol in center
        g2d.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 50));
        String jokerSymbol = "🃏";
        FontMetrics fm = g2d.getFontMetrics();
        int centerX = x + (CARD_WIDTH - fm.stringWidth(jokerSymbol)) / 2;
        g2d.drawString(jokerSymbol, centerX, y + CARD_HEIGHT / 2 + 15);
        
        // Stars decoration
        g2d.setColor(new Color(255, 215, 0));
        g2d.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        g2d.drawString("★", x + 15, y + CARD_HEIGHT - 20);
        g2d.drawString("★", x + CARD_WIDTH - 35, y + CARD_HEIGHT - 20);
    }
}

