import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class BossDoor extends Enemy {
    private BufferedImage doorSprite;
    private BufferedImage doorText;

    public BossDoor(int x, int y, int wid, int len) {
        super(x, y, wid, len, 1);
        isInvincible = true;
        doesDamageOnCollision = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        loadSpriteSheet("./lib/doors.png");
    }

    @Override
    public void tick(GameManager gm) {
        if (hitBox.intersects(gm.player.getHitBox())) {// && gm.player.keyAmount() >= 2 DISABLED FOR TESTING
            gm.currLevel = gm.levelList.get(5);
            gm.player.x = 450;
            gm.player.y = 400;
        }
    }

    private void loadSpriteSheet(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                BufferedImage doorSheet = ImageIO.read(inputStream);
                int frameHeight = doorSheet.getHeight() / 10; // Assuming 7 rows in the spritesheet
                int frameWidth = doorSheet.getWidth();
                doorSprite = doorSheet.getSubimage(0, 0, frameWidth, frameHeight); // Extract 5th row
                doorText = ImageIO.read(getClass().getResourceAsStream("./lib/doorToBoss.png"));
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g2d) {
        if (doorSprite != null) {
            g2d.drawImage(doorSprite, (int) x - 10, (int) y + 30, (int) (width * 1.2), (int) (height * 0.8), null);

            int textWidth = doorText.getWidth();
            int textHeight = doorText.getHeight();

            g2d.drawImage(doorText, (int) (x - 150), (int) (y + 15), (int) (x + textWidth * 0.005),
                    (int) (y + textHeight * 0.005),
                    null);
            // g2d.setColor(Color.black);
            // g2d.drawString("Door to boss", x + 20, y + 20);
        } // else {
          // g2d.setColor(Color.pink);
          // g2d.fill(hitBox);
          // g2d.setColor(Color.black);
          // g2d.drawString("Door to boss", x + 20, y + 20);
          // }
    }
}
