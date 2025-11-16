import java.awt.*;

public class EnemyTypeA extends Enemy {
    private int dy = 2;

    public EnemyTypeA(int x, int y) {
        super(x, y);
        this.life = 1;
    }

    @Override
    public void move() { y += dy; if(y > 450) y = 0; }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}
