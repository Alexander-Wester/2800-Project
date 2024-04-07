
// Boss2.java:
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Boss2 extends Enemy {
    private BufferedImage spriteSheet;
    private final int SPRITE_COLUMNS = 10; // Number of columns in the spritesheet
    private final int SPRITE_ROWS = 1; // Number of rows in the spritesheet
    private int currentFrame = 0; // Current frame for animation
    private long lastFrameTime; // Time of the last frame update
    private final int ANIMATION_DELAY = 100; // Delay between each frame (milliseconds)

    private ArrayList<Fireball2> fireballs;

    public ArrayList<Fireball2> getFireballs() {
        return fireballs;
    }

    public Boss2(int x1, int y1, int w1, int h1, int h2) {
        super(x1, y1, w1, h1, h2);
        isInvincible = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        fireballs = new ArrayList<>();
        loadSpriteSheet("lib/boss2.png");
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
            int spriteWidth = spriteSheet.getWidth() / SPRITE_COLUMNS;
            int spriteHeight = spriteSheet.getHeight() / SPRITE_ROWS;
            int srcX, srcY;

            // Otherwise, display the regular animation from the 3rd to 4th row
            srcX = (currentFrame % SPRITE_COLUMNS) * spriteWidth;
            srcY = ((currentFrame / SPRITE_COLUMNS) % 1) * spriteHeight; // Start from
                                                                         // 3rd row, end
                                                                         // at 4th row

            g2d.drawImage(spriteSheet, (int) x - 20, (int) y - 30, (int) (x - 20 + width * 1.5),
                    (int) (y - 30 + height * 1.5),
                    srcX, srcY, srcX + spriteWidth, srcY + spriteHeight, null);

            // Update the frame if enough time has passed
            long currentTime = System.currentTimeMillis();
            if (!isBeingHit && currentTime - lastFrameTime >= ANIMATION_DELAY) {
                currentFrame = (currentFrame + 1) % (SPRITE_COLUMNS); // End at the 4th row
                lastFrameTime = currentTime; // Update lastFrameTime
            }
            g2d.fill(new Rectangle((int) x, (int) y, (int) width, (int) height));
        }

        for (Fireball2 fireball : fireballs) {
            fireball.render(g2d);
        }
    }
}
