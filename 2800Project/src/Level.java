import java.awt.*;
import java.util.*;


public class Level {
    
    public Level leftLevel;
    public Level rightLevel;

    private String levelTitle;

    ArrayList<Enemy> enemyList;
    public ArrayList<Rectangle> collisionArray;

    // Add a tick counter for fireball generation
    private int tickCounter;

    // Add a tick counter for fireball generation
    private int tickCounter;

    public Level(Level left, Level right, ArrayList<Rectangle> colArr,String title,ArrayList<Enemy> enArr){//ADD ENEMY ARRAY
        leftLevel = left;
        rightLevel = right;
        collisionArray = colArr;
        levelTitle=title;
        enemyList=enArr;
        tickCounter = 0;
        tickCounter = 0;
    }

    //all new levels go here

    //steps:
    //make rectangles to go in a collision array (for walking and jumping on)
    //attach a left and right level so when you go to the side of the screen you enter a new level (likely done later)
    //make a list of enemies
    //init the level
    //then attach it as the left or right level of another level to put it into play.
    
    //has a level string for title for tracking, may or may not be deleted later. 
    

    public static Level levelStartUp(){
        ArrayList<Rectangle> colArr = new ArrayList<>();
        ArrayList<Rectangle> colArr = new ArrayList<>();
        colArr.add(new Rectangle(0,500,960,60)); 
        colArr.add(new Rectangle(100,400,700,10));
        colArr.add(new Rectangle(940,0,20,400));

        //colArr.add(new Rectangle(500,300,100,20));
        ArrayList<Enemy> enemyArrStart = new ArrayList<Enemy>();
        enemyArrStart.add(new DummyEnemy(700,400,50,50,2));
        enemyArrStart.add(new LavaTrap(50,480,200,20));
        enemyArrStart.add(new MovingPlatform(300, 100, 100, 20, 100, 500,2, false));
        colArr.add(((MovingPlatform)enemyArrStart.get(2)).hitBox);
        // Adds the moving platform's hitbox into the collision array
        Level startingLevel = new Level(null, null, colArr, "MAIN", enemyArrStart);
    
        ArrayList<Enemy> enemyArrRight1 = new ArrayList<Enemy>();
        enemyArrRight1.add(new StalagtiteTrap(550, 220, 0, 0, 0));
        enemyArrRight1.add(new StalagtiteTrap(750, 70, 0, 0, 0));
        enemyArrRight1.add(new JumpingEnemy(850, 350, 50, 50, 2));
    
        ArrayList<Enemy> enemyArrLeft1 = new ArrayList<Enemy>();
        enemyArrLeft1.add(new EnemyGrimm(50, 100, 50, 50, 5));
        enemyArrLeft1.add(new Fireball(50, 100));
    
        ArrayList<Rectangle> colArr2 = new ArrayList<>();
        colArr2.add(new Rectangle(0,500,960,60)); 
        colArr2.add(new Rectangle(100,400,200,40));
        colArr2.add(new Rectangle(320,200,100,20));
        Level leftLevel1 = new Level(null, startingLevel, colArr2, "LEFT", enemyArrLeft1);
    
        ArrayList<Rectangle> colArr3 = new ArrayList<>();
        colArr3.add(new Rectangle(0,500,960,60)); 
        colArr3.add(new Rectangle(200,350,100,40));
        colArr3.add(new Rectangle(500,200,100,20));
        colArr3.add(new Rectangle(700,50,100,20));
        Level rightLevel1 = new Level(startingLevel, null, colArr3, "RIGHT", enemyArrRight1);
    
        startingLevel.leftLevel = leftLevel1;
        startingLevel.rightLevel = rightLevel1;
    
        // Ensure that both left and right levels receive the same enemy list
        leftLevel1.enemyList = enemyArrLeft1;
        rightLevel1.enemyList = enemyArrRight1;
    
        return startingLevel;
    }
    

