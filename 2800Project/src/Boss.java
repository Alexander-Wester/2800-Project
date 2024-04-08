import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Boss extends Enemy{
    
    BufferedImage golemImage;
    BufferedImage golemImage2;

    BufferedImage blueImage;
    BufferedImage orangeImage;

    boolean blueCrystalAlive=true;
    boolean orangeCrystalAlive=true;

    boolean isAttackActive = false;
    long attackCooldownTimer;
    BossSwordAttack sword = null;

    public Boss(){
        super(430,200,100,200,10);
    hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
    orangeImage = loadImage("./lib/Topaz.png");
    blueImage = loadImage("./lib/Sapphire.png");
    golemImage = loadImage("./lib/Golem2.png");
    golemImage2 = loadImage("./lib/Golem_Core.png");

    }


    public void tick(GameManager gm){
        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);

        if(isAlive){

        if(gm.player.x>x-140 && gm.player.x<x+width+140){
            if(System.currentTimeMillis()>attackCooldownTimer){
                if(gm.player.x<x+50){
                    sword = new BossSwordAttack((int)x-150);
                }
                else{
                    sword = new BossSwordAttack((int)x+100);
                }
                gm.currLevel.enemyList.add(sword);
                isAttackActive=true;
                attackCooldownTimer= System.currentTimeMillis() + 3000;
            }
        }

    if(blueCrystalAlive || orangeCrystalAlive){
        isInvincible=true;
    }
    else{
        isInvincible=false;
    }

    if (beingHitTimer < System.currentTimeMillis()) {
        isBeingHit = false;
    }
    
    if(isAlive && gm.player.getIsAttackOnline()){
        if(gm.player.getAttackTriangle().intersects(hitBox)){
            if(gm.player.orbActive){
                if(gm.player.orbColor==Color.blue && blueCrystalAlive){
                    this.blueCrystalAlive=false;
                    gm.currLevel.enemyList.add(new BossBeamAttack(true,0));
                }
                if(gm.player.orbColor==Color.orange && orangeCrystalAlive){
                    this.orangeCrystalAlive=false;
                    gm.currLevel.enemyList.add(new BossBombAttack());
                }
            }
        }
    }

    if (health <= 0) {
        isAlive = false;
    }
  }
 
  if(isAttackActive){
    if(sword.plzEnd){
       gm.currLevel.enemyList.remove(sword);
        isAttackActive=false;
        sword=null;
    }
}
}

    public void render(Graphics2D g2d){
        if(isAlive){

        if(orangeCrystalAlive || blueCrystalAlive){
        g2d.drawImage(golemImage,(int)x-40, (int)y, (int)width+80, (int)height,null);
        }
        else{
            g2d.drawImage(golemImage2,(int)x-40, (int)y, (int)width+80, (int)height,null);
        }
        //g2d.fillRect((int)x, (int)y, (int)width, (int)height);
        //g2d.rotate(Math.toRadians(45));
        if(orangeCrystalAlive){
        //g2d.setColor(Color.orange);
        //g2d.fillRect((int)x-40, (int)y-40, 20, 20);
        g2d.drawImage(orangeImage,(int)x-40, (int)y-40, null);
        }
        if(blueCrystalAlive){
        //g2d.setColor(Color.blue);
        //g2d.fillRect((int)x+120, (int)y-40, 20, 20);
        g2d.drawImage(blueImage,(int)x+120, (int)y-40,null);
        }

        g2d.setColor(Color.gray);
        g2d.fillRect(440,150,80,20);

        g2d.setColor(Color.red);
        g2d.fillRect(442, 152, 8 * health, 16);

    }
    
}
public BufferedImage loadImage(String path){
    try{
        return ImageIO.read(new File(path));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

}
