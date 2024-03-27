import java.awt.*;

public class LavaTrap extends Enemy{
    public LavaTrap(int x, int y, int width, int height) {
        super(x, y, width, height, 0);
        isInvincible = true;
        hitBox = new Rectangle(x,y,width,height);
    }

    public void tick(GameManager gm){
        Player player = (Player) gm.gameObjects.get(0);
        if(hitBox.intersects(player.rect())){
            //player.jumpAllowed = true; //variable is private
            player.jump();
        }
    }
    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.fill(hitBox);
    }


}
