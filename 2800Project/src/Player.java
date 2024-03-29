import java.awt.*;
import java.util.ArrayList;

public class Player extends GameObject{

    private int playerInputVeloX; //player input's portion of the player's velocity
	private int playerInputVeloY;  

    private int playerVeloX;    //players' total velocity after all factors. 
	private int playerVeloY; 
    
    private boolean keyA = false;
    private boolean keyD = false;

    private int arcX;   //angle calculations for sword attack
    private int arcY;
    private Polygon attackTriangle;

    private boolean isAttackOnCooldown = false;//attack information
    private boolean isAttackOnline = false;
    private double attackAngle;
    private long attackTimer = 0;

    private long jumpTimer; 
    private boolean jumpActive = false;
    private boolean jumpAllowed = true;

    private int health=3;   //health stuff
    private long IFrames;
    private boolean canBeHit = true;    //temp invincibility after being hit
    private Rectangle hitBox;

    private long deathMessageTimer; //calcs how long to display "you died" message

    public Player(){
       super(450,400,30,60);
    }

    public void tick(GameManager gm){

        hitBox = new Rectangle((int)x,(int)y,(int)width,(int)height);//update hitbox for collision
        
        playerVeloCalc();
       
        if(System.currentTimeMillis() >= attackTimer && isAttackOnline){//checks if the attack hitbox and render should still be active
            isAttackOnline=false;
        }

        if(System.currentTimeMillis() <= attackTimer){//ditto
            isAttackOnline = true;
        }
        
        playerCollision(gm);//lets you stand on rects from the collisionArray from the current level

        if(x<10 && gm.getCurrentLevel().leftLevel==null && playerVeloX<0){//Can't enter null levels
            playerVeloX=0;
        }
        if(x>920 && gm.getCurrentLevel().rightLevel==null && playerVeloX>0){//Can't enter null levels
            playerVeloX=0;
        }

        x += playerVeloX;//changes position based on velos
        y += playerVeloY;

        swapLevel(gm); //if close to side, change level
        detectHits(gm);//detects sword on enemy, detects ememies colliding with you. 

    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.blue);//render character
		g2d.fillRect((int)x,(int)y, 30, 60);

        if(isAttackOnline){//print attack hitbox
			//printing attack hitbox. the animation for the attack should eventually go here, likely. 
			g2d.setColor(Color.yellow);
            g2d.fillPolygon(attackTriangle);
		}

        g2d.setColor(Color.RED);//print level title
        for(int i=0;i<health;i++){
            g2d.fillRect(50+25*(i+1), 50, 20, 20);
        }

