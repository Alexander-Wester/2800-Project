import java.awt.*;

public class BossBombAttack extends Enemy{
    
    public boolean root;
    long timer=0;

    boolean windupState=false;
    boolean damageState=false;
    boolean bothStatesOff=true;

    boolean firstTimeSetup=true;
    boolean reposisitionThisCylcle=false;

    
    public BossBombAttack(){
        super(300,0,80,80,1);
        isInvincible=true;
        doesDamageOnCollision=false;
        isAlive=true;
        actualEnemy=false;
       
        hitBox = new Rectangle((int)x-(int)width/2,(int)(y-width/2),(int)width,(int)height);
    }

    @Override
    public void tick(GameManager gm) {
        hitBox = new Rectangle((int)x-(int)width/2+10,(int)(y-width/2)+10,(int)width-20,(int)height-20);

        //generous if to stop unfair damage when renetering: simply resets timer to 0;
        if (gm.player.x < 15 || gm.player.x > 910) {
          timer = System.currentTimeMillis();
          bothStatesOff=true;
          windupState=false;
          damageState=false;
          doesDamageOnCollision=false;
        }
       
        //starts timer
        if(firstTimeSetup){
            timer = System.currentTimeMillis();
            firstTimeSetup=false;
        }

        //timer function for next 3 ifs
        if(bothStatesOff && System.currentTimeMillis()>timer+2500){
            if(!reposisitionThisCylcle){
                fixPosition(gm);
                reposisitionThisCylcle=true;
            }
            windupState=true;
            bothStatesOff=false;
        }
        else if(windupState && System.currentTimeMillis()>timer+3250){
            windupState=false;
            damageState=true;
            doesDamageOnCollision=true;
        }
        else if(damageState && System.currentTimeMillis()>timer+3750){
           reposisitionThisCylcle=false;
            damageState=false;
            doesDamageOnCollision=false;
            bothStatesOff=true;
            timer=System.currentTimeMillis();
        } 
    }

    public void fixPosition(GameManager gm){
        x = gm.player.x + 20;
        y= gm.player.y +20;
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.orange);
        
        if(windupState){
        for(int i=10;i<=80;i=i+10){
           // drawCenteredCircle(g2d, (int)x, (int)y, i);
           g2d.drawOval((int)x-i/2,(int)y-i/2,i,i);
        }
    }
        else if(damageState){
            g2d.fillOval((int)x-40, (int)y-40,80,80);
        }
    }

}
