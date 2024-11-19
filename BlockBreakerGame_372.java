import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BlockBreakerGame_372 extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Block> blocks;
    private boolean gameRunning;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Block Breaker");
        BlockBreakerGame_372 game = new BlockBreakerGame_372();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }

    public BlockBreakerGame_372() {
        paddle = new Paddle(350, 550);
        ball = new Ball(400, 300);
        blocks = new ArrayList<>();
        gameRunning = true;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                blocks.add(new Block(80 * j + 50, 30 * i + 50));
            }
        }

        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.BLACK);
    }

    public void start() {
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            ball.move();
            paddle.move();
            checkCollisions();
            repaint();
        }
    }

    private void checkCollisions() {
        if (ball.getBounds().intersects(paddle.getBounds())) {
            ball.reverseYDirection();
        }

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (ball.getBounds().intersects(block.getBounds())) {
                ball.reverseYDirection();
                blocks.remove(i);
                break;
            }
        }

        if (ball.getY() > getHeight()) {
            gameRunning = false;
            JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.PLAIN_MESSAGE);
        }

        if (blocks.isEmpty()) {
            gameRunning = false;
            JOptionPane.showMessageDialog(this, "You Win!", "Congratulations", JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameRunning) {
            ball.draw(g);
            paddle.draw(g);

            for (Block block : blocks) {
                block.draw(g);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddle.setDx(-5);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.setDx(5);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddle.setDx(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    class Paddle {
        private int x, y, dx;
        private final int WIDTH = 100, HEIGHT = 10;

        public Paddle(int x, int y) {
            this.x = x;
            this.y = y;
            this.dx = 0;
        }

        public void move() {
            x += dx;
            if (x < 0) {
                x = 0;
            }
            if (x > getWidth() - WIDTH) {
                x = getWidth() - WIDTH;
            }
        }

        public void draw(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, WIDTH, HEIGHT);
        }

        public void setDx(int dx) {
            this.dx = dx;
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, WIDTH, HEIGHT);
        }
    }

    class Ball {
        private int x, y, dx, dy;
        private final int SIZE = 20;

        public Ball(int x, int y) {
            this.x = x;
            this.y = y;
            this.dx = 2;
            this.dy = -2;
        }

        public void move() {
            x += dx;
            y += dy;

            if (x < 0 || x > getWidth() - SIZE) {
                dx = -dx;
            }

            if (y < 0) {
                dy = -dy;
            }
        }

        public void reverseYDirection() {
            dy = -dy;
        }

        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillOval(x, y, SIZE, SIZE);
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, SIZE, SIZE);
        }

        public int getY() {
            return y;
        }
    }

    class Block {
        private int x, y, width = 60, height = 20;

        public Block(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, width, height);
        }

        public Rectangle getBounds() {
            return new Rectangle(x, y, width, height);
        }
    }
}
