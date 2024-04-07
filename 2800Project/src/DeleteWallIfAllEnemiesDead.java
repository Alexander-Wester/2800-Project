import java.awt.Rectangle;
import java.util.ArrayList;

public class DeleteWallIfAllEnemiesDead extends Enemy{
    //ADD LAST TO ENEMY ARRAY
    //DELETES LAST WALL ONCE
    boolean deletedOnce = false;

    public DeleteWallIfAllEnemiesDead(){
    super(0,0,0,0,1);
    isInvincible=true;
    doesDamageOnCollision=false;
    hitBox = new Rectangle(1,1,1,1);
    }

    public void tick(GameManager gm){
        boolean allDead=true;
        ArrayList<Enemy> temp = gm.currLevel.enemyList;
        for(int i=0;i<temp.size()-1;i++){
            if(temp.get(i).isAlive() && temp.get(i).actualEnemy){
                allDead=false;
                break;
            }
        }
        if(allDead && !deletedOnce){
            gm.currLevel.collisionArray.remove(gm.currLevel.collisionArray.size()-1);
            deletedOnce=true;
        }
    }
}
