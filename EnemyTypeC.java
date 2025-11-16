import java.awt.*;
import java.util.List;

public class EnemyTypeC extends Enemy {
    private int speedX = 2;

    public EnemyTypeC(int x, int y) { super(x, y); hp = 10; }

    @Override
    public void move() {
        x += speedX;
        if(x < 0 || x > 600 - width) speedX *= -1;
    }

    @Override
    public void moveBullets(List<EnemyBullet> enemyBullets) {
        if(Math.random() < 0.02) {
            enemyBullets.add(new EnemyBullet(x + width/2, y + height, 0, 5));
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
        g.setColor(Color.RED);
        g.fillRect(x, y - 10, hp * 10, 5);
    }
}
