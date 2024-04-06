import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PlayerFireball {
    public Ellipse2D.Double hitBox;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double startTime;
    private BufferedImage fireSpriteSheet;
    private final int FIRE_SPRITE_COLUMNS = 3;
    private final int FIRE_SPRITE_ROWS = 5;
    private final int NUM_FRAMES_FIREBALL = FIRE_SPRITE_COLUMNS * FIRE_SPRITE_ROWS;
    private static final int ANIMATION_DELAY = 100; // Delay between each frame (milliseconds)
    private int fireballCurrentFrame = 0;
    private int currentFrame = 0;
    private long lastFrameTime;

    public PlayerFireball(double x, double y, double direction, double time) {
        this.x = x;
        this.y = y;
        this.startTime = time;
        double speed = 7.0;
        this.dx = Math.cos(direction) * speed;
        this.dy = Math.sin(direction) * speed;
        hitBox = new Ellipse2D.Double(this.x, this.y, 25, 25);
        loadSpriteSheet("fireball.png");
    }

    private void loadSpriteSheet(String path) { // loading the spritesheet
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                fireSpriteSheet = ImageIO.read(inputStream);
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tick(GameManager gm) {
        x += dx;
        y += dy;
        hitBox = new Ellipse2D.Double(x, y, 25, 25);
        ArrayList<Rectangle> arr = gm.getCurrentLevel().collisionArray;
        for (Rectangle rectangle : arr) {
            if (hitBox.intersects(rectangle)) {
                Player player = (Player) gm.gameObjects.get(0);
                player.resetFireball();
                return;
            }
        }
        ArrayList<Enemy> enemyArr = gm.getCurrentLevel().enemyList;
        for (Enemy enemy : enemyArr) {
            if (hitBox.intersects(enemy.hitBox) && enemy.isAlive) {
                enemy.hitLanded();
                Player player = (Player) gm.gameObjects.get(0);
                player.resetFireball();
                return;
            }
        }
        if (System.currentTimeMillis() >= startTime + 2000) {
            Player player = (Player) gm.gameObjects.get(0);
            player.resetFireball();
        }

    }

    public void render(Graphics2D g2d) {
        // g2d.setColor(Color.orange);
        // g2d.fill(hitBox);

        int fireballSpriteWidth = fireSpriteSheet.getWidth() / FIRE_SPRITE_COLUMNS;
        int fireballSpriteHeight = fireSpriteSheet.getHeight() / FIRE_SPRITE_ROWS;

        // Increase the size of the fireball
        int scaledFireballWidth = (int) (fireballSpriteWidth * 3.5);
        int scaledFireballHeight = (int) (fireballSpriteHeight * 3.5);

        int fireSrcX = (fireballCurrentFrame % FIRE_SPRITE_COLUMNS) * fireballSpriteWidth;
        int fireSrcY = (fireballCurrentFrame / FIRE_SPRITE_COLUMNS) * fireballSpriteHeight;

        g2d.drawImage(fireSpriteSheet, (int) x, (int) y,
                (int) x + scaledFireballWidth, (int) y + scaledFireballHeight,
                fireSrcX, fireSrcY, fireSrcX + fireballSpriteWidth, fireSrcY + fireballSpriteHeight, null);
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= ANIMATION_DELAY) {
            currentFrame = (currentFrame + 1) % NUM_FRAMES_FIREBALL;
            lastFrameTime = currentTime; // Update lastFrameTime
        }
    }
}
