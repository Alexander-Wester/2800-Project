import java.awt.*;
import java.lang.Math;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// Enemy Vengefly:
// Vengefly has 3 HPs
// Chases and attacks player, if hitted by Vengefly, player will lose 1 health.
public class VengeflyEnemy extends Enemy {
    private BufferedImage spriteSheet;
    private int currentFrame = 0;
    private long lastFrameTime;
    private final long ANIMATION_DELAY = 100; // Milliseconds between frame changes
    private final int SPRITE_COLUMNS = 24; // Number of columns in the sprite sheet
    private final int SPRITE_ROWS = 1; // Number of rows in the sprite sheet

    // private double speed;
    // private boolean isChasing;

    public VengeflyEnemy(int x1, int y1, int w1, int h1, int h2) {
        super(x1, y1, w1, h1, h2);
        isInvincible = false;
        hitBox = new Rectangle(x1, y1, w1, h1);
        // speed = 2; // Set the chasing speed
        // isChasing = false;
        loadSpriteSheet("vengeFly.png");
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

            if (isNearPlayer(gm)) {
                chasePlayer(gm);
            }
        }

        if (health <= 0) {
            isAlive = false;
        }
    }

    private void chasePlayer(GameManager gm) {
        GameObject player = gm.player; // Assuming player is at index 0

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
        GameObject player = gm.player;

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
        if (spriteSheet != null) {
            if (!isBeingHit && isAlive) {
                int spriteWidth = spriteSheet.getWidth() / SPRITE_COLUMNS;
                int spriteHeight = spriteSheet.getHeight() / SPRITE_ROWS;
                int srcX = currentFrame * spriteWidth;
                int srcY = 0; // Assuming the animation is on the first row
                int drawX = (int) x - spriteWidth / 2; // Adjust horizontally
                int drawY = (int) y - spriteHeight / 2; // Adjust vertically

                g2d.drawImage(spriteSheet, drawX + 25, drawY + 25,
                        (int) (drawX + 25 + width * 2), (int) (drawY + 25 + height * 2),
                        srcX, srcY, srcX + spriteWidth, srcY + spriteHeight, null);

                // Update the frame if enough time has passed
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastFrameTime >= ANIMATION_DELAY) {
                    currentFrame = (currentFrame + 1) % 9; // Advance to the next frame till frame 9
                    lastFrameTime = currentTime; // Update lastFrameTime
                }
            } else if (isAlive && isBeingHit) {
                int spriteWidth = spriteSheet.getWidth() / SPRITE_COLUMNS;
                int spriteHeight = spriteSheet.getHeight() / SPRITE_ROWS;
                int srcX = currentFrame * spriteWidth;
                int srcY = 0; // Assuming the animation is on the first row
                int drawX = (int) x - spriteWidth / 2; // Adjust horizontally
                int drawY = (int) y - spriteHeight / 2; // Adjust vertically

                g2d.drawImage(spriteSheet, drawX + 25, drawY + 25,
                        (int) (drawX + 25 + width * 2), (int) (drawY + 25 + height * 2),
                        srcX, srcY, srcX + spriteWidth, srcY + spriteHeight, null);

                // Update the frame if enough time has passed
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastFrameTime >= ANIMATION_DELAY) {
                    currentFrame = (currentFrame + 1) % 5 + 10; // Advance to the next frame till frame 9
                    lastFrameTime = currentTime; // Update lastFrameTime
                }
            }

        }
    }
}
