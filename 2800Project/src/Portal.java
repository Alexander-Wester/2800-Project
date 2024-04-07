import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Portal extends Enemy {
    private Ellipse2D portal;
    private Level nextLevel;
    private int xAfter, yAfter;
    private boolean active;

    private Enemy condition;

    private BufferedImage portalSpritesheet;
    private int currentFrame = 0;
    private int animationDelay = 10; // Delay between animation frames
    private int animationCounter = 0;

    public Portal(int x, int y, Level level, int xAfter, int yAfter) {
        super(x, y, 50, 80, 0);
        isInvincible = true;
        isAlive = false;
        nextLevel = level;
        this.xAfter = xAfter;
        this.yAfter = yAfter;
        hitBox = new Rectangle(0, 0, 0, 0);
        this.portal = new Ellipse2D.Double(x, y, width, height);
        active = true;
        loadSpritesheet("portal.png");
    }

    public Portal(int x, int y, Level level, int xAfter, int yAfter, Enemy condition) {
        this(x, y, level, xAfter, yAfter);
        this.condition = condition;
        active = false;
    }

    private void loadSpritesheet(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                portalSpritesheet = ImageIO.read(inputStream);
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tick(GameManager gm) {
        if (active) {
            Player player = gm.player;
            if (portal.intersects(player.rect())) {
                gm.currLevel = nextLevel;
                player.x = xAfter;
                player.y = yAfter;
            }
        } else {
            active = checkCondition();
        }

        // Animation update
        animationCounter++;
        if (animationCounter >= animationDelay) {
            animationCounter = 0;
            currentFrame = (currentFrame + 1) % 6; // 2 rows * 3 columns = 6 frames
        }
    }

    public void render(Graphics2D g2d) {
        // if (active) {
        // g2d.setColor(Color.magenta);
        // g2d.fill(portal);
        // }

        if (active) {
            if (portalSpritesheet != null) {
                int frameWidth = portalSpritesheet.getWidth() / 3;
                int frameHeight = portalSpritesheet.getHeight() / 2;
                int row = currentFrame / 3;
                int col = currentFrame % 3;

                g2d.drawImage(portalSpritesheet, (int) x - 25, (int) y, (int) (x - 25 + width * 2), (int) (y + height),
                        col * frameWidth, row * frameHeight, (col + 1) * frameWidth, (row + 1) * frameHeight, null);
            } else {
                g2d.setColor(Color.magenta);
                g2d.fill(portal);
            }
        }
    }

    public boolean checkCondition() {
        if (condition != null) {
            if (condition.isAlive == false) {
                return true;
            }
        }
        return false;
    }
}
