// Boss2.java:
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

public class Boss2 extends Enemy {
    private ArrayList<Fireball2> fireballs;

    public ArrayList<Fireball2> getFireballs() {
        return fireballs;
    }
    
    public Boss2(int x1, int y1, int w1, int h1, int h2) {
        super(x1, y1, w1, h1, h2);
        isInvincible = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        fireballs = new ArrayList<>();
    }

    @Override
    public void tick(GameManager gm) {
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);

        if (isAlive) {
            if (isBeingHit) {
                if (beingHitTimer < System.currentTimeMillis()) {
                    isBeingHit = false;
                }
            }
        }
        if (health == 0) {
            health = -1;
            isAlive = false;
            gm.player.activateFireball();
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d); // Call superclass render method to render the square

        int healthBarWidth = 50;
        int healthBarHeight = 10;
        int healthBarX = (int) x;
        int healthBarY = (int) y - healthBarHeight - 5; // Place health bar above the Boss

        // Calculate health bar color based on current health
        Color healthBarColor = Color.GREEN;
        double healthPercentage = (double) health / 10;
        if (healthPercentage <= 0.5) {
            healthBarColor = Color.YELLOW;
        }
        if (healthPercentage <= 0.2) {
            healthBarColor = Color.RED;
        }

        g2d.setColor(Color.BLACK);
        g2d.drawRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight); // Outline
        g2d.setColor(healthBarColor);
        int barWidth = (int) (healthBarWidth * healthPercentage);
        g2d.fillRect(healthBarX, healthBarY, barWidth, healthBarHeight); // Fill

        if (isAlive) {
            if (isBeingHit) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.ORANGE);
            }
            g2d.fill(new Rectangle((int) x, (int) y, (int) width, (int) height));
        }

        for (Fireball2 fireball : fireballs) {
            fireball.render(g2d);
        }        
    }
}
