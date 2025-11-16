import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

public class ShootingGame extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    private Player player;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private boolean leftPressed, rightPressed, spacePressed;
    private int score = 0;
    private boolean gameOver = false;
    private boolean titleScreen = true;

    public ShootingGame() {
        setFocusable(true);
        addKeyListener(this);
        resetGame();
        timer = new Timer(16, this);
        timer.start();
    }

    // ゲーム状態リセット
    private void resetGame() {
        score = 0;
        gameOver = false;
        titleScreen = true;
        bullets.clear();
        enemies.clear();
        player = new Player(250, 400, bullets);
        enemies.add(new EnemyTypeA(100, 50));
        enemies.add(new EnemyTypeB(300, 100));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        // 日本語フォント
        g.setFont(new Font("MS Gothic", Font.PLAIN, 18));
        g.setColor(Color.WHITE);

        if(titleScreen) {
            g.drawString("シューティングゲーム：スペースキーでスタート", 120, 250);
            g.drawString("← → で移動、スペースで弾発射", 150, 280);
            return;
        }

        if(gameOver) {
            g.setColor(Color.RED);
            g.drawString("ゲームオーバー！スコア: " + score, 200, 250);
            return;
        }

        // プレイヤー描画
        player.draw(g);

        // 弾描画
        for(Bullet b : bullets) b.draw(g);

        // 敵描画
        for(Enemy enemy : enemies) enemy.draw(g);

        // スコア表示
        g.setColor(Color.WHITE);
        g.drawString("スコア: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(titleScreen) {
            repaint();
            return;
        }

        if(gameOver) {
            repaint();
            return;
        }

        // プレイヤー移動
        if(leftPressed) player.move(-5);
        if(rightPressed) player.move(5);
        if(spacePressed) player.shoot();

        // 弾移動
        Iterator<Bullet> bulletIt = bullets.iterator();
        while(bulletIt.hasNext()) {
            Bullet b = bulletIt.next();
            b.move();
            if(b.getY() < 0) bulletIt.remove();
        }

        // 敵移動・衝突判定
        Iterator<Enemy> enemyIt = enemies.iterator();
        while(enemyIt.hasNext()) {
            Enemy enemy = enemyIt.next();
            enemy.move();

            // プレイヤーとの衝突
            if(player.getBounds().intersects(enemy.getBounds())) {
                gameOver = true;
                repaint();

                int result = JOptionPane.showConfirmDialog(
                        this,
                        "ゲームオーバー！もう一度プレイしますか？",
                        "ゲームオーバー",
                        JOptionPane.YES_NO_OPTION
                );

                if(result == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
                return;
            }

            // 弾との衝突
            Iterator<Bullet> bulletIt2 = bullets.iterator();
            while(bulletIt2.hasNext()) {
                Bullet b = bulletIt2.next();
                if(b.getBounds().intersects(enemy.getBounds())) {
                    enemy.damage();
                    bulletIt2.remove();
                    if(!enemy.isAlive()) {
                        enemyIt.remove();
                        score += (enemy instanceof EnemyTypeC) ? 500 : 100;
                    }
                    break;
                }
            }
        }

        // ボス出現処理（スコア500以上）
        boolean bossExists = enemies.stream().anyMatch(en -> en instanceof EnemyTypeC);
        if(score >= 500 && !bossExists) {
            enemies.add(new EnemyTypeC(260, 50));
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT) leftPressed = true;
        if(key == KeyEvent.VK_RIGHT) rightPressed = true;
        if(key == KeyEvent.VK_SPACE) {
            if(titleScreen) titleScreen = false;
            else spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT) leftPressed = false;
        if(key == KeyEvent.VK_RIGHT) rightPressed = false;
        if(key == KeyEvent.VK_SPACE) spacePressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("シューティングゲーム");
        ShootingGame game = new ShootingGame();
        frame.add(game);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
