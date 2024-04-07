import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LavaTrap extends Enemy {
    private BufferedImage lavaSpriteSheet;

    public LavaTrap(int x, int y, int width, int height) {
        super(x, y, width, height, 0);
        isInvincible = true;
        hitBox = new Rectangle(x, y, width, height);
        loadSpriteSheet("lava.png");
    }

    private void loadSpriteSheet(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                lavaSpriteSheet = ImageIO.read(inputStream);
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tick(GameManager gm) {
        Player player = (Player) gm.gameObjects.get(0);
        if (hitBox.intersects(player.rect())) {
            // player.jumpAllowed = true; //variable is private
            player.jump();
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (lavaSpriteSheet != null) {
            int spriteWidth = lavaSpriteSheet.getWidth(); // Divided by 2 for two columns
            int spriteHeight = lavaSpriteSheet.getHeight();

            int srcX = 0; // Set to 0 for the first frame

            int adjustedSpriteHeight = (int) (spriteHeight * 1.5);
            int adjustedSpriteWidth = (int) (spriteWidth * 1.7);

            g2d.drawImage(lavaSpriteSheet, (int) x, (int) y, (int) (x + adjustedSpriteWidth),
                    (int) (y + adjustedSpriteHeight),
                    srcX, 0, srcX + spriteWidth, spriteHeight, null);
        } else {
            // If spritesheet is not loaded, render a simple rectangle as before
            g2d.setColor(Color.red);
            g2d.fill(hitBox);
        }
    }

}
