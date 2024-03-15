import java.awt.*;
import java.util.*;


public class Level {
    
    public Level leftLevel;
    public Level rightLevel;

    private String levelTitle;

    ArrayList<Enemy> enemyList;
    public ArrayList<Rectangle> collisionArray;

    public Level(Level left, Level right, ArrayList<Rectangle> colArr,String title,ArrayList<Enemy> enArr){//ADD ENEMY ARRAY
        leftLevel = left;
        rightLevel = right;
        collisionArray = colArr;
        levelTitle=title;
        enemyList=enArr;
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
        ArrayList<Rectangle> colArr= new ArrayList<>();
        colArr.add(new Rectangle(0,500,960,60)); 
        colArr.add(new Rectangle(200,450,200,40));
        colArr.add(new Rectangle(500,300,100,20));
        ArrayList<Enemy> enemyArrStart = new ArrayList<Enemy>();
        enemyArrStart.add(new DummyEnemy(700,400,50,50,2));
        enemyArrStart.add(new LavaTrap(50,480,200,20));
        enemyArrStart.add(new MovingPlatform(300, 100, 100, 20, 100, 500,2, false));
        colArr.add(((MovingPlatform)enemyArrStart.get(2)).hitBox);
        //Adds the moving platforms hitbox into the collision array
        Level startingLevel = new Level(null,null,colArr, "MAIN",enemyArrStart);

       
        ArrayList<Enemy> emptyEnemyArr = new ArrayList<Enemy>();

        ArrayList<Enemy> enemyArrRight1 = new ArrayList<Enemy>();
        enemyArrRight1.add(new StalagtiteTrap(550, 220, 0, 0, 0));
        enemyArrRight1.add(new StalagtiteTrap(750, 70, 0, 0, 0));

    
        ArrayList<Rectangle> colArr2= new ArrayList<>();
        colArr2.add(new Rectangle(0,500,960,60)); 
        colArr2.add(new Rectangle(100,400,200,40));
        colArr2.add(new Rectangle(320,200,100,20));
        Level leftLevel1 = new Level(null,startingLevel,colArr2, "LEFT",emptyEnemyArr);

        ArrayList<Rectangle> colArr3= new ArrayList<>();
        colArr3.add(new Rectangle(0,500,960,60)); 
        colArr3.add(new Rectangle(200,350,100,40));
        colArr3.add(new Rectangle(500,200,100,20));
        colArr3.add(new Rectangle(700,50,100,20));
        Level rightLevel1 = new Level(startingLevel,null,colArr3, "RIGHT",enemyArrRight1);



        startingLevel.leftLevel = leftLevel1;
        startingLevel.rightLevel=rightLevel1;

        return startingLevel;
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

    public void tick(GameManager gm){
        for(int i=0;i<enemyList.size();i++){
            enemyList.get(i).tick(gm);
        }
    }


}
