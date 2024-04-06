import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HealingFountain extends Enemy{

    private long healingTimer = 0;
    private BufferedImage image;
    public  HealingFountain(int x, int y, int w, int h){
        super(x,y,w,h,0);
        isAlive = false;
        isInvincible = true;
        hitBox = new Rectangle(x,y,w,h);
        loadImage();
    }

    public void tick(GameManager gm){
        if(System.currentTimeMillis() > healingTimer && hitBox.intersects(gm.player.rect())){
            if(gm.player.health < 5) {
                gm.player.health++;
                healingTimer = (System.currentTimeMillis() + 1000);
            }
        }
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.pink);
        g2d.drawImage(image,(int)x,(int)y,(int)width,(int)height,null);
    }

    public void loadImage(){
        try{
            image = ImageIO.read(new File("src/fountain.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
