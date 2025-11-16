import java.util.List;
import java.awt.*;

public class EnemyTypeB extends Enemy {
    public EnemyTypeB(int x, int y) { super(x, y); hp = 2; }

    @Override
    public void moveBullets(List<EnemyBullet> enemyBullets) {
        if(Math.random() < 0.01) {
            int dx = (int)(Math.random() * 3 - 1); // -1,0,1
            enemyBullets.add(new EnemyBullet(x + width/2, y + height, dx, 4));
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, width, height);
    }
}
