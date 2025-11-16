import java.awt.*;

public abstract class Enemy {
    protected int x, y;
    protected int width = 40, height = 40;
    protected int life = 1;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void move();
    public abstract void draw(Graphics g);

    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }

    public void damage() { life--; }
    public boolean isAlive() { return life > 0; }
}
