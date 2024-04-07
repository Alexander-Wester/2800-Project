import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Key extends Enemy {
    private boolean collected = false;
    private BufferedImage image;

    public Key(int x, int y) {
        super(x, y, 20, 30, 0);
        hitBox = new Rectangle(x, y, 20, 30);
        loadImage();
        isAlive = false;
        isInvincible = true;
    }

    public void tick(GameManager gm) {
        if (hitBox.intersects(gm.player.rect()) && !collected) {
            collected = true;
            gm.player.collectKey();
        }
    }

    public void render(Graphics2D g2d) {
        if (!collected) {
            g2d.drawImage(image, (int) x - 40, (int) y - 40, 100, 100, null);
        }
    }

    public void loadImage() {
        try {
            image = ImageIO.read(new File("lib/key.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
