import java.awt.*;

public class BossBeamAttack extends Enemy{
    
    public boolean root;
    long timer=0;

    boolean windupState=false;
    boolean damageState=false;
    boolean bothStatesOff=true;

    int position;

    BossBeamAttack left3 = null;
    BossBeamAttack left2 = null;
    BossBeamAttack left1 = null;
    BossBeamAttack right1 = null;
    BossBeamAttack right2 = null;
    BossBeamAttack right3= null;

    boolean firstTimeSetup=true;
    boolean reposisitionThisCylcle=false;

    
    public BossBeamAttack(boolean r,int p){
        super(300,0,50,500,1);
        position=p;
        isInvincible=true;
        doesDamageOnCollision=false;
        root=r;
       
        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    @Override
    public void tick(GameManager gm) {
        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);

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


        if(root){
            generateAndAddExtraBeams(gm);
            root=false;
        }

        //timer function for next 3 ifs
        if(bothStatesOff && System.currentTimeMillis()>timer+3500){
            if(!reposisitionThisCylcle){
                fixPositions(gm);
                reposisitionThisCylcle=true;
            }
            windupState=true;
            bothStatesOff=false;
        }
        else if(windupState && System.currentTimeMillis()>timer+4500){
            windupState=false;
            damageState=true;
            doesDamageOnCollision=true;
        }
        else if(damageState && System.currentTimeMillis()>timer+6000){
           reposisitionThisCylcle=false;
            damageState=false;
            doesDamageOnCollision=false;
            bothStatesOff=true;
            timer=System.currentTimeMillis();
        } 
    }

    public void generateAndAddExtraBeams(GameManager gm){
       
        left3=new BossBeamAttack(false,-3);
        left2=new BossBeamAttack(false,-2);
       left1 = new BossBeamAttack(false,-1);
       right1 = new BossBeamAttack(false,1);
       right2 = new BossBeamAttack(false,2);
       right3 = new BossBeamAttack(false,3);
       gm.currLevel.enemyList.add(left3);
       gm.currLevel.enemyList.add(left2);
        gm.currLevel.enemyList.add(left1);
        gm.currLevel.enemyList.add(right1);
        gm.currLevel.enemyList.add(right2);
        gm.currLevel.enemyList.add(right3);
    }

    public void fixPositions(GameManager gm){
        this.x = gm.player.x + 200*position;
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.blue);
        
        if(windupState){
        for(int i=(int)x;i<x+50;i=i+8){
            g2d.drawRect(i, 0, 3, 500);
        }
    }
        else if(damageState){
            g2d.fillRect((int)x, (int)y, (int)width,(int)height);
        }
    }




}