    public static Level level1(){
        ArrayList<Rectangle> colArr= new ArrayList<>();
        ArrayList<Enemy> enemyArr = new ArrayList<Enemy>();
        colArr.add(new Rectangle(0,500,960,60));
        colArr.add(new Rectangle(720,450,240,50));
        colArr.add(new Rectangle(810,400,150,50));
        colArr.add(new Rectangle(900,350,60,50));

        enemyArr.add(new JumpingEnemy(600,380,50,100,2));
        enemyArr.add(new JumpingEnemy(830,200,50,100,2));

        Level roomOne = new Level(null,null, colArr, "Room1" ,enemyArr);


        ArrayList<Rectangle> colArr2 = new ArrayList<>();
        ArrayList<Enemy> enemyArr2 = new ArrayList<Enemy>();
        colArr2.add(new Rectangle(0,500,960,60));
        colArr2.add(new Rectangle(0,0,960,20));
        enemyArr2.add(new LavaTrap(0,485,960,30));
        colArr2.add(new Rectangle(0,350,150,200));
        colArr2.add(new Rectangle(810,350,150,200));

        MovingPlatform platform1 = new MovingPlatform(150,350,100,20,150,710,2,false);
        enemyArr2.add(platform1);
        colArr2.add(platform1.hitBox);

        Random random = new Random();
        int randomInt;
        for(int i = 0; i < random.nextInt(4) + 4; i ++) {
            randomInt = random.nextInt(661) + 150;
            enemyArr2.add(new StalagtiteTrap(randomInt, 20, 0, 0, 0));
        }

        Level roomTwo = new Level(roomOne,null, colArr2, "Room2" ,enemyArr2);
        roomOne.rightLevel = roomTwo;

        ArrayList<Rectangle> colArr3 = new ArrayList<>();
        ArrayList<Enemy> enemyArr3 = new ArrayList<Enemy>();

        colArr3.add(new Rectangle(0,350,150,200));
        enemyArr3.add(new LavaTrap(0,485,960,30));
        colArr3.add(new Rectangle(0,500,960,20));
        colArr3.add(new Rectangle(280,250,750,20));
        colArr3.add(new Rectangle(280,450,50,50));
        colArr3.add(new Rectangle(480,450,50,50));
        colArr3.add(new Rectangle(680,450,50,50));
        colArr3.add(new Rectangle(880,450,50,50));

        MovingPlatform platform2 = new MovingPlatform(150,350,130,20,150,460,2,true);
        enemyArr3.add(platform2);
        colArr3.add(platform2.hitBox);

        enemyArr3.add(new JumpingEnemy(390,0,50,100,2));
        enemyArr3.add(new JumpingEnemy(830,0,50,100,2));

        Level roomThree = new Level(roomTwo,null, colArr3, "Room3" ,enemyArr3);
        roomTwo.rightLevel = roomThree;

        ArrayList<Rectangle> colArr4 = new ArrayList<>();
        ArrayList<Enemy> enemyArr4 = new ArrayList<Enemy>();
        colArr4.add(new Rectangle(0,500,960,30));
        colArr4.add(new Rectangle(0,0,960,20));
        colArr4.add(new Rectangle(0,250,100,40));
        colArr4.add(new Rectangle(0,290,200,40));
        colArr4.add(new Rectangle(0,330,300,40));
        colArr4.add(new Rectangle(200,370,210,300));
        enemyArr4.add(new LavaTrap(410,485,550,30));
        colArr4.add(new Rectangle(530, 410, 200, 200));
        colArr4.add(new Rectangle(850, 450,200,100));
        enemyArr4.add(new JumpingEnemy(650,250,50,100,2));

        for(int i = 0; i < random.nextInt(4) + 4; i ++) {
            randomInt = random.nextInt(661) + 150;
            enemyArr4.add(new StalagtiteTrap(randomInt, 20, 0, 0, 0));
        }
        enemyArr4.add(new Portal(150,400,roomOne,200,200));
        Level roomFour = new Level(roomThree,null, colArr4, "Room4" ,enemyArr4);
        roomThree.rightLevel = roomFour;

        ArrayList<Rectangle> colArr5 = new ArrayList<>();
        ArrayList<Enemy> enemyArr5 = new ArrayList<Enemy>();
        colArr5.add(new Rectangle(0,500,960,40));
        enemyArr5.add(new LavaTrap(0,485,960,100));
        colArr5.add(new Rectangle(0,450,125,100));
        colArr5.add(new Rectangle(300,450,360,100));
        colArr5.add(new Rectangle(835, 450, 125,100));
        colArr5.add(new Rectangle(125,255,175,20));
        colArr5.add(new Rectangle(660,255,175,20));
        enemyArr5.add(new EnemyGrimm(480,100,100,100,10));

        Level roomBoss = new Level(roomFour,null, colArr5, "Boss Room" ,enemyArr5);
        roomFour.rightLevel = roomBoss;

        return roomOne;
    }
    

