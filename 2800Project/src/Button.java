import java.awt.*;

public class Button extends Enemy{
    public Button(float x, float y, float width, float height) {
        super(x, y, width, height, 1);
        isAlive = true;
        isInvincible = false;
        doesDamageOnCollision = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public void tick(GameManager gm){
        if(health <= 0){
            isAlive = false;
            isInvincible = true;
        }
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.DARK_GRAY);
        g2d.fill(hitBox);
    }
}
