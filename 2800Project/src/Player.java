import java.awt.*;
//import java.awt.image.*;
//import java.awt.event.*;
//import java.awt.image.*;
import java.util.ArrayList;

//import GameCanvas.playerListener;


public class Player extends GameObject{

    private int playerInputVeloX;
	private int playerInputVeloY;  

    private int playerVeloX;
	private int playerVeloY; 
    
    private boolean keyA = false;
    private boolean keyD = false;

    private int arcX;
    private int arcY;

    private boolean isAttackOnCooldown = false;
    private boolean isAttackOnline = false;
    private double attackAngle;
    private long attackTimer = 0;

    private long jumpTimer;
    private boolean jumpActive = false;

    public Player(){
       super(450,400,30,60);
    }

    public int[] getPlayerPos(){
        return new int[]{(int)x,(int)y};
    }

    public void tick(GameManager gm){

        //System.out.print(".");
        playerVeloCalc();
       
        if(System.currentTimeMillis() >= attackTimer && isAttackOnline){
            //System.out.println("Timer ended");
            isAttackOnline=false;
        }

        if(System.currentTimeMillis() <= attackTimer){
            isAttackOnline = true;
        }
        //System.out.println("before " + playerVeloX + " " + playerVeloY);
        
        playerCollision(gm);

        //System.out.println("after " + playerVeloX + " " + playerVeloY);

        x += playerVeloX;
        y += playerVeloY;
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.blue);
		int[] playerPos = getPlayerPos();
		g2d.fillRect(playerPos[0],playerPos[1], 30, 60);

        if(getIsAttackOnline()){
			//System.out.println("Arc printing");
			g2d.setColor(Color.yellow);
			g2d.fillArc(getArcX()-20,getArcY()-25, 100, 100, (int)(getAttackAngle()*180/Math.PI - 15), 30);
		}
    }

    public void playerInputVeloX(int x){
        playerInputVeloX = x;   
    }
    public void playerInputVeloY(int y){
        playerInputVeloY = y;   
    }

    public void playerVeloCalc(){//This method is trivial now, but if we add any speed up tools/powers or enemies that slow you,
        //you can add them to this calculation here.
        playerVeloX = playerInputVeloX;
        playerVeloY = playerInputVeloY;

        if(System.currentTimeMillis() > jumpTimer +1000){
            jumpActive = false;
        }
        if(jumpActive){
            playerVeloY += (int)(-5*(-2*(((double)(System.currentTimeMillis()-jumpTimer)/500.0)-1)+2));
            //System.out.println(System.currentTimeMillis()-jumpTimer);
        }
        playerVeloY+=8;//gravity
        //System.out.println(playerVeloY); 
    }

    public void swingSword(int x2, int y2){
        if(isAttackOnCooldown){
            return;
        }

        int diffX = ((int)x - x2 - 15)*-1;
        int diffY = ((int)y - y2 - 15);

        System.out.println("diffx and y: " + diffX + " " + diffY);

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

        //if(attackAngle < 0){
            //attackAngle += 2*Math.PI;
        //}

       // System.out.println("Angle: " + attackAngle);

        arcX = (int)(x -15 +  (25 * Math.cos(attackAngle)));
        arcY = (int)(y -15 - (25 * Math.sin(attackAngle)));

        //System.out.println(20*Math.cos(attackAngle) + " " + 20*Math.sin(attackAngle));
        System.out.println("Your POS: " + (int)(x+15) + " " + (int)(y+15) + " arc pos: " + arcX +" " + arcY);

        //double arcAngle = getAttackAngle();
			//System.out.println("arcAngle is " + getAttackAngle() + " attackAngle is " + attackAngle);

        attackTimer = System.currentTimeMillis() + 1000;
        //System.out.println("Timer Started!");

    }

    public int getArcX(){
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

    public void jump(){
        //System.out.println("Hi");
        jumpActive = true;
        jumpTimer = System.currentTimeMillis();
    }





    public void setKeyA(boolean b){
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

    public void playerCollision(GameManager gm){
        ArrayList<Rectangle> arr = gm.getCurrentLevel().collisionArray;
        //left
        Rectangle tempRectangle = new Rectangle((int)x-5,(int)y,5,60);
        for(int i=0;i<arr.size();i++){
            if(tempRectangle.intersects(arr.get(i)) && playerVeloX<0){ 
                playerVeloX=0;
                x=arr.get(i).x+arr.get(i).width+2;//reset just outside to avoid clipping
            }
        }
        //down
        tempRectangle = new Rectangle((int)x,(int)y+70,30,5);
        for(int i=0;i<arr.size();i++){
            if(tempRectangle.intersects(arr.get(i)) && playerVeloY>0){ 
                //System.out.print(".");
                playerVeloY=0;
                y=arr.get(i).y-62;
            }
        }
             //right
        tempRectangle = new Rectangle((int)x+35,(int)y,5,60);
        for(int i=0;i<arr.size();i++){
            if(tempRectangle.intersects(arr.get(i)) && playerVeloX>0){ 
                //System.out.print(".");
                playerVeloX=0;
                x=arr.get(i).x-32;
            }
        }
        //UP
        tempRectangle = new Rectangle((int)x,(int)y-5,30,5);
        for(int i=0;i<arr.size();i++){
            if(tempRectangle.intersects(arr.get(i)) && playerVeloY<0){ 
                //System.out.print(".");
                playerVeloY=0;
                y=arr.get(i).y+arr.get(i).height+5;
                jumpActive=false;
            }
        } 
    }
}
