import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * Created by USER on 27.05.2015.
 */
public class Road extends JPanel implements ActionListener, Runnable {
    Image img = new ImageIcon("res/road.png").getImage();
    Player p = new Player();
    Timer mainTimer = new Timer(20, this);
    List<Enemy> enemies = new ArrayList<Enemy>();

    public Road() {
        enemiesFactory.start();
        mainTimer.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    Thread enemiesFactory = new Thread(this);

    public void run() {
        while (true) {
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(2000));
                enemies.add(new Enemy(1200, (500), rand.nextInt(200), this));
                enemies.add(new Enemy(1200, (600), rand.nextInt(90), this));
                enemies.add(new Enemy(1200, (700), rand.nextInt(150), this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            p.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            p.keyReleased(e);
        }
    }

    public void paint(Graphics g) {

        g.drawImage(img, p.layer1, 0, null);
        g.drawImage(img, p.layer2, 0, null);
        g.drawImage(p.img, p.x, p.y, null);

        double v = (50 / Player.MAX_V) * p.v;
        g.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.ITALIC, 20);
        g.setFont(font);
        g.drawString("Speed: " + v + "km/h", 50, 469);
        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.x >= 2400 || e.x <= -2400) {
                i.remove();

            } else {
                e.move();
                g.drawImage(e.img, e.x, e.y, null);
            }
        }
    }

    private void testWin() {
        if (p.s > 100000) {
            JOptionPane.showMessageDialog(null, "You win!!!");
            System.exit(0);
        }

    }

    public void actionPerformed(ActionEvent e) {
        p.move();
        repaint();
        testCollisionWithEnemies();
        testWin();
    }

    private void testCollisionWithEnemies() {
        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (p.getRect().intersects(e.getRect())) {
                JOptionPane.showMessageDialog(null, "Your lose!!!");
                System.exit(1);
            }
        }
    }
}
