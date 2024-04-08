import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Button extends Enemy {
    private BufferedImage[] sprites; // Array to store button sprites
    private int currentFrame = 0; // Current frame of the animation

    public Button(float x, float y, float width, float height) {
        super(x, y, width, height, 1);
        isAlive = true;
        isInvincible = false;
        doesDamageOnCollision = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        // Load button spritesheet
        loadSprites();
    }

    private void loadSprites() {
        sprites = new BufferedImage[4]; // 4 columns in the spritesheet
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("lib/Button.png"));
            int spriteWidth = spritesheet.getWidth() / 4; // Calculate width of each sprite
            int spriteHeight = spritesheet.getHeight(); // Height of spritesheet

            // Split spritesheet into individual sprites
            for (int i = 0; i < 4; i++) {
                sprites[i] = spritesheet.getSubimage(i * spriteWidth, 0, spriteWidth, spriteHeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(GameManager gm) {
        if (health <= 0) {
            isAlive = false;
            isInvincible = true;
        }
    }

    public void render(Graphics2D g2d) {
        if (isAlive) {
            int spriteWidth = sprites[0].getWidth() / 4; // Calculate width of each sprite
            int spriteHeight = sprites[0].getHeight(); // Height of spritesheet
            g2d.drawImage(sprites[currentFrame], (int) x, (int) y, spriteWidth * 5,
                    spriteHeight * 2, null);
        }
        // g2d.setColor(Color.DARK_GRAY);
        // g2d.fill(hitBox);
    }

    public void onHitByFireball() {
        // Animate button sprite by advancing to the next frame
        if (currentFrame < 3) { // Check if not already at the last frame
            currentFrame++;
        }
    }
}
