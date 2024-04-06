

import javax.swing.*;
import java.awt.*;
//import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
//import java.io.*;
//import javax.imageio.*;

//simple canvas and thread.
//important: this file contains mouse and keyboard listener, for new tools you will likely have to add new if statements for new keys.
//framerate decided here, and background and basic frawing components are here too.

public class GameCanvas extends Canvas implements Runnable {

	private Thread thread;
	private BufferStrategy bufferStrategy;

	private GameManager gameManager = new GameManager();
	Player player = new Player();

	Point mousePos;

	public GameCanvas() { 
		//gameManager.addGameObject(player);
		gameManager.player = player;
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
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					player.swingSword(e.getX(), e.getY());
				} else if (SwingUtilities.isRightMouseButton(e)) {
					player.fireball(e.getX(), e.getY());
				}
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
		//this activates the real code
		gameManager.tick();	
	}
	
	// this function is called maximum times per second. Use it to draw the game's graphics
	private void render() {
		// Gets the graphics of the buffer and enables anti-aliasing (looks nicer but computationaly expensive)
		Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// black background
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		//this draws the real code
		gameManager.render(g2d);

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
				if(player.isRunning()){
					player.playerInputVeloX(-10);
				} else {
					player.playerInputVeloX(-5);
				}
				player.setKeyA(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_D){
				if(player.isRunning()){
					player.playerInputVeloX(10);
				} else {
					player.playerInputVeloX(5);
				}
				player.setKeyD(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE){
				player.jump();
				
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT){
				if(player.canRun()){
					player.playerRun(true);
				}
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
			if(e.getKeyCode() == KeyEvent.VK_SHIFT){
				player.playerRun(false);
			}
		}
	
	}


}



