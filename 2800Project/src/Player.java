import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Player extends GameObject {
    private BufferedImage spriteSheet;
    private final int SPRITE_COLUMNS = 8; // Number of columns in the spritesheet
    private final int SPRITE_ROWS = 4; // Number of rows in the spritesheet

    private final int IDLE_FRAME_COLUMN = 0; // Column of the idle animation frame
    private final int IDLE_FRAME_ROW = 0; // Row of the idle animation frame

    private int playerInputVeloX; // player input's portion of the player's velocity
    private int playerInputVeloY;

    private int playerVeloX; // players' total velocity after all factors.
    private int playerVeloY;

    private boolean keyA = false;
    private boolean keyD = false;

    private int arcX; // angle calculations for sword attack
    private int arcY;
    public Polygon attackTriangle;

    private boolean isAttackOnCooldown = false;// attack information
    private boolean isAttackOnline = false;
    private double attackAngle;
    private long attackTimer = 0;

    private long jumpTimer;
    private boolean jumpActive = false;
    private boolean jumpAllowed = true;

    public int health=3;   //health stuff
     long IFrames;
     boolean canBeHit = true;    //temp invincibility after being hit
    private Rectangle hitBox;

    private long deathMessageTimer; //calcs how long to display "you died" message

    private boolean fireballActivated = true; // one of the abilities that you unlock
    private PlayerFireball playerFireball;
    private boolean fireballAlive = false;
    private double fireballTimer = 0;


    public boolean orbActive = false;
    public Color orbColor;

    boolean bossSwordKnockback = false;
    int bossSwordKnockbackDirection=0; //-1 left, 1 right;
    long bossSwordKnockbackTimer;

    private boolean fireballActivated = true; // one of the abilities that you unlock
    private PlayerFireball playerFireball;
    private boolean fireballAlive = false;
    private double fireballTimer = 0;

    public Player() {
        super(450, 400, 30, 60);
        loadSpriteSheet("player.png");
    }

    private void loadSpriteSheet(String path) { // loading the spritesheet
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream != null) {
                spriteSheet = ImageIO.read(inputStream);
            } else {
                throw new IOException("Resource not found: " + path);
            }
        } catch (IOException e) {
            System.out.println("Error loading spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tick(GameManager gm) {

        hitBox = new Rectangle((int) x, (int) y, (int) width, (int) height);// update hitbox for collision

        playerVeloCalc();

        if(bossSwordKnockback && System.currentTimeMillis()>bossSwordKnockbackTimer){
            bossSwordKnockback = false;
        }
       
        if(System.currentTimeMillis() >= attackTimer && isAttackOnline){//checks if the attack hitbox and render should still be active
            isAttackOnline=false;
        }

        if (System.currentTimeMillis() <= attackTimer) {// ditto
            isAttackOnline = true;
        }
        if(fireballAlive){
            playerFireball.tick(gm);
        }
        
        playerCollision(gm);//lets you stand on rects from the collisionArray from the current level

        if (x < 10 && gm.getCurrentLevel().leftLevel == null && playerVeloX < 0) {// Can't enter null levels
            playerVeloX = 0;
        }
        if (x > 920 && gm.getCurrentLevel().rightLevel == null && playerVeloX > 0) {// Can't enter null levels
            playerVeloX = 0;
        }

        x += playerVeloX;// changes position based on velos
        y += playerVeloY;

        swapLevel(gm); // if close to side, change level
        detectHits(gm);// detects sword on enemy, detects ememies colliding with you.

    }

    public void render(Graphics2D g2d) {
        g2d.setColor(Color.blue);// render character
        g2d.fillRect((int) x, (int) y, 30, 60);

        if (spriteSheet != null) {
            int spriteWidth = spriteSheet.getWidth() / SPRITE_COLUMNS;
            int spriteHeight = spriteSheet.getHeight() / SPRITE_ROWS;

            int srcX, srcY;

            if (!keyA) {
                // Calculate the position of the idle frame in the spritesheet
                srcX = IDLE_FRAME_COLUMN * spriteWidth;
                srcY = IDLE_FRAME_ROW * spriteHeight;

                g2d.drawImage(spriteSheet, (int) x, (int) y, (int) x + spriteWidth, (int) y + spriteHeight,
                        srcX, srcY, srcX + spriteWidth, srcY + spriteHeight, null);
            } else {
                srcX = IDLE_FRAME_COLUMN + 1 * spriteWidth;
                srcY = IDLE_FRAME_ROW * spriteHeight;

                g2d.drawImage(spriteSheet, (int) x, (int) y, (int) x + spriteWidth, (int) y + spriteHeight,
                        srcX, srcY, -srcX + spriteWidth, srcY + spriteHeight, null);
            }
        }

        if (isAttackOnline) {// print attack hitbox
            // printing attack hitbox. the animation for the attack should eventually go
            // here, likely.
            g2d.setColor(Color.yellow);
            g2d.fillPolygon(attackTriangle);
		}
        if(fireballAlive){
            playerFireball.render(g2d);
        }

        g2d.setColor(Color.RED);// print level title
        for (int i = 0; i < health; i++) {
            g2d.fillRect(50 + 25 * (i + 1), 50, 20, 20);
        }

        if (System.currentTimeMillis() < deathMessageTimer) {// print death message
            g2d.setColor(Color.red);
            g2d.drawString("YOU DIED", 400, 150);

        }
        
        
        if(orbActive){
            g2d.setColor(orbColor);
            Ellipse2D.Double sphere = new Ellipse2D.Double(x + 40, y - 20, 10, 10);
            g2d.fill(sphere);
        }
        //g2d.drawString("bsk is " + bossSwordKnockback + "dir is "+ bossSwordKnockbackDirection, 100, 120);
       
    }

    public void playerInputVeloX(int x) {// part of velo calc. I could probably find a way to skip this step but it
                                         // works for now
        playerInputVeloX = x;
    }

    public void playerInputVeloY(int y) {// part of velo calc. ditto
        playerInputVeloY = y;
    }

    public void playerVeloCalc() {
        playerVeloX = playerInputVeloX;// starts with player input
        playerVeloY = playerInputVeloY;

        if (System.currentTimeMillis() > jumpTimer + 1000) {// adds upward velo based on the point of the jump
            jumpActive = false;
        }
        if (jumpActive) {
            playerVeloY += (int) (-5 * (-2 * (((double) (System.currentTimeMillis() - jumpTimer) / 500.0) - 1) + 2));// jump
                                                                                                                     // calc
        }
        if(bossSwordKnockback){
            playerVeloX += 20 * bossSwordKnockbackDirection;
        }
        playerVeloY+=8;//gravity
    }

    public void swingSword(int x2, int y2) {// math to make a sword swing hitbox
        if (isAttackOnCooldown) {
            return;
        }

        int diffX = ((int) x - x2 - 15) * -1;
        int diffY = ((int) y - y2 - 15);

        // System.out.println("diffx and y: " + diffX + " " + diffY);

        attackAngle = Math.atan((double) diffY / diffX);
        if (diffX < 0 && diffY > 0) {
            // System.out.println("here1");
            attackAngle = Math.PI + attackAngle;
        }
        if (diffX < 0 && diffY < 0) {
            attackAngle = Math.PI + attackAngle;
        } else if (diffX > 0 && diffY < 0) {
            attackAngle = 2 * Math.PI + attackAngle;
        }

        arcX = (int) (x + 15 + (25 * Math.cos(attackAngle)));
        arcY = (int) (y + 15 - (25 * Math.sin(attackAngle)));

        // System.out.println(20*Math.cos(attackAngle) + " " +
        // 20*Math.sin(attackAngle));
        // System.out.println("Your POS: " + (int)(x+15) + " " + (int)(y+15) + " arc
        // pos: " + arcX +" " + arcY);

        // create triangle;
        int arcX2 = (int) (arcX + (100 * Math.cos((attackAngle) + (15 * Math.PI / 180))));
        int arcY2 = (int) (arcY - (100 * Math.sin((attackAngle) + (15 * Math.PI / 180))));
        int arcX3 = (int) (arcX + (100 * Math.cos((attackAngle) - (15 * Math.PI / 180))));
        int arcY3 = (int) (arcY - (100 * Math.sin((attackAngle) - (15 * Math.PI / 180))));

        int[] triangleXvalues = new int[] { arcX, arcX2, arcX3 };
        int[] triangleYvalues = new int[] { arcY, arcY2, arcY3 };

        attackTriangle = new Polygon(triangleXvalues, triangleYvalues, 3);

        attackTimer = System.currentTimeMillis() + 450;

    }

    public void activateFireball(){
        this.fireballActivated = true;
    }

    public void resetFireball(){
        this.fireballAlive = false;
        fireballTimer = System.currentTimeMillis();
    }

    public void fireball(int x2, int y2){
        if(fireballActivated && !fireballAlive && System.currentTimeMillis() >= fireballTimer + 500){
            double dx = x2 - x;
            double dy = y2 - y;
            double angle = Math.atan2(dy,dx);
            playerFireball = new PlayerFireball(x,y,angle, System.currentTimeMillis());
            fireballAlive = true;
        }
    }

    public void activateFireball() {
        this.fireballActivated = true;
    }

    public void resetFireball() {
        this.fireballAlive = false;
        fireballTimer = System.currentTimeMillis();
    }


    public int getArcX() {// random getters needed because of bad formatting at the start of this project.
                          // may be refactored later
        return arcX;
    }

    public int getArcY() {
        return arcY;
    }

    public double getAttackAngle() {
        return this.attackAngle;
    }

    public boolean getIsAttackOnline() {
        return isAttackOnline;
    }

    public void jump() {// activating jump and its timer.
        // System.out.println("Hi");
        if (jumpAllowed) {
            jumpAllowed = false;
            jumpActive = true;
            jumpTimer = System.currentTimeMillis();
        }
    }

    public void setKeyA(boolean b) {// random getters needed because of bad formatting at the start of this project.
                                    // may be refactored later
        keyA = b;
    }

    public boolean getKeyA() {
        return keyA;
    }

    public void setKeyD(boolean b) {
        keyD = b;
    }

    public boolean getKeyD() {
        return keyD;
    }

    public void swapLevel(GameManager gm) {// simple if close enough to the side, shift levels.
        if (x < 5) {
            gm.setCurrentLevel(gm.getCurrentLevel().leftLevel);
            x = 910;
        }
        if (x > 925) {
            gm.setCurrentLevel(gm.getCurrentLevel().rightLevel);
            x = 20;
        }
    }

    public void detectHits(GameManager gm) {
        // if sword is in enemy hitbox, they take damage
        ArrayList<Enemy> tempEnemyList = gm.getCurrentLevel().enemyList;
        for(int i=0;i<tempEnemyList.size();i++){
            if (isAttackOnline && this.attackTriangle.intersects(tempEnemyList.get(i).getHitBox())){
                tempEnemyList.get(i).hitLanded(gm);
            }

            if (System.currentTimeMillis() > IFrames) {
                canBeHit = true;
            }
           // System.out.println("i is " + i);
           //if you are in enemy hitbox, you take damage
            if(hitBox.intersects(tempEnemyList.get(i).getHitBox()) && canBeHit && tempEnemyList.get(i).isAlive && tempEnemyList.get(i).doesDamageOnCollision){
                tempEnemyList.get(i).hitLanded(gm);
                health--;
                if (health <= 0) {
                    death(gm);
                }
                canBeHit = false;
                IFrames = System.currentTimeMillis() + 1000;
                // if there is a unique enemy hit effect, it will be called here, abstract
                // method defined in Enemy and actual method
                // defined in the unique enemy file (eg knockback, more than one hit, etc. )
            }


        }
    }

    public void death(GameManager gm) {// called if health<0, resets game basically. can be changed to respawn properly
                                       // later.
        gm.reset();
        health = 3;
        x = 450;
        y = 400;
        deathMessageTimer = System.currentTimeMillis() + 3000;
    }

    public Polygon getAttackTriangle(){
        return this.attackTriangle;
    }

    public Rectangle getHitBox(){
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }
    

    public void playerCollision(GameManager gm) {

        // detects collisions, one for each side, plus some jump consistency stuff.

        ArrayList<Rectangle> arr = gm.getCurrentLevel().collisionArray;
        // UP
        Rectangle tempRectangle = new Rectangle((int) x, (int) y - 5, 30, 5);
        for (int i = 0; i < arr.size(); i++) {
            if (tempRectangle.intersects(arr.get(i)) && playerVeloY < 0) {
                playerVeloY = 0;// stops you from moving into the object
                y = arr.get(i).y + arr.get(i).height + 2;// reset just outside to avoid clipping
                jumpActive = false;// after hitting your head on something you no longer get any upward velocity
                                   // from jump
            }
        }
        // left
        tempRectangle = new Rectangle((int) x - 5, (int) y, 5, 60);
        for (int i = 0; i < arr.size(); i++) {
            if (tempRectangle.intersects(arr.get(i)) && playerVeloX < 0) {
                playerVeloX = 0;
                x = arr.get(i).x + arr.get(i).width + 2;
            }
        }
        // down
        tempRectangle = new Rectangle((int) x, (int) y + 70, 30, 5);
        for (int i = 0; i < arr.size(); i++) {
            if (tempRectangle.intersects(arr.get(i)) && playerVeloY > 0) {
                // System.out.print(".");
                playerVeloY = 0;
                y = arr.get(i).y - height - 2;
            }
        }
        // JUMP
        tempRectangle = new Rectangle((int) x, (int) y + 70, (int) height, 10);// helps only jump when on the ground.
        for (int i = 0; i < arr.size(); i++) {
            if (tempRectangle.intersects(arr.get(i))) {
                jumpAllowed = true;
                break;
            } else {
                jumpAllowed = false;
            }
        }
        // right
        tempRectangle = new Rectangle((int) x + 35, (int) y, 5, 60);
        for (int i = 0; i < arr.size(); i++) {
            if (tempRectangle.intersects(arr.get(i)) && playerVeloX > 0) {
                // System.out.print(".");
                playerVeloX = 0;
                x = arr.get(i).x - width - 2;
            }
        }

    }
}