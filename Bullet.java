import java.awt.*;

public class Bullet {
    private int x, y;
    private int speed = 8;

    public Bullet(int x, int y) { this.x = x; this.y = y; }

    public void move() { y -= speed; }

    public void draw(Graphics g) { g.setColor(Color.YELLOW); g.fillRect(x, y, 5, 10); }

    public int getY() { return y; }

    public Rectangle getBounds() { return new Rectangle(x, y, 5, 10); }
}
