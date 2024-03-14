import java.awt.*;
import java.util.ArrayList;

public class StalagtiteTrap extends Enemy{

    boolean isFalling = false;
    Polygon visualPolygon;
    

    public StalagtiteTrap(int x1, int y1, int w1, int h1, int h2){
        //width,height and health are useless for this. 
        super(x1,y1,w1,h1,h2);
        isInvincible=true;
        hitBox = new Rectangle((int)x+5,(int)y,10,25);
    }

    public void tick(GameManager gm){
        hitBox = new Rectangle((int)x+5,(int)y,10,25);
       
        if(Math.abs(gm.gameObjects.get(0).x-x)<50){
           isFalling=true;
        }
        if(isFalling){
            Rectangle tempRectangle = new Rectangle((int)x,(int)(y+5+height),(int)width,5);
            ArrayList<Rectangle> arr=gm.getCurrentLevel().collisionArray;
            for(int i=0;i<arr.size();i++){
              if(tempRectangle.intersects(arr.get(i))){ 
                  isFalling=false;
                  isAlive=false;
              }
        }
    }
        if(isFalling){
            y=y+6;
        }
}
    public void render(Graphics2D g2d){
        int[] xVals = new int[]{(int)x,(int)x+10,(int)x+20};
        int[] yVals = new int[]{(int)y,(int)y+30,(int)y};
        visualPolygon = new Polygon(xVals,yVals,3);
        
       g2d.setColor(Color.gray);
        g2d.fill(visualPolygon);
    }

}
