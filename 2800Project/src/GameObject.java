import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GameObject {
	private BufferedImage sprite; // The sprite image
	public float x, y, width, height;
	// basically from lab. just has position and size, any subclasses of this will
	// have their own tick render and more.

	public GameObject(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		loadSprite("./lib/health.png");
	}

	private void loadSprite(String path) {
		try (InputStream inputStream = getClass().getResourceAsStream(path)) {
			if (inputStream != null) {
				sprite = ImageIO.read(inputStream);
			} else {
				throw new IOException("Resource not found: " + path);
			}
		} catch (IOException e) {
			System.out.println("Error loading sprite: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void tick(GameManager gm) {
	}

	public void render(Graphics2D g2d) {
		// Draw the sprite at the object's position
		g2d.drawImage(sprite, 0, 0, 0 + (int) width, 0 + (int) height, null);
	}

	public Rectangle rect() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}
}