        if(System.currentTimeMillis()<deathMessageTimer){//print death message
            g2d.setColor(Color.red);
            g2d.drawString("YOU DIED", 400, 150);

        }
    }

    public void playerInputVeloX(int x){//part of velo calc. I could probably find a way to skip this step but it works for now 
        playerInputVeloX = x;   
    }
    public void playerInputVeloY(int y){//part of velo calc. ditto
        playerInputVeloY = y;   
    }

    public void playerVeloCalc(){
        playerVeloX = playerInputVeloX;//starts with player input
        playerVeloY = playerInputVeloY;

        if(System.currentTimeMillis() > jumpTimer +1000){//adds upward velo based on the point of the jump
            jumpActive = false;
        }
        if(jumpActive){
            playerVeloY += (int)(-5*(-2*(((double)(System.currentTimeMillis()-jumpTimer)/500.0)-1)+2));//jump calc
        }
        playerVeloY+=8;//gravity
    }

    public void swingSword(int x2, int y2){//math to make a sword swing hitbox
        if(isAttackOnCooldown){
            return;
        }

        int diffX = ((int)x - x2 - 15)*-1;
        int diffY = ((int)y - y2 - 15);

       // System.out.println("diffx and y: " + diffX + " " + diffY);

        attackAngle  = Math.atan((double)diffY/diffX);
        if(diffX<0 && diffY>0){
            //System.out.println("here1");
            attackAngle = Math.PI + attackAngle;
        }
        if(diffX<0 && diffY<0){
            attackAngle = Math.PI + attackAngle;
        }
        else if(diffX>0 && diffY<0){
            attackAngle = 2*Math.PI + attackAngle;
        }


        arcX = (int)(x  +15 +  (25 * Math.cos(attackAngle)));
        arcY = (int)(y  +15 - (25 * Math.sin(attackAngle)));

        //System.out.println(20*Math.cos(attackAngle) + " " + 20*Math.sin(attackAngle));
        //System.out.println("Your POS: " + (int)(x+15) + " " + (int)(y+15) + " arc pos: " + arcX +" " + arcY);

            //create triangle;
            int arcX2 = (int)(arcX +  (100 * Math.cos((attackAngle)+(15*Math.PI/180))));
            int arcY2 = (int)(arcY - (100 * Math.sin((attackAngle)+(15*Math.PI/180))));
            int arcX3 = (int)(arcX +  (100 * Math.cos((attackAngle)-(15*Math.PI/180))));
            int arcY3 = (int)(arcY - (100 * Math.sin((attackAngle)-(15*Math.PI/180))));

            int[] triangleXvalues = new int[]{arcX,arcX2,arcX3};
            int[] triangleYvalues = new int[]{arcY,arcY2,arcY3};


            attackTriangle = new Polygon(triangleXvalues,triangleYvalues,3);

        attackTimer = System.currentTimeMillis() + 450;

    }

    public int getArcX(){//random getters needed because of bad formatting at the start of this project. may be refactored later
        return arcX;
    }
    public int getArcY(){
        return arcY;
    }
    public double getAttackAngle(){
        return this.attackAngle;
    }
    public boolean getIsAttackOnline(){
        return isAttackOnline;
    }

    public void jump(){//activating jump and its timer. 
        //System.out.println("Hi");
        if(jumpAllowed){
            jumpAllowed=false;
        jumpActive = true;
        jumpTimer = System.currentTimeMillis();
        }
    }





    public void setKeyA(boolean b){//random getters needed because of bad formatting at the start of this project. may be refactored later
        keyA = b;
    }
    public boolean getKeyA(){
        return keyA;
    }
    public void setKeyD(boolean b){
        keyD = b;
    }
    public boolean getKeyD(){
        return keyD;
    }
    public void swapLevel(GameManager gm){//simple if close enough to the side, shift levels. 
        if(x<5){
            gm.setCurrentLevel(gm.getCurrentLevel().leftLevel);
            x=910;
        }
        if(x>925){
            gm.setCurrentLevel(gm.getCurrentLevel().rightLevel);
            x=20;
        }
    }

    public void detectHits(GameManager gm){
        //if sword is in enemy hitbox, they take damage
        ArrayList<Enemy> tempEnemyList = gm.getCurrentLevel().enemyList;
        for(int i=0;i<tempEnemyList.size();i++){
            if (isAttackOnline && this.attackTriangle.intersects(tempEnemyList.get(i).getHitBox())){
                tempEnemyList.get(i).hitLanded();
            }

            if(System.currentTimeMillis()>IFrames){
                canBeHit=true;
            }
           // System.out.println("i is " + i);
           //if you are in enemy hitbox, you take damage
            if(hitBox.intersects(tempEnemyList.get(i).getHitBox()) && canBeHit && tempEnemyList.get(i).isAlive){
                health--;
                if(health<=0){
                    death(gm);
                }
                canBeHit=false;
                IFrames = System.currentTimeMillis() + 1000;
                //if there is a unique enemy hit effect, it will be called here, abstract method defined in Enemy and actual method
                //defined in the unique enemy file (eg knockback, more than one hit, etc. )
            }
        }
    }

    public void death(GameManager gm){//called if health<0, resets game basically. can be changed to respawn properly later. 
        gm.reset();
        health=3;
        x=450;
        y=400;
        deathMessageTimer=System.currentTimeMillis() + 3000;
    }
    

    public void playerCollision(GameManager gm){
        
        //detects collisions, one for each side, plus some jump consistency stuff.

        ArrayList<Rectangle> arr = gm.getCurrentLevel().collisionArray;
         //UP
         Rectangle tempRectangle = new Rectangle((int)x,(int)y-5,30,5);
         for(int i=0;i<arr.size();i++){
             if(tempRectangle.intersects(arr.get(i)) && playerVeloY<0){ 
                 playerVeloY=0;//stops you from moving into the object
                 y=arr.get(i).y+arr.get(i).height+2;//reset just outside to avoid clipping
                 jumpActive=false;//after hitting your head on something you no longer get any upward velocity from jump
             }
         } 
        //left
       tempRectangle = new Rectangle((int)x-5,(int)y,5,60);
        for(int i=0;i<arr.size();i++){
            if(tempRectangle.intersects(arr.get(i)) && playerVeloX<0){ 
                playerVeloX=0;
                x=arr.get(i).x+arr.get(i).width+2;
            }
        }
        //down
        tempRectangle = new Rectangle((int)x,(int)y+70,30,5);
        for(int i=0;i<arr.size();i++){
            if(tempRectangle.intersects(arr.get(i)) && playerVeloY>0){ 
                //System.out.print(".");
                playerVeloY=0;
                y=arr.get(i).y-height-2;
            }
        }
        //JUMP
        tempRectangle = new Rectangle((int)x,(int)y+70,(int)height,10);//helps only jump when on the ground. 
        for(int i=0;i<arr.size();i++){
            if(tempRectangle.intersects(arr.get(i))){ 
              jumpAllowed=true;
              break;
            }
            else{
                jumpAllowed=false;
            }     
        }
             //right
        tempRectangle = new Rectangle((int)x+35,(int)y,5,60);
        for(int i=0;i<arr.size();i++){
            if(tempRectangle.intersects(arr.get(i)) && playerVeloX>0){ 
                //System.out.print(".");
                playerVeloX=0;
                x=arr.get(i).x-width-2;
            }
        }
       
    }
}
