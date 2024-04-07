import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class MovingPlatform extends Enemy {
    private int lower;
    private int upper;
    private int speed;
    private boolean movingLower;
    private boolean movingUpper;
    private boolean upDown;
    private BufferedImage platformSprite;

    public MovingPlatform(int x, int y, int width, int height, int lower, int upper, int speed, boolean upDown) {
        super(x, y, width, height, 0);
        hitBox = new Rectangle(x, y, width, height);
        isInvincible = true;
        isAlive = false;
        this.lower = lower;
        this.upper = upper;
        movingLower = false;
        movingUpper = true;
        this.speed = speed;
        this.upDown = upDown;
        loadSprite("./lib/ground.png");
    }

    private void loadSprite(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                platformSprite = ImageIO.read(inputStream);
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading sprite: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tick(GameManager gm) {
        if (!upDown) {// If upDown is false the platform is moving on x-Axis
            if (movingUpper) {
                if (hitBox.getX() <= upper) {
                    int x = (int) (hitBox.getX() + speed);
                    hitBox.setLocation(x, (int) hitBox.getY());
                } else if (hitBox.getX() >= upper) {
                    movingUpper = false;
                    movingLower = true;
                }
            } else if (movingLower) {
                if (hitBox.getX() >= lower) {
                    int x = (int) (hitBox.getX() - speed);
                    hitBox.setLocation(x, (int) hitBox.getY());
                } else if (hitBox.getX() <= lower) {
                    movingLower = false;
                    movingUpper = true;
                }
            }
        } else {// Moves on y-axis if upDown is true
            if (movingUpper) { // Upper lower is kind of reversed for up down since a higher number Y is lower
                               // on the screen
                if (hitBox.getY() <= upper) {
                    int y = (int) (hitBox.getY() + speed);
                    hitBox.setLocation((int) hitBox.getX(), y);
                } else if (hitBox.getY() >= lower) {
                    movingUpper = false;
                    movingLower = true;
                }
            } else if (movingLower) {
                if (hitBox.getY() >= lower) {
                    int y = (int) (hitBox.getY() - speed);
                    hitBox.setLocation((int) hitBox.getX(), y);
                } else if (hitBox.getY() <= lower) {
                    movingUpper = true;
                    movingLower = false;
                }
            }

        }
    }

    public void render(Graphics2D g2d) {
        // Draw platform sprite
        g2d.drawImage(platformSprite, (int) hitBox.getX(), (int) hitBox.getY(), (int) hitBox.getWidth(),
                (int) hitBox.getHeight(), null);
    }
}
