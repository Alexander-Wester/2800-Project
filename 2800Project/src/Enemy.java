import java.awt.Graphics2D;
import java.awt.*;

public class Enemy extends GameObject{
    int health;
    Rectangle hitBox;

    boolean isBeingHit=false;
    long beingHitTimer;

    boolean isAlive = true;
    boolean doneFalling;

    boolean isInvincible;

    public Enemy(float x, float y, float width, float height,int thisHealth){
       super(x,y,width,height);
       health = thisHealth;
    }

    public Rectangle getHitBox(){
        return this.hitBox;
    }

    public void hitLanded(){
        //System.out.print(".");
        if(!isBeingHit && isAlive){
        beingHitTimer = System.currentTimeMillis() + 500;
        isBeingHit=true;
        if(!isInvincible){
        health--;
        }
       }
    }

    public void tick(GameManager gm){}//Enemy logic goes here: checking hits, moving the enemy, etc. 

    public void render(Graphics2D g2d){}

    public void onHitEffects(){
    }
}
