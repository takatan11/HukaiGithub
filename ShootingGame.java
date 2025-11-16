import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShootingGame extends JPanel implements ActionListener, KeyListener {

    Timer timer;
    Player player;
    Enemy enemy;
    GameMaster gm;

    boolean leftPressed = false;
    boolean rightPressed = false;

    public ShootingGame() {
        setFocusable(true);
        addKeyListener(this);

        player = new Player(250, 400);
        enemy  = new Enemy(250, 50);
        gm     = new GameMaster();

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        player.draw(g);
        enemy.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("Enemy クラス追加（赤い敵が落ちてくる）", 20, 20);
        g.drawString("Score: " + gm.getScore(), 20, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (leftPressed) player.moveLeft();
        if (rightPressed) player.moveRight(getWidth());

        enemy.move();

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT)  leftPressed = true;
        if (key == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT)  leftPressed = false;
        if (key == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shooting Game - Day3");
        ShootingGame game = new ShootingGame();
        frame.add(game);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
