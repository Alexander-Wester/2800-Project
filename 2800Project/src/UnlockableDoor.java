import java.awt.*;

public class UnlockableDoor extends Enemy{

    private  Enemy condition;
    public UnlockableDoor(int x, int y, int w, int h, Enemy condition) {
        super(x, y, w,h,0);
        isAlive = false;
        isInvincible = true;
        this.condition = condition;
        hitBox = new Rectangle(x,y,w,h);
    }

    public void tick(GameManager gm){
        if(checkCondition()){
            hitBox.setLocation(-200,-200);
        }
    }

    public void render(Graphics2D g2d) {
        g2d.setColor(new Color(150,75,0));
        g2d.fill(hitBox);
    }
    public boolean checkCondition(){
        if(condition.isAlive == false){
            return true;
        }
        return false;
    }
}
