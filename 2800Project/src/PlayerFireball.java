import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class PlayerFireball {
    public Ellipse2D.Double hitBox;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double startTime;

    public PlayerFireball(double x, double y, double direction, double time) {
        this.x = x;
        this.y = y;
        this.startTime = time;
        double speed = 7.0;
        this.dx = Math.cos(direction) * speed;
        this.dy = Math.sin(direction) * speed;
        hitBox = new Ellipse2D.Double(this.x,this.y,25,25);
    }

    public void tick(GameManager gm){
        x += dx;
        y += dy;
        hitBox = new Ellipse2D.Double(x,y,25,25);
        ArrayList<Rectangle> arr = gm.getCurrentLevel().collisionArray;
        for (Rectangle rectangle : arr) {
            if (hitBox.intersects(rectangle)) {
                Player player = gm.player;
                player.resetFireball();
                return;
            }
        }
        ArrayList<Enemy> enemyArr = gm.getCurrentLevel().enemyList;
        for (Enemy enemy : enemyArr) {
            if (hitBox.intersects(enemy.hitBox) && enemy.isAlive) {
                enemy.hitLanded(gm);
                Player player = gm.player;
                player.resetFireball();
                return;
            }
        }
        if(System.currentTimeMillis() >= startTime + 2000){
            Player player = gm.player;
            player.resetFireball();
        }


    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.orange);
        g2d.fill(hitBox);
    }
}
