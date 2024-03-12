import java.awt.*;
import java.util.LinkedList;

public class GameManager {
	
	public LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
	Level currLevel = Level.levelStartUp();

	public void tick() {
		for(int i = 0; i < gameObjects.size(); i++){
			gameObjects.get(i).tick(this);
		}
	}
	
	public void render(Graphics2D g2d) {
		for(int i = 0; i < gameObjects.size(); i++) gameObjects.get(i).render(g2d);
		currLevel.render(g2d);
	}

	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	public void removeGameObject(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}

	public Level getCurrentLevel(){
		return currLevel;
	}
}
