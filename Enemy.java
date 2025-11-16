import java.awt.Color;
import java.awt.Graphics;

public class Enemy {

    private int x;
    private int y;
    private final int size = 40;
    private int speed = 2;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, size, size);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
