import java.awt.Graphics2D;
import java.awt.*;

public class Enemy extends GameObject{
    int health;
    Rectangle hitBox;

    boolean isBeingHit=false;
    long beingHitTimer;

    boolean isAlive = true;
    boolean doneFalling;

    boolean isInvincible;//used for traps or other hazards that cannot be killed. 

    //basic enemy. can even be made general if needed
    //NOTE: a fair bit of the enemies' collisions with player is in the play function

    public Enemy(float x, float y, float width, float height,int thisHealth){
       super(x,y,width,height);
       health = thisHealth;
    }

    public Rectangle getHitBox(){
        return this.hitBox;
    }

    //NOTE: all enemies hurt you if you collide with their hitbox. feel free to change this if needed.
    public void hitLanded(){
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
