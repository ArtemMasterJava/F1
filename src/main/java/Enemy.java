import javax.swing.*;
import java.awt.*;

/**
 * Created by USER on 08.06.2015.
 */
public class Enemy {
    int x;
    int y;
    int v;
    Image img = new ImageIcon(getClass().getClassLoader().getResource("res/enemy.png")).getImage();
    Road road;


    public Enemy(int x, int y, int v, Road road) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.road = road;
    }

    public void move() {
        x = x - road.p.v + v;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, 150, 63);

    }

}
