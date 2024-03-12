import java.awt.*;
import java.util.*;


public class Level {
    
    private Level leftLevel;
    private Level rightLevel;

    //ArrayList<Enemies>
    public ArrayList<Rectangle> collisionArray;

    public Level(Level left, Level right, ArrayList<Rectangle> colArr){//ADD ENEMY ARRAY
        leftLevel = left;
        rightLevel = right;
        collisionArray = colArr;
    }


    public static Level levelStartUp(){
        ArrayList<Rectangle> colArr= new ArrayList<>();
        colArr.add(new Rectangle(0,500,960,60)); 
        colArr.add(new Rectangle(200,450,200,40));
        colArr.add(new Rectangle(500,300,100,20));
        return new Level(null,null,colArr);
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.white);
        for(int i=0;i<collisionArray.size();i++){
            g2d.fill(collisionArray.get(i));
        }
    }


}
