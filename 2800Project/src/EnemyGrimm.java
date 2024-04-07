import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// Boss Grimm:
// Grimm has 10 HPs
// it creates multiple fireballs to attack player, if hitted by fireballs, player will lose 1 health.

public class EnemyGrimm extends Enemy {
    private ArrayList<Fireball> fireballs;
    private BufferedImage spriteSheet;
    private int spriteColumns = 17;
    private int spriteRows = 7;
    private int currentFrame = 0;
    private long lastFrameTime;
    private static final int ANIMATION_DELAY = 120; // Delay between each frame (milliseconds)

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    private double fireballTimer = 0;

    public EnemyGrimm(int x1, int y1, int w1, int h1, int h2) {
        super(x1, y1, w1, h1, h2);
        isInvincible = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        fireballs = new ArrayList<>();
        loadSpriteSheet("grimm.png");
        lastFrameTime = System.currentTimeMillis();
    }

    private void loadSpriteSheet(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                spriteSheet = ImageIO.read(inputStream);
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading sprite sheet: " + e.getMessage());
            e.printStackTrace();
        }
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

        if (isAlive && spriteSheet != null && !isBeingHit) {
            int spriteWidth = spriteSheet.getWidth() / spriteColumns;
            int spriteHeight = spriteSheet.getHeight() / spriteRows;
            int srcX, srcY;

            if (isBeingHit) {
                // If the enemy is being hit, display sprite from the 6th row and 2nd column
                srcX = spriteWidth;
                srcY = 5 * spriteHeight;
            } else {
                // Otherwise, display the regular animation from the 3rd to 4th row
                int currentAnimationFrame = currentFrame % (spriteColumns * 12); // End at the 4th row
                srcX = (currentAnimationFrame % spriteColumns) * spriteWidth;
                srcY = ((currentAnimationFrame / spriteColumns) % 2 + 2) * spriteHeight; // Start from
                                                                                         // 3rd row, end
                                                                                         // at 4th row
            }
            g2d.drawImage(spriteSheet, (int) x - 180, (int) y - 180, (int) (x - 180 + width * 3.8),
                    (int) (y - 180 + height * 3.8),
                    srcX, srcY, srcX + spriteWidth, srcY + spriteHeight, null);

            // Update the frame if enough time has passed
            long currentTime = System.currentTimeMillis();
            if (!isBeingHit && currentTime - lastFrameTime >= ANIMATION_DELAY) {
                currentFrame = (currentFrame + 1) % (spriteColumns * 12); // End at the 4th row
                lastFrameTime = currentTime; // Update lastFrameTime
            }
            // if (isBeingHit) {
            // g2d.setColor(Color.red);
            // } else {
            // g2d.setColor(Color.orange);
            // }
            // g2d.fill(new Rectangle((int) x, (int) y, (int) width, (int) height));
        }

        for (Fireball fireball : fireballs) {
            fireball.render(g2d);
        }
    }

}
