import java.awt.*;
import java.util.List;

public class Player {
    private int x, y;
    private List<Bullet> bullets;
    private long lastShotTime = 0;

    public Player(int x, int y, List<Bullet> bullets) {
        this.x = x;
        this.y = y;
        this.bullets = bullets;
    }

    public void move(int dx) {
        x += dx;
        if(x < 0) x = 0;
        if(x > 560) x = 560;
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if(now - lastShotTime > 300) { // 0.3秒ごと
            bullets.add(new Bullet(x + 18, y));
            lastShotTime = now;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 40, 40);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }
}
