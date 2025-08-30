package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private final int TILE_SIZE = 25;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int MAX_TILES = (WIDTH * HEIGHT) / (TILE_SIZE * TILE_SIZE);

    private final int[] x = new int[MAX_TILES];
    private final int[] y = new int[MAX_TILES];

    private int bodyParts = 5;
    private int score = 0;

    private int foodX;
    private int foodY;

    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;

    private JButton retryButton;
    private SnakeGame parent;

    public GamePanel(SnakeGame parent, int delay) {
        this.parent = parent;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        setLayout(null);

        addKeyListener(new MyKeyAdapter());

        random = new Random();

        // Retry button
        retryButton = new JButton("Retry");
        retryButton.setBounds(WIDTH/2 - 60, HEIGHT/2 + 40, 120, 40);
        retryButton.setFocusable(false);
        retryButton.setVisible(false);
        retryButton.addActionListener(e -> parent.showMenu());
        add(retryButton);

        startGame(delay);
    }

    private void startGame(int delay) {
        // Reset snake
        bodyParts = 5;
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 100 - i * TILE_SIZE;
            y[i] = 100;
        }
        direction = 'R';
        score = 0;
        running = true;
        retryButton.setVisible(false);

        spawnFood();

        timer = new Timer(delay, this);
        timer.start();
    }

    private void spawnFood() {
        foodX = random.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        foodY = random.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            // Food
            g.setColor(Color.RED);
            g.fillOval(foodX, foodY, TILE_SIZE, TILE_SIZE);

            // Snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) g.setColor(Color.GREEN);
                else g.setColor(new Color(45,180,0));
                g.fillRect(x[i], y[i], TILE_SIZE, TILE_SIZE);
            }

            // Score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 20, 30);

        } else {
            // Game Over
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", WIDTH/2 - 120, HEIGHT/2 - 20);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Score: " + score, WIDTH/2 - 50, HEIGHT/2 + 20);

            retryButton.setVisible(true);
        }
    }

    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U' -> y[0] -= TILE_SIZE;
            case 'D' -> y[0] += TILE_SIZE;
            case 'L' -> x[0] -= TILE_SIZE;
            case 'R' -> x[0] += TILE_SIZE;
        }
    }

    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            bodyParts++;
            score++;
            spawnFood(); // infinite food
        }
    }

    private void checkCollisions() {
        // Self collision
        for (int i = 1; i < bodyParts; i++) {
            if (x[0] == x[i] && y[0] == y[i]) running = false;
        }
        // Wall collision
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) running = false;

        if (!running) timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> { if (direction != 'R') direction = 'L'; }
                case KeyEvent.VK_RIGHT -> { if (direction != 'L') direction = 'R'; }
                case KeyEvent.VK_UP -> { if (direction != 'D') direction = 'U'; }
                case KeyEvent.VK_DOWN -> { if (direction != 'U') direction = 'D'; }
            }
        }
    }
}
