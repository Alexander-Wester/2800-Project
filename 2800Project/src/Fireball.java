import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

// The attack of Enemy Grimm
// Stop generating after Grimm die

public class Fireball extends Enemy {

    private static final int FIREBALL_SIZE = 20; // Diameter of the fireball
    private final int fixedX = 480; // Fixed x-coordinate
    private final int fixedY = 100; // Fixed y-coordinate
    private Ellipse2D visualCircle;
    private double angle; // Angle in radians
    private double speed = 2.0; // Speed of the fireball

    public Fireball() {
        // Fireballs don't need width, height, or health, so we pass 0 for these parameters
        super(0, 0, 0, 0, 0);
        isInvincible = true;
        hitBox = new Rectangle(fixedX, fixedY, FIREBALL_SIZE, FIREBALL_SIZE);
        createVisualCircle();
        generateRandomAngle();
    }

    @Override
    public void tick(GameManager gm) {
        move(); 
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fill(visualCircle);
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
}