import java.awt.*;
import java.util.List;

public class Player {
    private int x, y;
    private int width = 50, height = 50;
    private int hp = 5;
    private List<Bullet> bullets;
    private int shootCooldown = 0;

    public Player(int x, int y, List<Bullet> bullets) {
        this.x = x;
        this.y = y;
        this.bullets = bullets;
    }

    public void move(int dx) {
        x += dx;
        if(x < 0) x = 0;
        if(x > 600 - width) x = 600 - width;
    }

    public void shoot() {
        if(shootCooldown <= 0) {
            bullets.add(new Bullet(x + width/2 - 5, y));
            shootCooldown = 15; // クールタイム
        } else {
            shootCooldown--;
        }
    }

    public void damage() { hp--; }
    public boolean isAlive() { return hp > 0; }
    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
        // HPバー
        g.setColor(Color.RED);
        g.fillRect(10, 40, hp * 20, 10);
    }
}
