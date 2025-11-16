import java.awt.*;

public class EnemyTypeB extends Enemy {
    private int dy = 1;
    private int dx = 2;
    private boolean right = true;

    public EnemyTypeB(int x, int y) {
        super(x, y);
        this.life = 2; // ライフ2
    }

    @Override
    public void move() {
        y += dy;
        if(right) x += dx; else x -= dx;
        if(x < 0) right = true;
        if(x > 560) right = false;
        if(y > 450) y = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
    }
}
