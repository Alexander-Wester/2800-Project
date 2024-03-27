import java.awt.*;
import java.lang.Math;

// Enemy Vengefly:
// Vengefly has 3 HPs
// Chases and attacks player, if hitted by Vengefly, player will lose 1 health.
public class VengeflyEnemy extends Enemy {

    private double speed;
    private boolean isChasing;

    public VengeflyEnemy(int x1, int y1, int w1, int h1, int h2) {
        super(x1, y1, w1, h1, h2);
        isInvincible = false;
        hitBox = new Rectangle(x1, y1, w1, h1);
        speed = 2; // Set the chasing speed
        isChasing = false;
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
            
            if (isNearPlayer(gm)) {
                chasePlayer(gm);
            }
        }
        
        if (health <= 0) {
            isAlive = false;
        }
    }

    private void chasePlayer(GameManager gm) {
        GameObject player = gm.gameObjects.get(0); // Assuming player is at index 0

        double dx = player.x - x;
        double dy = player.y - y;
        double angle = Math.atan2(dy, dx);
        
        // Calculate velocity components
        double vx = Math.cos(angle);
        double vy = Math.sin(angle);
    
        double speed = 2.0;
        
        // Update enemy's position
        x += vx * speed;
        y += vy * speed;
    }


    private boolean isNearPlayer(GameManager gm) {
        GameObject player = gm.gameObjects.get(0); 
        
        // Calculate distance between enemy and player using Pythagoras' theorem
        double dx = Math.abs(player.x - x);
        double dy = Math.abs(player.y - y);
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        // Define the threshold for "near" 
        double nearThreshold = 500;
        
        return distance < nearThreshold;
    }    

    @Override
    public void render(Graphics2D g2d) {
        if (isAlive) {
            if (isBeingHit) {
                g2d.setColor(Color.red);
            } else {
                g2d.setColor(Color.orange);
            }
            g2d.fill(new Rectangle((int) x, (int) y, (int) width, (int) height));
        }
    }
}
