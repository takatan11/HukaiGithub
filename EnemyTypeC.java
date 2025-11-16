import java.awt.*;

public class EnemyTypeC extends Enemy {
    private int dx = 3;
    private boolean right = true;

    public EnemyTypeC(int x, int y) {
        super(x, y);
        this.life = 10; // ボスはライフ10
        this.width = 80;
        this.height = 80;
    }

    @Override
    public void move() {
        if(right) x += dx; else x -= dx;
        if(x < 0) right = true;
        if(x > 520) right = false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, width, height);
        // ライフバー表示
        g.setColor(Color.BLACK);
        g.fillRect(x, y-10, width, 5);
        g.setColor(Color.GREEN);
        g.fillRect(x, y-10, (int)((width*(double)life/10)), 5);
    }
}
