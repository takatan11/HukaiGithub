import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ShootingGame extends JPanel implements KeyListener {

    private Player player;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<EnemyBullet> enemyBullets = new ArrayList<>();
    private boolean leftPressed, rightPressed, spacePressed;
    private int score = 0;
    private boolean gameOver = false;
    private boolean titleScreen = true;

    private int spawnCounter = 0;
    private int spawnInterval = 120;
    private int maxEnemies = 10;
    private int nextBossScore = 500;

    private BufferedImage backBuffer;

    public ShootingGame() {
        setFocusable(true);
        addKeyListener(this);

        resetGame();
        startGameLoop();
    }

    private void resetGame() {
        score = 0;
        gameOver = false;
        titleScreen = true;
        bullets.clear();
        enemyBullets.clear();
        enemies.clear();

        player = new Player(275, 500, bullets); // 画面下部固定

        // 初期敵
        enemies.add(new EnemyTypeA(100, 50));
        enemies.add(new EnemyTypeB(300, 100));

        spawnCounter = 0;
        nextBossScore = 500;

        repaint();
    }

    private void startGameLoop() {
        new Thread(() -> {
            long lastTime = System.nanoTime();
            double nsPerFrame = 1000000000.0 / 60.0; // 60FPS

            while(true) {
                long now = System.nanoTime();
                if(now - lastTime >= nsPerFrame) {
                    lastTime += nsPerFrame;
                    updateGame();
                    repaint();
                }
                try { Thread.sleep(1); } catch(InterruptedException e) { }
            }
        }).start();
    }

    private void updateGame() {
        if(titleScreen || gameOver) return;

        // プレイヤー操作
        if(leftPressed) player.move(-5);
        if(rightPressed) player.move(5);
        if(spacePressed) player.shoot();

        // プレイヤー弾移動
        Iterator<Bullet> bulletIt = bullets.iterator();
        while(bulletIt.hasNext()) {
            Bullet b = bulletIt.next();
            b.move();
            if(b.getY() < 0) bulletIt.remove();
        }

        // 敵移動・弾・衝突判定
        Iterator<Enemy> enemyIt = enemies.iterator();
        while(enemyIt.hasNext()) {
            Enemy enemy = enemyIt.next();
            enemy.move();

            // 敵弾発射
            if(enemy instanceof EnemyTypeA) {
                if(Math.random() < 0.01)
                    enemyBullets.add(new EnemyBullet(
                        enemy.getX() + enemy.getWidth()/2,
                        enemy.getY() + enemy.getHeight(),
                        0, 4
                    ));
            } else if(enemy instanceof EnemyTypeB) {
                if(Math.random() < 0.01) {
                    int dx = (int)(Math.random() * 3 - 1); // -1,0,1
                    enemyBullets.add(new EnemyBullet(
                        enemy.getX() + enemy.getWidth()/2,
                        enemy.getY() + enemy.getHeight(),
                        dx, 4
                    ));
                }
            } else if(enemy instanceof EnemyTypeC) {
                if(Math.random() < 0.02)
                    enemyBullets.add(new EnemyBullet(
                        enemy.getX() + enemy.getWidth()/2,
                        enemy.getY() + enemy.getHeight(),
                        0, 5
                    ));
            }

            // プレイヤーとの衝突
            if(player.getBounds().intersects(enemy.getBounds())) {
                player.damage();
                if(!player.isAlive()) { triggerGameOver(); return; }
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

        // 敵弾移動
        Iterator<EnemyBullet> ebIt = enemyBullets.iterator();
        while(ebIt.hasNext()) {
            EnemyBullet eb = ebIt.next();
            eb.move();
            if(eb.getY() > getHeight()) ebIt.remove();
            else if(eb.getBounds().intersects(player.getBounds())) {
                ebIt.remove();
                player.damage();
                if(!player.isAlive()) { triggerGameOver(); return; }
            }
        }

        // ボス出現
        if(score >= nextBossScore) {
            enemies.add(new EnemyTypeC(260, 50));
            nextBossScore += 1000;
        }

        // 敵自動生成
        spawnCounter++;
        if(spawnCounter >= spawnInterval) {
            spawnCounter = 0;
            int normalEnemyCount = (int) enemies.stream().filter(en -> !(en instanceof EnemyTypeC)).count();
            if(normalEnemyCount < maxEnemies) {
                int x = (int)(Math.random() * 560);
                if(Math.random() < 0.5) enemies.add(new EnemyTypeA(x, 0));
                else enemies.add(new EnemyTypeB(x, 0));
            }
        }
    }

    private void triggerGameOver() {
        gameOver = true;
        repaint();

        int result = JOptionPane.showConfirmDialog(
                this,
                "Game Over! Play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if(result == JOptionPane.YES_OPTION) resetGame();
        else System.exit(0);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(backBuffer == null || backBuffer.getWidth() != getWidth() || backBuffer.getHeight() != getHeight()) {
            backBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2 = backBuffer.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + score, 10, 20);

        if(titleScreen) {
            g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g2.drawString("Shooting Game: Press SPACE to Start", 150, 250);
        } else if(gameOver) {
            g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g2.setColor(Color.RED);
            g2.drawString("Game Over! Score: " + score, 200, 250);
        } else {
            player.draw(g2);
            for(Bullet b : bullets) b.draw(g2);
            for(Enemy e : enemies) e.draw(g2);
            for(EnemyBullet eb : enemyBullets) eb.draw(g2);
        }

        g.drawImage(backBuffer, 0, 0, null);
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

    @Override public void keyTyped(KeyEvent e) {}

    // メインメソッド
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shooting Game");
        ShootingGame game = new ShootingGame();
        frame.add(game);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

