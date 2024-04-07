import java.awt.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StalagtiteTrap extends Enemy {

    private BufferedImage sprite;
    boolean isFalling = false;
    Polygon visualPolygon;
    private int currentFrame = 0;
    private long lastFrameTime;
    // simple trap, is invicible.
    private static final int ANIMATION_DELAY = 100; // Delay between each frame (milliseconds)

    public StalagtiteTrap(int x1, int y1, int w1, int h1, int h2) {
        // width,height and health are useless for this.
        super(x1, y1, w1, h1, h2);
        isInvincible = true;
        hitBox = new Rectangle((int) x + 5, (int) y, 10, 25);
        loadSprite("./lib/fallingEnemy.png"); // Load the sprite
    }

    private void loadSprite(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                sprite = ImageIO.read(inputStream);
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading sprite: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tick(GameManager gm) {
        hitBox = new Rectangle((int) x + 5, (int) y, 10, 25);

        if (Math.abs(gm.player.x - x) < 50) {// falls if near the player.
            isFalling = true;
        }
        if (isFalling) {// falls, breaks when it hits the ground.
            Rectangle tempRectangle = new Rectangle((int) x, (int) (y + 5 + height), (int) width, 5);
            ArrayList<Rectangle> arr = gm.getCurrentLevel().collisionArray;
            for (int i = 0; i < arr.size(); i++) {
                if (tempRectangle.intersects(arr.get(i))) {
                    isFalling = false;
                    isAlive = false;
                }
            }
        }
        if (isFalling) {
            y = y + 6;
        }
    }

    public void render(Graphics2D g2d) {
        // int[] xVals = new int[] { (int) x, (int) x + 10, (int) x + 20 };
        // int[] yVals = new int[] { (int) y, (int) y + 30, (int) y };
        // visualPolygon = new Polygon(xVals, yVals, 3);// makes, then prints a triangle

        // g2d.setColor(Color.GREEN);
        // g2d.fill(visualPolygon);

        if (sprite != null) {
            int frameWidth = sprite.getWidth() / 6; // 6 frames in the sprite sheet
            int frameHeight = sprite.getHeight();

            // Calculate the source coordinates for the current frame
            int srcX = (currentFrame % 2) * frameWidth;

            int scaledFrameWidth = frameWidth * 2;
            int scaledFrameHeight = frameHeight * 2;
            // Draw the current frame
            g2d.drawImage(sprite, (int) x, (int) y, (int) x + scaledFrameWidth, (int) y + scaledFrameHeight,
                    srcX, 0, srcX + frameWidth, frameHeight, null);

            // Update current frame for the next render
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameTime >= ANIMATION_DELAY * 2) {
                currentFrame = (currentFrame + 1) % 2;
                lastFrameTime = currentTime; // Update lastFrameTime
            }
        }
    }

}
