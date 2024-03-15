import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.*;

public class DummyEnemy extends Enemy {

    //basic enemy that just falls to the ground, doesnt move or attack, but you take damage if you run into it
    
    public DummyEnemy(int x1, int y1, int w1, int h1, int h2){
        super(x1,y1,w1,h1,h2);
        isInvincible=false;
        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    public void tick(GameManager gm){
       
        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);
        
         if(isAlive){
         if(isBeingHit){//isBeinghit blocks from being hit, and turns them orange. (it and the timer is set in player File.)
             if(beingHitTimer<System.currentTimeMillis()){
                 isBeingHit=false;
             }
         }
         if(!doneFalling){
         fall(gm);
         }
     }
         if(health<=0){
             isAlive=false;//if not alive it doesnt render or do collision anymore. 
         }
     }

      public void fall(GameManager gm){
         //down
         Rectangle tempRectangle = new Rectangle((int)x,(int)(y+5+height),(int)width,5);
         ArrayList<Rectangle> arr=gm.getCurrentLevel().collisionArray;
         for(int i=0;i<arr.size();i++){
              if(tempRectangle.intersects(arr.get(i))){ 
                  y= y - 2;
                  doneFalling=true;
              }
              else{
                y=y+2;
              }
          }

    }

    public void render(Graphics2D g2d){
    if(isAlive){

        if(isBeingHit){
            g2d.setColor(Color.red);
        }
        else{
            g2d.setColor(Color.orange);
        }
        g2d.fill(new Rectangle((int)x,(int)y,(int)width,(int)height));
    }
}


}
