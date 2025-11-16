import java.awt.Color;
import java.awt.Graphics;

public class Player {

    private int x;
    private int y;
    private final int width = 50;
    private final int height = 20;
    private final int speed = 5;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight(int windowWidth) {
        if (x + width + speed <= windowWidth) {
            x += speed;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, width, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}

