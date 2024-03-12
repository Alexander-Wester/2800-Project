import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		// create the main window and make it non-resizable
		JFrame mainWindow = new JFrame("Definitely Not Hollow Knight");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(960, 540);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setResizable(false);
		
		// create our game canvas
		GameCanvas gameCanvas = new GameCanvas();
		mainWindow.add(gameCanvas);
		mainWindow.setVisible(true);

		// start our game loop
		gameCanvas.start();
	}
	
}
