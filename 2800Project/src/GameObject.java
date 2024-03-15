import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GameObject {
	
	public float x, y, width, height;
	//basically from lab. just has position and size, any subclasses of this will have their own tick render and more. 

	public GameObject(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void tick(GameManager gm) {}
	public void render(Graphics2D g2d) {}
	public Rectangle rect() { return new Rectangle((int) x, (int) y, (int) width, (int) height); }
}
