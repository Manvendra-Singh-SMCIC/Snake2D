package game;

import java.awt.Color;

import javax.swing.*;

public class MainSnake {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 915, 700);
        frame.setTitle("Snake2D");
        frame.setResizable(false);
        ImageIcon icon = new ImageIcon((new MainSnake()).getClass().getResource("SnakeIcon.png"));
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        gamePanel.setBackground(Color.darkGray);
        frame.add(gamePanel);

        frame.setVisible(true);
    }
}