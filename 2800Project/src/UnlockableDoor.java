import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class UnlockableDoor extends Enemy {
    private BufferedImage doorSprite;
    private Enemy condition;

    public UnlockableDoor(int x, int y, int w, int h, Enemy condition) {
        super(x, y, w, h, 0);
        isAlive = false;
        isInvincible = true;
        this.condition = condition;
        hitBox = new Rectangle(x, y, w, h);
        loadSpriteSheet("lib/doors.png");
    }

    private void loadSpriteSheet(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                BufferedImage doorSheet = ImageIO.read(inputStream);
                int frameHeight = doorSheet.getHeight() / 10; // Assuming 7 rows in the spritesheet
                int frameWidth = doorSheet.getWidth();
                doorSprite = doorSheet.getSubimage(0, 0, frameWidth, frameHeight); // Extract 5th row
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tick(GameManager gm) {
        if (checkCondition()) {
            hitBox.setLocation(-200, -200);
        }
    }

    public void render(Graphics2D g2d) {
        if (doorSprite != null) {
            g2d.drawImage(doorSprite, (int) x - 10, (int) y + 30, (int) (width * 1.2), (int) (height * 0.8), null);
        }
        // g2d.setColor(new Color(150, 75, 0));
        // g2d.fill(hitBox);
    }

    public boolean checkCondition() {
        if (condition.isAlive == false) {
            return true;
        }
        return false;
    }
}
