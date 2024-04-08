import java.awt.*;
import java.awt.image.BufferedImage;

public class BossSwordAttack extends Enemy {

    long swordAttackTimer;
    public boolean plzEnd = false;

    BufferedImage image;
    BufferedImage mirrored;

    public BossSwordAttack(int x){
        super(x,150,150,40,5);
        isInvincible=true;
        doesDamageOnCollision = false;
        swordAttackTimer = System.currentTimeMillis() + 1000;
        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
        image = ImageLoader.loadImage("./lib/SwordResize.png");
    }

    public void tick(GameManager gm){

        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);

        if(System.currentTimeMillis()>swordAttackTimer){
            doesDamageOnCollision=true;
            y+=10;
            if(y>400){
                plzEnd=true;
            }
        }
    }

    @Override
    public void hitLanded(GameManager gm){
       
       gm.player.bossSwordKnockback=true;
       gm.player.bossSwordKnockbackTimer = System.currentTimeMillis() + 100;
       
       if(gm.player.x+15<960/2-50){
       gm.player.bossSwordKnockbackDirection = -1;
       }
       else{
        gm.player.bossSwordKnockbackDirection = 1;
       }

    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.gray);

        if(x==280){
            g2d.drawImage(image,(int)x+150, (int)y-20, -(int)width, (int)height+40, null);
        }
        else{
            g2d.drawImage(image,(int)x, (int)y-20, (int)width, (int)height+40, null);
        }

       // g2d.drawRect((int)x,(int)y,(int)width,(int)height);
    }
}