    public static Level level1(){
        ArrayList<Rectangle> colArr= new ArrayList<>();
        ArrayList<Enemy> enemyArr = new ArrayList<Enemy>();
        colArr.add(new Rectangle(0,500,960,60));
        colArr.add(new Rectangle(720,450,240,50));
        colArr.add(new Rectangle(810,400,150,50));
        colArr.add(new Rectangle(900,350,60,50));

        enemyArr.add(new JumpingEnemy(600,380,50,100,2));
        enemyArr.add(new JumpingEnemy(830,200,50,100,2));

        Level roomOne = new Level(null,null, colArr, "Room1" ,enemyArr);


        ArrayList<Rectangle> colArr2 = new ArrayList<>();
        ArrayList<Enemy> enemyArr2 = new ArrayList<Enemy>();
        colArr2.add(new Rectangle(0,500,960,60));
        colArr2.add(new Rectangle(0,0,960,20));
        enemyArr2.add(new LavaTrap(0,485,960,30));
        colArr2.add(new Rectangle(0,350,150,200));
        colArr2.add(new Rectangle(810,350,150,200));

        MovingPlatform platform1 = new MovingPlatform(150,350,100,20,150,710,2,false);
        enemyArr2.add(platform1);
        colArr2.add(platform1.hitBox);

        Random random = new Random();
        int randomInt;
        for(int i = 0; i < random.nextInt(4) + 4; i ++) {
            randomInt = random.nextInt(661) + 150;
            enemyArr2.add(new StalagtiteTrap(randomInt, 20, 0, 0, 0));
        }

        Level roomTwo = new Level(roomOne,null, colArr2, "Room2" ,enemyArr2);
        roomOne.rightLevel = roomTwo;

        ArrayList<Rectangle> colArr3 = new ArrayList<>();
        ArrayList<Enemy> enemyArr3 = new ArrayList<Enemy>();

        colArr3.add(new Rectangle(0,350,150,200));
        enemyArr3.add(new LavaTrap(0,485,960,30));
        colArr3.add(new Rectangle(0,500,960,20));
        colArr3.add(new Rectangle(280,250,750,20));
        colArr3.add(new Rectangle(280,450,50,50));
        colArr3.add(new Rectangle(480,450,50,50));
        colArr3.add(new Rectangle(680,450,50,50));
        colArr3.add(new Rectangle(880,450,50,50));

        MovingPlatform platform2 = new MovingPlatform(150,350,130,20,150,460,2,true);
        enemyArr3.add(platform2);
        colArr3.add(platform2.hitBox);

        enemyArr3.add(new JumpingEnemy(390,0,50,100,2));
        enemyArr3.add(new JumpingEnemy(830,0,50,100,2));

        Level roomThree = new Level(roomTwo,null, colArr3, "Room3" ,enemyArr3);
        roomTwo.rightLevel = roomThree;

        ArrayList<Rectangle> colArr4 = new ArrayList<>();
        ArrayList<Enemy> enemyArr4 = new ArrayList<Enemy>();
        colArr4.add(new Rectangle(0,500,960,30));
        colArr4.add(new Rectangle(0,0,960,20));
        colArr4.add(new Rectangle(0,250,100,40));
        colArr4.add(new Rectangle(0,290,200,40));
        colArr4.add(new Rectangle(0,330,300,40));
        colArr4.add(new Rectangle(200,370,210,300));
        enemyArr4.add(new LavaTrap(410,485,550,30));
        colArr4.add(new Rectangle(530, 410, 200, 200));
        colArr4.add(new Rectangle(850, 450,200,100));
        enemyArr4.add(new JumpingEnemy(650,250,50,100,2));

        for(int i = 0; i < random.nextInt(4) + 4; i ++) {
            randomInt = random.nextInt(661) + 150;
            enemyArr4.add(new StalagtiteTrap(randomInt, 20, 0, 0, 0));
        }
        enemyArr4.add(new Portal(150,400,roomOne,200,200));
        Level roomFour = new Level(roomThree,null, colArr4, "Room4" ,enemyArr4);
        roomThree.rightLevel = roomFour;

        ArrayList<Rectangle> colArr5 = new ArrayList<>();
        ArrayList<Enemy> enemyArr5 = new ArrayList<Enemy>();
        colArr5.add(new Rectangle(0,500,960,40));
        enemyArr5.add(new LavaTrap(0,485,960,100));
        colArr5.add(new Rectangle(0,450,125,100));
        colArr5.add(new Rectangle(300,450,360,100));
        colArr5.add(new Rectangle(835, 450, 125,100));
        colArr5.add(new Rectangle(125,255,175,20));
        colArr5.add(new Rectangle(660,255,175,20));
        enemyArr5.add(new EnemyGrimm(480,100,100,100,10));

        Level roomBoss = new Level(roomFour,null, colArr5, "Boss Room" ,enemyArr5);
        roomFour.rightLevel = roomBoss;

        return roomOne;
    }

