import java.awt.*;

public class BossOrbGenerator extends Enemy{
    
    Color thisColor;
    

    public BossOrbGenerator(int x, int y, Color c){
        super(x, y, 100, 10, 10);
        thisColor = c;
        isInvincible=true;
        doesDamageOnCollision=false;
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public void tick(GameManager gm){
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        if(gm.player.getHitBox().intersects(hitBox)){
            gm.player.orbActive=true;
            gm.player.orbColor=thisColor;
        }
    }

    public void render(Graphics2D g2d){
        g2d.setColor(thisColor);
        g2d.fillRect((int)x,(int)y,(int)width,(int)height);
    }


}
