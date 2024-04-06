import java.awt.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// Jumping Enemy:
// Moving up and down, but won't attack player. Player lose 1 health when runs into it

public class JumpingEnemy extends Enemy {
    private BufferedImage enemySpriteSheetGreen;
    private BufferedImage enemySpriteSheetRed;
    private final int ENEMY_SPRITE_COLUMNS = 11;
    private final int ENEMY_SPRITE_ROWS = 1;
    private int currentFrame = 0;
    private long lastFrameTime;
    private static final int ANIMATION_DELAY = 300; // Delay between each frame (milliseconds)

    private boolean isMovingUp = false; // Indicates whether the enemy is moving up or down
    private static final int MOVEMENT_SPEED = 2; // Movement speed of the enemy
    private double jumpTimer;
    private int lowerBound;

    public JumpingEnemy(int x1, int y1, int w1, int h1, int h2) {
        super(x1, y1, w1, h1, h2);
        loadEnemySpriteSheet("jumpEnemyGreen.png");
        isInvincible = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    private void loadEnemySpriteSheet(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                enemySpriteSheetGreen = ImageIO.read(inputStream);
                enemySpriteSheetRed = ImageIO.read(getClass().getResourceAsStream("jumpEnemyRed.png"));
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading enemy spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
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
        if (System.currentTimeMillis() > jumpTimer) {
            ArrayList<Rectangle> arr = gm.getCurrentLevel().collisionArray;
            for (Rectangle rectangle : arr) {
                if (hitBox.intersects(rectangle)) {
                    if (isMovingUp) {
                        isMovingUp = false;
                    } else {
                        isMovingUp = true;
                    }
                    jumpTimer = System.currentTimeMillis() + 250;
                    lowerBound = (int) y;
                }
            }
        }
        if (isMovingUp) {
            y -= MOVEMENT_SPEED;
            if (y < 0 || y < lowerBound - 150) {
                y += MOVEMENT_SPEED; // Ensure the enemy stays within the lower limit
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
                // Calculate sprite width and height
                int spriteWidth = enemySpriteSheetRed.getWidth() / ENEMY_SPRITE_COLUMNS;
                int spriteHeight = enemySpriteSheetRed.getHeight() / ENEMY_SPRITE_ROWS;

                // Calculate scaled sprite width and height
                int scaledSpriteWidth = (int) (spriteWidth * 5);
                int scaledSpriteHeight = (int) (spriteHeight * 5.5);

                // Calculate source coordinates for current frame
                int srcX = currentFrame * spriteWidth;
                int srcY = 0; // Since there's only one row in the spritesheet

                // Draw the current frame
                g2d.drawImage(enemySpriteSheetRed, (int) x, (int) y - 75, (int) x + scaledSpriteWidth,
                        (int) y - 75 + scaledSpriteHeight,
                        srcX, srcY, srcX + spriteWidth, srcY + spriteHeight, null);

                // Update the frame if enough time has elapsed
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastFrameTime >= ANIMATION_DELAY) {
                    currentFrame = (currentFrame + 1) % 7;
                    lastFrameTime = currentTime;
                }
            } else {
                // Calculate sprite width and height
                int spriteWidth = enemySpriteSheetGreen.getWidth() / ENEMY_SPRITE_COLUMNS;
                int spriteHeight = enemySpriteSheetGreen.getHeight() / ENEMY_SPRITE_ROWS;

                // Calculate scaled sprite width and height
                int scaledSpriteWidth = (int) (spriteWidth * 5);
                int scaledSpriteHeight = (int) (spriteHeight * 5.5);

                // Calculate source coordinates for current frame
                int srcX = currentFrame * spriteWidth;
                int srcY = 0; // Since there's only one row in the spritesheet

                // Draw the current frame
                g2d.drawImage(enemySpriteSheetGreen, (int) x, (int) y - 75, (int) x + scaledSpriteWidth,
                        (int) y - 75 + scaledSpriteHeight,
                        srcX, srcY, srcX + spriteWidth, srcY + spriteHeight, null);

                // Update the frame if enough time has elapsed
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastFrameTime >= ANIMATION_DELAY) {
                    currentFrame = (currentFrame + 1) % 7;
                    lastFrameTime = currentTime;
                }
            }
            // g2d.fill(new Rectangle((int) x, (int) y, (int) width, (int) height));

        }
    }
}