    public void render(Graphics2D g2d){

        //draws all rectangles in collision array. 
        g2d.setColor(Color.white);
        for(int i=0;i<collisionArray.size();i++){
            g2d.fill(collisionArray.get(i));
        }
        g2d.setColor(Color.red);
        g2d.drawString(levelTitle,400,100);

        for(int i=0;i<enemyList.size();i++){
            enemyList.get(i).render(g2d);
        }
    }

    public void tick(GameManager gm) {
        // Increment the tick counter
        tickCounter++;
    
        // Increment the 5-second counter
        int fiveSecondCounter = tickCounter / 50; // Since you're incrementing tickCounter every tick, 50 ticks = 1 second
    
        // Check if 5 seconds have passed
        if (gm.getCurrentLevel() instanceof Level && ((Level) gm.getCurrentLevel()).leftLevel == null
        && this == gm.getCurrentLevel() && fiveSecondCounter % 5 == 0) {
            // If 5 seconds have passed, stop fireball generation for 1 second
            if (gm.getCurrentLevel() instanceof Level && ((Level) gm.getCurrentLevel()).leftLevel == null
            && this == gm.getCurrentLevel() && tickCounter % 20 == 0) {
                // Generate new fireball only if the EnemyGrimm is alive
                if (isEnemyGrimmAlive()) {
                    generateNewFireball();
                }
            }
        } else {
            // Generate new fireball every 10 ticks (0.1 seconds) outside of the 1-second pause
            if (gm.getCurrentLevel() instanceof Level && ((Level) gm.getCurrentLevel()).leftLevel == null
                    && this == gm.getCurrentLevel() && tickCounter % 20 == 0) {
                // Generate new fireball only if the EnemyGrimm is alive
                if (isEnemyGrimmAlive()) {
                    generateNewFireball();
                }
            }
        }
    
        // Rest of the tick method
        for (int i = 0; i < enemyList.size(); i++) {
    public void tick(GameManager gm) {
        // Increment the tick counter
        tickCounter++;
    
        // Increment the 5-second counter
        int fiveSecondCounter = tickCounter / 50; // Since you're incrementing tickCounter every tick, 50 ticks = 1 second
    
        // Check if 5 seconds have passed
        if (gm.getCurrentLevel() instanceof Level && ((Level) gm.getCurrentLevel()).leftLevel == null
        && this == gm.getCurrentLevel() && fiveSecondCounter % 5 == 0) {
            // If 5 seconds have passed, stop fireball generation for 1 second
            if (gm.getCurrentLevel() instanceof Level && ((Level) gm.getCurrentLevel()).leftLevel == null
            && this == gm.getCurrentLevel() && tickCounter % 20 == 0) {
                // Generate new fireball only if the EnemyGrimm is alive
                if (isEnemyGrimmAlive()) {
                    generateNewFireball();
                }
            }
        } else {
            // Generate new fireball every 10 ticks (0.1 seconds) outside of the 1-second pause
            if (gm.getCurrentLevel() instanceof Level && ((Level) gm.getCurrentLevel()).leftLevel == null
                    && this == gm.getCurrentLevel() && tickCounter % 20 == 0) {
                // Generate new fireball only if the EnemyGrimm is alive
                if (isEnemyGrimmAlive()) {
                    generateNewFireball();
                }
            }
        }
    
        // Rest of the tick method
        for (int i = 0; i < enemyList.size(); i++) {
            enemyList.get(i).tick(gm);
        }
    }
    
    private boolean isEnemyGrimmAlive() {
        for (Enemy enemy : enemyList) {
            if (enemy instanceof EnemyGrimm && enemy.isAlive()) {
                return true;
            }
        }
        return false;
    }
    
    
    private void generateNewFireball() {
        // Generate and add a new fireball to the enemy list
        enemyList.add(new Fireball(10, 20));
    }
    
    private boolean isEnemyGrimmAlive() {
        for (Enemy enemy : enemyList) {
            if (enemy instanceof EnemyGrimm && enemy.isAlive()) {
                return true;
            }
        }
        return false;
    }
    
    
    private void generateNewFireball() {
        // Generate and add a new fireball to the enemy list
        enemyList.add(new Fireball(10, 20));
    }
}
