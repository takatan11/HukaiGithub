import java.awt.*;

public class Bullet {
    private int x, y;
    private int width = 10, height = 20;
    private int speed = 5;

    public Bullet(int x, int y) { this.x = x; this.y = y; }

    public void move() { y -= speed; }
    public int getY() { return y; }
    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }
}
