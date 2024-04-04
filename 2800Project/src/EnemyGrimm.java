import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;

// Boss Grimm:
// Grimm has 10 HPs
// it creates multiple fireballs to attack player, if hitted by fireballs, player will lose 1 health.

public class EnemyGrimm extends Enemy {
    private ArrayList<Fireball> fireballs;

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }
    private double fireballTimer = 0;
    
    public EnemyGrimm(int x1, int y1, int w1, int h1, int h2) {
        super(x1, y1, w1, h1, h2);
        isInvincible = false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        fireballs = new ArrayList<>();
    }

    @Override
    public void tick(GameManager gm) {
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);

        if (isAlive) {
            if (isBeingHit) {
                if (beingHitTimer < System.currentTimeMillis()) {
                    isBeingHit = false;
                }
            }
            if(System.currentTimeMillis() > fireballTimer){
                fireballs.add(new Fireball((int)(this.x + width/2), (int)(this.y + height/2)));
                fireballTimer = System.currentTimeMillis() + 1000;
            }
        }
        if (health <= 0) {
            isAlive = false;
        }
       
        for(Fireball fireball : fireballs){
            fireball.tick(gm);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d); // Call superclass render method to render the square

        if (isAlive) {
            if (isBeingHit) {
                g2d.setColor(Color.red);
            } else {
                g2d.setColor(Color.orange);
            }
            g2d.fill(new Rectangle((int) x, (int) y, (int) width, (int) height));
        }

        for (Fireball fireball : fireballs) {
            fireball.render(g2d);
        }
    }
    
}
