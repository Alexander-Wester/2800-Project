import java.awt.*;
import java.util.LinkedList;

public class GameManager {

	//the most important, calls each object's tick and render individually.
	//currlevel is not a game object, but important and also has it's methods called here. 
	
	
	public LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
	Level currLevel = Level.level1();

	public void tick() {
		//calls all gameobject's tick()
		for(int i = 0; i < gameObjects.size(); i++){
			gameObjects.get(i).tick(this);
		}
		currLevel.tick(this);
		//NOTE: Enemy tick is called through the level tick function
	}
	
	public void render(Graphics2D g2d) {
		//calls all gameObject's render()
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

	public void setCurrentLevel(Level l){
		currLevel = l;
	}

	public void reset(){
		currLevel = Level.level1();
		//this is for death: currently just resets the whole game basically lol.
	}
}
