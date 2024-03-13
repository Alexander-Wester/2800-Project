import java.awt.*;
import java.util.*;


public class Level {
    
    public Level leftLevel;
    public Level rightLevel;

    private String levelTitle;

    //ArrayList<Enemies>
    public ArrayList<Rectangle> collisionArray;

    public Level(Level left, Level right, ArrayList<Rectangle> colArr,String title){//ADD ENEMY ARRAY
        leftLevel = left;
        rightLevel = right;
        collisionArray = colArr;
        levelTitle=title;
    }


    public static Level levelStartUp(){
        ArrayList<Rectangle> colArr= new ArrayList<>();
        colArr.add(new Rectangle(0,500,960,60)); 
        colArr.add(new Rectangle(200,450,200,40));
        colArr.add(new Rectangle(500,300,100,20));
        Level startingLevel = new Level(null,null,colArr, "MAIN");
       
        ArrayList<Rectangle> colArr2= new ArrayList<>();
        colArr2.add(new Rectangle(0,500,960,60)); 
        colArr2.add(new Rectangle(100,400,200,40));
        colArr2.add(new Rectangle(320,200,100,20));
        Level leftLevel1 = new Level(null,startingLevel,colArr2, "LEFT");

        ArrayList<Rectangle> colArr3= new ArrayList<>();
        colArr3.add(new Rectangle(0,500,960,60)); 
        colArr3.add(new Rectangle(200,350,100,40));
        colArr3.add(new Rectangle(500,200,100,20));
        colArr3.add(new Rectangle(700,50,100,20));
        Level rightLevel1 = new Level(startingLevel,null,colArr3, "RIGHT");



        startingLevel.leftLevel = leftLevel1;
        startingLevel.rightLevel=rightLevel1;

        return startingLevel;
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.white);
        for(int i=0;i<collisionArray.size();i++){
            g2d.fill(collisionArray.get(i));
        }
        g2d.setColor(Color.red);
        g2d.drawString(levelTitle,400,100);
    }


}
