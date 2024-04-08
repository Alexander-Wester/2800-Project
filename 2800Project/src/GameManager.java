import java.awt.*;
import java.util.LinkedList;
import java.util.ArrayList;

public class GameManager {

	//the most important, calls each object's tick and render individually.
	//currlevel is not a game object, but important and also has it's methods called here. 
	
	
	public LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
	Level currLevel = Level.level1(this);

	public ArrayList<Level> levelList;

	Player player;

	public void setLevelList(ArrayList<Level> l){
		this.levelList = l;
	}

	public LinkedList<GameObject> getGameObjects() {
        return gameObjects;
    }

	public boolean leftPortalActivated = false;

	public boolean rightPortalActivated = false;

	public void activateLeftPortal(){
		leftPortalActivated = true;
	}

	public void activateRightPortal(){
		rightPortalActivated = true;
	}
	public void tick() {
		//calls all gameobject's tick()
		for(int i = 0; i < gameObjects.size(); i++){
			gameObjects.get(i).tick(this);
		}
		currLevel.tick(this);
		player.tick(this);
		//NOTE: Enemy tick is called through the level tick function
	}
	
	public void render(Graphics2D g2d) {
		//calls all gameObject's render()
		//for(int i = 0; i < gameObjects.size(); i++) gameObjects.get(i).render(g2d);
		//System.out.print(".");
		currLevel.render(g2d);
		player.render(g2d);
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
		currLevel = Level.level1(this);
		//this is for death: currently just resets the whole game basically lol.
	}
}
