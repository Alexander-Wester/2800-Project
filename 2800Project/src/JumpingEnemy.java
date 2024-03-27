import java.awt.*;

// Jumping Enemy:
// Moving up and down, but won't attack player. Player lose 1 health when runs into it

public class JumpingEnemy extends Enemy {
    private boolean isMovingUp = true; // Indicates whether the enemy is moving up or down
    private static final int MOVEMENT_SPEED = 2; // Movement speed of the enemy

    public JumpingEnemy(int x1, int y1, int w1, int h1, int h2) {
        super(x1, y1, w1, h1, h2);
        isInvincible = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public void tick(GameManager gm) {
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);

        if (isAlive) {
            if (isBeingHit) {
                if (beingHitTimer < System.currentTimeMillis()) {
                    isBeingHit = false;
                }
            }
            if (!doneFalling) {
                fall(gm);
            }
        }
        if (health <= 0) {
            isAlive = false;
        }
    }

    private void fall(GameManager gm) {
        // Move the enemy up or down
        if (isMovingUp) {
            y -= MOVEMENT_SPEED;
            if (y < 150) {
                y = 150; // Ensure the enemy stays within the lower limit
                isMovingUp = false; // Reverse direction when reaching the lower limit
            }
        } else {
            y += MOVEMENT_SPEED;
            if (y + height > 540) {
                y = 540 - height; // Ensure the enemy stays within the upper limit
                isMovingUp = true; // Reverse direction when reaching the upper limit
            }
        }
    } 

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
