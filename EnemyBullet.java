import java.awt.*;

public class EnemyBullet {
    private int x, y;
    private int width = 8, height = 12;
    private int dx, dy;

    public EnemyBullet(int x, int y, int dx, int dy) {
        this.x = x; this.y = y;
        this.dx = dx; this.dy = dy;
    }

    public void move() { x += dx; y += dy; }
    public int getY() { return y; }
    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }

    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(x, y, width, height);
    }
}
