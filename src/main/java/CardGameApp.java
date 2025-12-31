import controller.MainController;

import javax.swing.*;

/**
 * Main entry point for the Card Games application.
 * 
 * This application uses the Model-View-Controller (MVC) architecture:
 * 
 * MODEL (model package):
 *   - Card, Suit, Rank, Deck: Core card game entities
 *   - HigherLowerModel: Game logic for Higher/Lower
 *   - BlackjackModel: Game logic for Blackjack
 * 
 * VIEW (view package):
 *   - MainMenuView: Main menu interface
 *   - HigherLowerView: Higher/Lower game interface
 *   - BlackjackView: Blackjack game interface
 *   - RulesView: Game rules display
 *   - CardPanel: Custom card rendering component
 *   - StyleUtils: UI styling utilities
 * 
 * CONTROLLER (controller package):
 *   - MainController: Main application controller & navigation
 *   - HigherLowerController: Higher/Lower game controller
 *   - BlackjackController: Blackjack game controller
 */
public class CardGameApp {
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            // Try to use system look and feel for better integration
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fall back to default if system L&F not available
            System.out.println("Using default look and feel");
        }
        
        // Enable anti-aliasing for text
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        // Start the application
        SwingUtilities.invokeLater(() -> {
            MainController controller = new MainController();
            controller.start();
        });
    }
}
