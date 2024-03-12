//import java.awt.*;
//import java.awt.image.*;
//import java.awt.event.*;


public class Player {
    

    private int playerPosX;
	private int playerPosY;

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
        playerPosX = 450;
        playerPosY = 400;
    }

    public int[] getPlayerPos(){
        return new int[]{playerPosX,playerPosY};
    }

    public void playerLogic(){
        playerVeloCalc();
        playerPosX += playerVeloX;
        playerPosY += playerVeloY;
       
        if(System.currentTimeMillis() >= attackTimer && isAttackOnline){
            //System.out.println("Timer ended");
            isAttackOnline=false;
        }

        if(System.currentTimeMillis() <= attackTimer){
            isAttackOnline = true;
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
            return;
        }

        if(jumpActive){
            playerVeloY += (int)(-10 * ((-2 * (double)(System.currentTimeMillis()-jumpTimer) / 500) + 2));
        }
    }

    public void swingSword(int x, int y){
        if(isAttackOnCooldown){
            return;
        }

        int diffX = (int)x - playerPosX - 15;
        int diffY = ((int)y - playerPosY - 15)*-1;

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

        arcX = (int)(playerPosX -15 +  (25 * Math.cos(attackAngle)));
        arcY = (int)(playerPosY -15 - (25 * Math.sin(attackAngle)));

        //System.out.println(20*Math.cos(attackAngle) + " " + 20*Math.sin(attackAngle));
        System.out.println("Your POS: " + (int)(playerPosX+15) + " " + (int)(playerPosY+15) + " arc pos: " + arcX +" " + arcY);

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
        System.out.println("Hi");
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

}
