import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// The attack of Enemy Grimm
// Stop generating after Grimm die

public class Fireball extends Enemy {

    private static final int FIREBALL_SIZE = 20; // Diameter of the fireball
    private final int fixedX = 480; // Fixed x-coordinate
    private final int fixedY = 100; // Fixed y-coordinate
    private Ellipse2D visualCircle;
    private double angle; // Angle in radians
    private double speed = 2.0; // Speed of the fireball

    private static final int SPRITE_COLUMNS = 4; // Number of columns in the spritesheet
    private static final int SPRITE_ROWS = 5; // Number of rows in the spritesheet
    private static final int ANIMATION_FRAME_COUNT = SPRITE_COLUMNS;// Number of frames in the
                                                                    // animation
    private static final int ANIMATION_DELAY = 100; // Delay between each frame (milliseconds)
    private BufferedImage[] fireballFrames; // Array to hold the frames of the fireball animation
    private int currentFrame = 0; // Current frame index
    private long lastFrameTime; // Time of the last frame update
    private int frameWidth; // Width of each frame in the spritesheet
    private int frameHeight; // Height of each frame in the spritesheet

    public Fireball(int x, int y) {
        // Fireballs don't need width, height, or health, so we pass 0 for these
        // parameters
        super(x, y, 0, 0, 0);
        isInvincible = true;
        hitBox = new Rectangle(fixedX, fixedY, FIREBALL_SIZE, FIREBALL_SIZE);
        createVisualCircle();
        generateRandomAngle();
        loadSpriteSheet("lib/fireball.png");
        lastFrameTime = System.currentTimeMillis();
    }

    private void loadSpriteSheet(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                BufferedImage spriteSheet = ImageIO.read(inputStream);
                frameWidth = spriteSheet.getWidth() / SPRITE_COLUMNS;
                frameHeight = spriteSheet.getHeight() / SPRITE_ROWS;
                fireballFrames = new BufferedImage[ANIMATION_FRAME_COUNT];
                int frameIndex = 0;
                for (int col = 0; col < SPRITE_COLUMNS; col++) {
                    fireballFrames[frameIndex] = spriteSheet.getSubimage(col * frameWidth, 2 * frameHeight, frameWidth,
                            frameHeight);
                    frameIndex++;
                }
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void tick(GameManager gm) {
        move();
        updateAnimationFrame();
    }

    @Override
    public void render(Graphics2D g2d) {
        if (fireballFrames != null) {

            int xPos = (int) hitBox.getX();
            int yPos = (int) hitBox.getY();
            int scaledWidth = (int) (frameWidth * 4);
            int scaledHeight = (int) (frameHeight * 4);
            g2d.drawImage(fireballFrames[currentFrame], xPos, yPos-20, scaledWidth, scaledHeight, null);
        } else {
            g2d.setColor(Color.RED);
            g2d.fill(hitBox);
        }
    }

    private void createVisualCircle() {
        visualCircle = new Ellipse2D.Double(fixedX, fixedY, FIREBALL_SIZE, FIREBALL_SIZE);
    }

    // Generate a random angle for the fireball's movement direction
    private void generateRandomAngle() {
        Random random = new Random();
        angle = random.nextDouble() * 2 * Math.PI; // Random angle between 0 and 2*PI (in radians)
    }

    // Move the fireball based on its current angle and speed
    private void move() {
        double deltaX = speed * Math.cos(angle);
        double deltaY = speed * Math.sin(angle);
        visualCircle.setFrame(visualCircle.getX() + deltaX, visualCircle.getY() + deltaY, FIREBALL_SIZE, FIREBALL_SIZE);
        hitBox.setLocation((int) visualCircle.getX(), (int) visualCircle.getY());
    }

    private void updateAnimationFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= ANIMATION_DELAY) {
            currentFrame = (currentFrame + 1) % ANIMATION_FRAME_COUNT;
            lastFrameTime = currentTime;
        }
    }
}