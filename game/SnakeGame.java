package game;

import javax.swing.*;

public class SnakeGame extends JFrame {

    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public SnakeGame() {
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null); // center screen

        showMenu();

        setVisible(true);
    }

    // Show the menu panel
    public void showMenu() {
        if (gamePanel != null) remove(gamePanel);
        menuPanel = new MenuPanel(this);
        add(menuPanel);
        revalidate();
        repaint();
    }

    // Start the game with selected delay
    public void startGame(int delay) {
        if (menuPanel != null) remove(menuPanel);
        gamePanel = new GamePanel(this, delay);
        add(gamePanel);
        revalidate();
        repaint();
        gamePanel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}
