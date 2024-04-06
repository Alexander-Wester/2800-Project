import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Fireball2 extends Enemy {

    private static final int FIREBALL_SIZE = 20;
    private final int fixedX = 480;
    private final int fixedY = 100;
    private Ellipse2D visualCircle;
    private double angleToPlayer;
    private double speed = 2.0;

    public Fireball2(GameManager gm) {
        super(0, 0, 0, 0, 0);
        isInvincible = true;
        hitBox = new Rectangle(fixedX, fixedY, FIREBALL_SIZE, FIREBALL_SIZE);
        createVisualCircle();
        aimAtPlayer(gm.player);
    }
    

    @Override
    public void tick(GameManager gm) {
        move();
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fill(visualCircle);
    }

    private void createVisualCircle() {
        visualCircle = new Ellipse2D.Double(fixedX, fixedY, FIREBALL_SIZE, FIREBALL_SIZE);
    }

    private void aimAtPlayer(GameObject player) {
        double dx = player.x - visualCircle.getX();
        double dy = player.y - visualCircle.getY();
        angleToPlayer = Math.atan2(dy, dx); // Calculate the angle to the player's position
    }

    private void move() {
        // Move the fireball towards the player's position
        double deltaX = speed * Math.cos(angleToPlayer);
        double deltaY = speed * Math.sin(angleToPlayer);
        visualCircle.setFrame(visualCircle.getX() + deltaX, visualCircle.getY() + deltaY, FIREBALL_SIZE, FIREBALL_SIZE);
        hitBox.setLocation((int) visualCircle.getX(), (int) visualCircle.getY());
    }
    
}
