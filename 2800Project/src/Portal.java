import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Portal extends Enemy{
    private Ellipse2D portal;
    private Level nextLevel;
    private int xAfter, yAfter;
    public Portal(int x, int y, Level level, int xAfter, int yAfter) {
        super(x, y, 50, 80, 0);
        isInvincible = true;
        isAlive = false;
        nextLevel = level;
        this.xAfter = xAfter;
        this.yAfter = yAfter;
        hitBox = new Rectangle(0,0,0,0);
        this.portal = new Ellipse2D.Double(x,y,width, height);
    }

    public void tick(GameManager gm){
        Player player = gm.player;
        if(portal.intersects(player.rect())){
            gm.currLevel = nextLevel;
            player.x = xAfter;
            player.y = yAfter;
        }
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.magenta);
        g2d.fill(portal);
    }
}
