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

    public Level(Level left, Level right, ArrayList<Rectangle> colArr,String title,ArrayList<Enemy> enArr){//ADD ENEMY ARRAY
        leftLevel = left;
        rightLevel = right;
        collisionArray = colArr;
        levelTitle=title;
        enemyList=enArr;
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
    
/*

//OLD TESTING STUFF KEEPING JUST IN CASE


    public static Level levelStartUp(){
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
    */


    public static Level level1(GameManager gm){
        ArrayList<Level> levelList = new ArrayList<Level>();


        ArrayList<Rectangle> colArr= new ArrayList<>();
        ArrayList<Enemy> enemyArr = new ArrayList<Enemy>();
        colArr.add(new Rectangle(0,500,960,60));
        colArr.add(new Rectangle(720,450,240,50));
        colArr.add(new Rectangle(810,400,150,50));
        colArr.add(new Rectangle(900,350,60,50));

        enemyArr.add(new JumpingEnemy(600,380,50,100,2));
        enemyArr.add(new JumpingEnemy(830,200,50,100,2));
        enemyArr.add(new HealingFountain(340, 350,200,150));
        enemyArr.add(new BossDoor(250, 50, 100, 200));

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
        colArr5.add(new Rectangle(125,265,175,20));
        colArr5.add(new Rectangle(660,265,175,20));
        Enemy miniBoss1 = new EnemyGrimm(480,100,100,100,10);
        enemyArr5.add(miniBoss1);
        enemyArr5.add(new Portal(455, 150, roomOne, 430,300, miniBoss1));
        Level roomFive = new Level(roomFour,null, colArr5, "Boss Room" ,enemyArr5);
        roomFour.rightLevel = roomFive;
        enemyArr.add(new Portal(495,250, roomFive, 455,350,miniBoss1));


        ArrayList<Rectangle> colArr6 = new ArrayList<>();
        ArrayList<Enemy> enemyArr6 = new ArrayList<Enemy>();
        colArr6.add(new Rectangle(0,450,960,100));
        colArr6.add(new Rectangle(900,0,50,300));
        Button button1 = new Button(425,0,50,50);
        enemyArr6.add(button1);
        Enemy unlockableDoor1 = new UnlockableDoor(900,300,50,150, button1);
        enemyArr6.add(unlockableDoor1);
        colArr6.add(unlockableDoor1.hitBox);
        for(int i = 0; i < random.nextInt(10) + 10; i ++) {
            randomInt = random.nextInt(900);
            enemyArr6.add(new VengeflyEnemy(randomInt, 0, 20, 20, 2));
        }
        Level roomSix = new Level(roomFive, null, colArr6, "Room Six", enemyArr6);
        roomFive.rightLevel = roomSix;

        ArrayList<Rectangle> colArr7 = new ArrayList<>();
        ArrayList<Enemy> enemyArr7 = new ArrayList<Enemy>();
        colArr7.add(new Rectangle(0,500,1000,40));
        colArr7.add(new Rectangle(900,0,50,300));
        colArr7.add(new Rectangle(900,450,50,100));
        enemyArr7.add(new LavaTrap(0,480,1000,40));
        Button room7Btn1 = new Button(50, 0 ,50,50);
        enemyArr7.add(room7Btn1);
        Button room7Btn2 = new Button(125, 0 ,50,50);
        enemyArr7.add(room7Btn2);
        Button room7Btn3 = new Button(200, 0 ,50,50);
        enemyArr7.add(room7Btn3);
        Button room7Btn4 = new Button(275, 0 ,50,50);
        enemyArr7.add(room7Btn4);
        Button room7Btn5 = new Button(350, 0 ,50,50);
        enemyArr7.add(room7Btn5);
        Button room7Btn6 = new Button(425, 0 ,50,50);
        enemyArr7.add(room7Btn6);
        Button room7Btn7 = new Button(500, 0 ,50,50);
        enemyArr7.add(room7Btn7);
        Button room7Btn8 = new Button(575, 0 ,50,50);
        enemyArr7.add(room7Btn8);
        Button room7Btn9 = new Button(650, 0 ,50,50);
        enemyArr7.add(room7Btn9);
        Button room7Btn10 = new Button(725, 0 ,50,50);
        enemyArr7.add(room7Btn10);
        Button room7Btn11 = new Button(800, 0 ,50,50);
        enemyArr7.add(room7Btn11);

        UnlockableDoor trapFloor1 = new UnlockableDoor(0,450,100,50,room7Btn4);
        colArr7.add(trapFloor1.hitBox);
        enemyArr7.add(trapFloor1);
        UnlockableDoor trapFloor2 = new UnlockableDoor(100,450,100,50, room7Btn8);
        colArr7.add(trapFloor2.hitBox);
        enemyArr7.add(trapFloor2);
        UnlockableDoor trapFloor3 = new UnlockableDoor(200,450,100,50, room7Btn2);
        colArr7.add(trapFloor3.hitBox);
        enemyArr7.add(trapFloor3);
        UnlockableDoor trapFloor4 = new UnlockableDoor(300,450,100,50, room7Btn11);
        colArr7.add(trapFloor4.hitBox);
        enemyArr7.add(trapFloor4);
        UnlockableDoor trapFloor5 = new UnlockableDoor(400,450,100,50, room7Btn3);
        colArr7.add(trapFloor5.hitBox);
        enemyArr7.add(trapFloor5);
        UnlockableDoor trapFloor6 = new UnlockableDoor(500,450,100,50,room7Btn6);
        colArr7.add(trapFloor6.hitBox);
        enemyArr7.add(trapFloor6);
        UnlockableDoor trapFloor7 = new UnlockableDoor(600,450,100,50,room7Btn5);
        colArr7.add(trapFloor7.hitBox);
        enemyArr7.add(trapFloor7);
        UnlockableDoor trapFloor8 = new UnlockableDoor(700,450,100,50,room7Btn9);
        colArr7.add(trapFloor8.hitBox);
        enemyArr7.add(trapFloor8);
        UnlockableDoor trapFloor9 = new UnlockableDoor(800,450,900,50,room7Btn1);
        colArr7.add(trapFloor9.hitBox);
        enemyArr7.add(trapFloor9);
        UnlockableDoor exitDoor = new UnlockableDoor(900,300,50,150,room7Btn7);
        colArr7.add(exitDoor.hitBox);
        enemyArr7.add(exitDoor);

        Level roomSeven = new Level(roomSix, null, colArr7, "Room Seven", enemyArr7);
        roomSix.rightLevel = roomSeven;

        ArrayList<Rectangle> colArr8 = new ArrayList<>();
        ArrayList<Enemy> enemyArr8 = new ArrayList<Enemy>();
        colArr8.add(new Rectangle(0,500,900,20));
        enemyArr8.add(new LavaTrap(0,480,960,40));
        colArr8.add(new Rectangle(0,450,100,50));
        colArr8.add(new Rectangle(850,450,200,50));
        MovingPlatform roomEightplatform1 = new MovingPlatform(100,450,150,10,100,700,2,false);
        colArr8.add(roomEightplatform1.hitBox);
        enemyArr8.add(roomEightplatform1);
        colArr8.add(new Rectangle(455,0,10,50));
        colArr8.add(new Rectangle(515,0,10,50));
        Button roomEightButton = new Button(465,0,50,50);
        MovingPlatform roomEightPlatform2 = new MovingPlatform(400,50,20,10,400,550,2,false);
        colArr8.add(roomEightPlatform2.hitBox);
        enemyArr8.add(roomEightPlatform2);
        enemyArr8.add(roomEightButton);
        UnlockableDoor roomEightDoor = new UnlockableDoor(900,300,50,150,roomEightButton);
        colArr8.add(roomEightDoor.hitBox);
        enemyArr8.add(roomEightDoor);
        colArr8.add(new Rectangle(900,0,50,300));
        for(int i = 0; i < random.nextInt(10) + 5; i ++) {
            randomInt = random.nextInt(900);
            enemyArr8.add(new VengeflyEnemy(randomInt, 0, 20, 20, 2));
        }
        Level roomEight = new Level(roomSeven, null, colArr8, "Room Eight", enemyArr8);
        roomSeven.rightLevel = roomEight;


        ArrayList<Rectangle> colArr9 = new ArrayList<>();
        ArrayList<Enemy> enemyArr9 = new ArrayList<Enemy>();
        colArr9.add(new Rectangle(0,500,960,50));
        enemyArr9.add(new LavaTrap(0,480,960,50));
        colArr9.add(new Rectangle(0,450,100,50));
        colArr9.add(new Rectangle(280,400,400,20));
        colArr9.add(new Rectangle(860,450,100,50));
        Level roomNine = new Level(roomEight, null, colArr9, "Room Nine", enemyArr9);
        roomEight.rightLevel = roomNine;

        ArrayList<Rectangle> colArr10 = new ArrayList<>();
        ArrayList<Enemy> enemyArr10 = new ArrayList<Enemy>();
        colArr10.add(new Rectangle(0,450,960,100));
        colArr10.add(new Rectangle(180,420,600,100));
        colArr10.add(new Rectangle(280,390,400,100));
        colArr10.add(new Rectangle(380,360,200,100));
        enemyArr10.add(new Portal(470,100, roomOne,430,300));
        Level roomTen = new Level(roomNine, null, colArr10, "Room Ten", enemyArr10);
        roomNine.rightLevel = roomTen;

        ArrayList<Rectangle> colArrBossMain = new ArrayList<>();
        colArrBossMain.add(new Rectangle(0,500,960,60));
        colArrBossMain.add(new Rectangle(100,400,700,10));
        colArrBossMain.add(new Rectangle(940,0,20,400));

        //colArr.add(new Rectangle(500,300,100,20));
        ArrayList<Enemy> enemyArrBossMain = new ArrayList<Enemy>();
        enemyArrBossMain.add(new Boss());
        Level levelBossMain = new Level(null, null, colArrBossMain, "BossMain", enemyArrBossMain);

        ArrayList<Enemy> enemyArrBossRight1 = new ArrayList<Enemy>();
        ArrayList<Enemy> enemyArrBossLeft1 = new ArrayList<Enemy>();


        ArrayList<Rectangle> colArrBossRight1 = new ArrayList<>();
       colArrBossRight1.add(new Rectangle(0,500,960,60));
        colArrBossRight1.add(new Rectangle(0,350,800,20));
        colArrBossRight1.add(new Rectangle(100,200,800,20));
        colArrBossRight1.add(new Rectangle(0,0,5,400));
        colArrBossRight1.add(new Rectangle(940,100,20,400));
         enemyArrBossRight1.add(new BossBeamAttack(true,0));

         ArrayList<Enemy> enemyArrBossRight2 = new ArrayList<Enemy>();
         ArrayList<Rectangle> colArrBossRight2 = new ArrayList<>();
         colArrBossRight2.add(new Rectangle(0,500,960,60)); 
         colArrBossRight2.add(new Rectangle(0,100,5,400));
         colArrBossRight2.add(new Rectangle(0,250,50,20));
         colArrBossRight2.add(new Rectangle(940,0,20,200));
         colArrBossRight2.add(new Rectangle(800,350,150,200));
         enemyArrBossRight2.add(new DummyEnemy(920,250,40,100,5));
         enemyArrBossRight2.add(new VengeflyEnemy(100,100,20,20,4));
         enemyArrBossRight2.add(new VengeflyEnemy(600,100,20,20,4));
         Level levelBossRight2 = new Level(null, null, colArrBossRight2, "right2", enemyArrBossRight2);


         ArrayList<Rectangle> colArrBossRight3 = new ArrayList<>();
         ArrayList<Enemy> enemyArrBossRight3 = new ArrayList<Enemy>();
 
         colArrBossRight3.add(new Rectangle(0,350,150,200));
         enemyArrBossRight3.add(new LavaTrap(0,485,960,30));
         enemyArrBossRight3.add(new BossBeamAttack(true, 0));
         colArrBossRight3.add(new Rectangle(0,500,960,20));
         colArrBossRight3.add(new Rectangle(280,450,80,50));
         colArrBossRight3.add(new Rectangle(480,450,80,50));
         colArrBossRight3.add(new Rectangle(680,450,200,150));
         enemyArrBossRight3.add(new BossOrbGenerator(700, 445, Color.blue));
         enemyArrBossRight3.add(new Portal(780,360,levelBossMain,150,400));

         Level levelBossRight3 = new Level(levelBossRight2, null,colArrBossRight3,"Right3",enemyArrBossRight3);
         
        ArrayList<Rectangle> colArrBossLeft1 = new ArrayList<>();
        colArrBossLeft1.add(new Rectangle(0,500,960,60));
        colArrBossLeft1.add(new Rectangle(200,350,100,40));
        colArrBossLeft1.add(new Rectangle(500,200,100,20));
        colArrBossLeft1.add(new Rectangle(700,100,100,20));
        enemyArrBossLeft1.add(new BossOrbGenerator(700, 95, Color.orange));

        Level levelBossLeft1 = new Level(null, levelBossMain, colArrBossLeft1, "LEFT", enemyArrBossLeft1);
        Level levelBossRight1 = new Level(levelBossMain, levelBossRight2, colArrBossRight1, "RIGHT", enemyArrBossRight1);
    
        levelBossMain.leftLevel = levelBossLeft1;
        levelBossMain.rightLevel = levelBossRight1;
        levelBossRight2.leftLevel=levelBossRight1;
        levelBossRight2.rightLevel=levelBossRight3;

    
        // Ensure that both left and right levels receive the same enemy list
        levelBossLeft1.enemyList = enemyArrBossLeft1;
        levelBossLeft1.enemyList = enemyArrBossRight1;

        levelList.add(roomOne);
        levelList.add(roomTwo);
        levelList.add(roomThree);
        levelList.add(roomFour);
        levelList.add(roomFive);
        levelList.add(levelBossMain);
        levelList.add(levelBossLeft1);
        levelList.add(levelBossRight1);
        levelList.add(roomSix);
        levelList.add(roomSeven);
        levelList.add(roomEight);
        levelList.add(roomNine);
        levelList.add(roomTen);

        gm.setLevelList(levelList);

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
        if (gm.getCurrentLevel() instanceof Level && this == gm.getCurrentLevel() && fiveSecondCounter % 5 == 0) {
            // If 5 seconds have passed, stop fireball generation for 1 second
            if (gm.getCurrentLevel() instanceof Level && this == gm.getCurrentLevel() && tickCounter % 20 == 0) {
                // Generate new fireball only if the EnemyGrimm is alive
                if (isEnemyGrimmAlive()) {
                    generateNewFireball();
                }
            }
        } else {
            // Generate new fireball every 10 ticks (0.1 seconds) outside of the 1-second pause
            if (gm.getCurrentLevel() instanceof Level && this == gm.getCurrentLevel() && tickCounter % 20 == 0) {
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
        enemyList.add(new Fireball());
    }
    
}
