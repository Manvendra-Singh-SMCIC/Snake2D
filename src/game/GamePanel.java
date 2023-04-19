package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("all")
public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private ImageIcon snakeTitle;
    private ImageIcon leftMouth;
    private ImageIcon rightMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;
    private ImageIcon snakeImage;
    private ImageIcon enemy;
    boolean isPaused = false;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private int[] snakexlength = new int[750];
    private int[] snakeylength = new int[750];
    private int lengthOfSnake = 3;

    int[] xPos = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525,
            550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850 };
    int[] yPos = { 75, 100, 125, 150, 175, 200, 225, 250, 275, 300,
            325, 350, 375, 400, 425, 450, 475, 500, 525, 550,
            575, 600, 625 };

    private Random random = new Random();
    private int enemyX, enemyY;

    private int moves = 0;

    private int score = 0;

    private Timer timer;
    private int delay = 100;

    private boolean gameOver;

    public GamePanel() {
        images();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();

        newEnemy();
    }

    public void images() {
        snakeTitle = new ImageIcon(getClass().getResource("SnakeTitle.jpg"));
        leftMouth = new ImageIcon(getClass().getResource("SnakeLeftMouth.png"));
        rightMouth = new ImageIcon(getClass().getResource("SnakeRightMouth.png"));
        upMouth = new ImageIcon(getClass().getResource("SnakeDownMouth.png"));
        downMouth = new ImageIcon(getClass().getResource("SnakeUpMouth.png"));
        snakeImage = new ImageIcon(getClass().getResource("SnakeImage.png"));
        enemy = new ImageIcon(getClass().getResource("SnakeEnemy.png"));
    }

    private void newEnemy() {
        enemyX = xPos[random.nextInt(xPos.length)];
        enemyY = yPos[random.nextInt(yPos.length)];

        for (int i = lengthOfSnake; i >= 0; i--) {
            if (snakexlength[i] == enemyX && snakeylength[i] == enemyY) {
                newEnemy();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.white);
        g.drawRect(24, 10, 852, 55);
        g.drawRect(24, 74, 852, 576);

        snakeTitle.paintIcon(this, g, 25, 11);

        g.setColor(Color.black);
        g.fillRect(25, 75, 851, 575);

        if (moves == 0) {
            snakexlength[0] = 100;
            snakexlength[1] = 75;
            snakexlength[2] = 50;

            snakeylength[0] = 100;
            snakeylength[1] = 100;
            snakeylength[2] = 100;
        }

        if (left) {
            leftMouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (right) {
            rightMouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (up) {
            upMouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (down) {
            downMouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }

        for (int i = 1; i < lengthOfSnake; i++) {
            snakeImage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
        }

        enemy.paintIcon(this, g, enemyX, enemyY);

        if (gameOver) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 50));
            g.drawString("Game Over", 300, 300);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press SPACE TO Restart", 320, 350);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
        g.drawString("Score : " + score, 750, 30);
        g.drawString("Length : " + lengthOfSnake, 750, 50);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = lengthOfSnake; i > 0; i--) {
            snakexlength[i] = snakexlength[i - 1];
            snakeylength[i] = snakeylength[i - 1];
        }

        if (left) {
            snakexlength[0] -= 25;
        }
        if (right) {
            snakexlength[0] += 25;
        }
        if (up) {
            snakeylength[0] -= 25;
        }
        if (down) {
            snakeylength[0] += 25;
        }

        if (snakexlength[0] > 850) {
            snakexlength[0] = 25;
        }
        if (snakexlength[0] < 25) {
            snakexlength[0] = 850;
        }
        if (snakeylength[0] > 625) {
            snakeylength[0] = 75;
        }
        if (snakeylength[0] < 75) {
            snakeylength[0] = 625;
        }

        colloidesWithEnemy();
        colloidesWithBody();

        repaint();
    }

    private void colloidesWithBody() {
        for (int i = lengthOfSnake; i > 0; i--) {
            if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void colloidesWithEnemy() {
        if (snakexlength[0] == enemyX && snakeylength[0] == enemyY) {
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver == true) {
            restart();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT && !right) {
            left = true;
            right = false;
            up = false;
            down = false;

            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !left) {
            left = false;
            right = true;
            up = false;
            down = false;

            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && !down) {
            left = false;
            right = false;
            up = true;
            down = false;

            moves++;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !up) {
            left = false;
            right = false;
            up = false;
            down = true;

            moves++;
        }
    }

    private void restart() {
        gameOver = false;
        moves = 0;
        score = 0;
        lengthOfSnake = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
