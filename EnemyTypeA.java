import java.util.List;
import java.awt.*;

public class EnemyTypeA extends Enemy {
    public EnemyTypeA(int x, int y) { super(x, y); hp = 1; }

    @Override
    public void moveBullets(List<EnemyBullet> enemyBullets) {
        if(Math.random() < 0.01)
            enemyBullets.add(new EnemyBullet(x + width/2, y + height, 0, 4));
    }
}
