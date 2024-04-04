import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.*;

public class BossDoor extends Enemy {
    
    
    public BossDoor(int x, int y, int wid,int len){
        super(x, y, wid, len, 1);
        isInvincible=true;
        doesDamageOnCollision=false;
        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    @Override
    public void tick(GameManager gm) {
        if (hitBox.intersects(gm.player.getHitBox())){
            gm.currLevel = gm.levelList.get(5);
            gm.player.x = 450;
            gm.player.y = 400;
        }
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.pink);
        g2d.fill(hitBox);
        g2d.setColor(Color.black);
        g2d.drawString("Door to boss", x+20, y+20);
    }
}
