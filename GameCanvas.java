

import java.awt.*;
//import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
//import java.io.*;
//import javax.imageio.*;

// A custom Canvas class which will execute the game logic and drawing on a custom thread
public class GameCanvas extends Canvas implements Runnable {

	// Our game thread and the buffer strategy of our canvas
	private Thread thread;
	private BufferStrategy bufferStrategy;

	Player player = new Player();

	Point mousePos;

	public GameCanvas() { 
	}

	// This initalizes the buffering and starts the game thread.
	public void start() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();

		thread = new Thread(this, "Game Thread");
		thread.start(); // invokes the run() function

		addKeyListener(new playerListener(player, mousePos));
		setFocusable(true);

		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e){
				System.out.println("Mouse Pressed! mouse coords: " + e.getX() + " " + e.getY());
				player.swingSword(e.getX(),e.getY());
			}
			public void mouseExited(MouseEvent e){
			}
			public void mouseReleased(MouseEvent e){
			}
			public void mousePressed(MouseEvent e){
			}
			public void mouseEntered(MouseEvent e){
			}
		});
	}

	// Everything inside this function will be executed in the Game Thread
	@Override
	public void run() {
		/* the following code makes sure that the program updates (ticks)
		 * 60 times per second. And it makes sure that it draws (renders)
		 * maximum number of times per second. It also prints thse numbers
		 * every second for debugging purposes.
		 */
		
		long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0;
        //int updatesCounter = 0; // for counting the updates
        //int framesCounter = 0; // for counting the frames

		
		// Updates per second cap
		final int UPS_CAP = 60;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / (double) (1000000000 / UPS_CAP);
            lastTime = now;

            while (delta >= 1) {
                tick();
                //updatesCounter++;
                delta--;
            }

            render();
            //framesCounter++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("Updates per second: " + updatesCounter + ", Frames per second: " + framesCounter);
                //updatesCounter = 0;
                //framesCounter = 0;
            }
        }
	}

	private void tick() {
		player.playerLogic();	
	}
	
	// this function is called maximum times per second. Use it to draw the game's graphics
	private void render() {
		// Gets the graphics of the buffer and enables anti-aliasing (looks nicer but computationaly expensive)
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Drawing Begins______________________________

		// black background
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// our blue square color
		g2d.setColor(Color.blue);
		int[] playerPos = player.getPlayerPos();
		g2d.fillRect(playerPos[0],playerPos[1], 30, 60);

		if(player.getIsAttackOnline()){
			//System.out.println("Arc printing");
			g2d.setColor(Color.yellow);
			g2d.fillArc(player.getArcX()-20, player.getArcY()-25, 100, 100, (int)(player.getAttackAngle()*180/Math.PI - 15), 30);
		}

		g2d.dispose(); // Disposes the graphics after we are done drawing
		bufferStrategy.show();
	}


	public class playerListener implements KeyListener{
	
		private Player player;
		public playerListener(Player p, Point mousePos){
			player = p;
		}
	
		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_A){
				player.playerInputVeloX(-5);
				player.setKeyA(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_D){
				player.playerInputVeloX(5);
				player.setKeyD(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE){
				player.jump();
				
			}
		}
	
		@Override
		public void keyTyped(KeyEvent e){
			//empty i think
		}
	
		@Override
		public void keyReleased(KeyEvent e){
			if (e.getKeyCode() == KeyEvent.VK_A){
				player.setKeyA(false);
				if(!player.getKeyD()){
				player.playerInputVeloX(0);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_D){
				player.setKeyD(false);
				if(!player.getKeyA()){
				player.playerInputVeloX(0);
				}
			} 
		}
	
	}


}




