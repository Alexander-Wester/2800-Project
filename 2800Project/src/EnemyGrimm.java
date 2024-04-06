import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

// Boss Grimm:
// Grimm has 10 HPs
// it creates multiple fireballs to attack player, if hitted by fireballs, player will lose 1 health.

public class EnemyGrimm extends Enemy {
    private ArrayList<Fireball> fireballs;

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }
    
    public EnemyGrimm(int x1, int y1, int w1, int h1, int h2) {
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
            //Since this is a miniboss type enemy and it uses fireballs killing it lets the player use fireballs
            gm.player.activateFireball();
            JOptionPane.showMessageDialog(null, "You have unlocked the ability to use the fireball (right click to use)");
        }
    }

    @Override
public void render(Graphics2D g2d) {
    super.render(g2d); // Call superclass render method to render the square

    int healthBarWidth = 50;
    int healthBarHeight = 10;
    int healthBarX = (int) x;
    int healthBarY = (int) y - healthBarHeight - 5; // Place health bar above the EnemyGrimm

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
    g2d.drawRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
    g2d.setColor(healthBarColor);
    int barWidth = (int) (healthBarWidth * healthPercentage);
    g2d.fillRect(healthBarX, healthBarY, barWidth, healthBarHeight);

    if (isAlive) {
        if (isBeingHit) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.ORANGE);
        }
        g2d.fill(new Rectangle((int) x, (int) y, (int) width, (int) height));
    }

    for (Fireball fireball : fireballs) {
        fireball.render(g2d);
    }
}

    
}
