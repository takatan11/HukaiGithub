import java.awt.*;
import java.util.List;

public abstract class Enemy {
    protected int x, y, width = 50, height = 50, hp = 1;
    protected int speedY = 1;

    public Enemy(int x, int y) { this.x = x; this.y = y; }

    public void move() { y += speedY; }
    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }
    public void damage() { hp--; }
    public boolean isAlive() { return hp > 0; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public abstract void moveBullets(List<EnemyBullet> enemyBullets);

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}
