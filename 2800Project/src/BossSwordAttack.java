import java.awt.*;

public class BossSwordAttack extends Enemy {

    long swordAttackTimer;
    public boolean plzEnd = false;

    public BossSwordAttack(int x){
        super(x,150,150,40,5);
        isInvincible=true;
        doesDamageOnCollision = true;
        swordAttackTimer = System.currentTimeMillis() + 1000;
        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    public void tick(GameManager gm){

        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);

        if(System.currentTimeMillis()>swordAttackTimer){
            y+=8;
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
        g2d.fillRect((int)x,(int)y,(int)width,(int)height);
    }
}
