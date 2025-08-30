package game;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(SnakeGame parent) {
        setLayout(null);
        setBackground(Color.BLACK);

        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");

        easy.setBounds(350, 200, 200, 50);
        medium.setBounds(350, 300, 200, 50);
        hard.setBounds(350, 400, 200, 50);

        add(easy);
        add(medium);
        add(hard);

        easy.addActionListener(e -> parent.startGame(200));   // slow
        medium.addActionListener(e -> parent.startGame(120)); // medium
        hard.addActionListener(e -> parent.startGame(70));    // fast
    }
}
