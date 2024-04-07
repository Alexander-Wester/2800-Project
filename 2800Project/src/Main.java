import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		// very simple create frame and gameCanvas, nothing special here.
		JFrame mainWindow = new JFrame("Definitely Not Hollow Knight");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(960, 540);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setResizable(false);

		GameCanvas gameCanvas = new GameCanvas();
		mainWindow.add(gameCanvas);
		mainWindow.setVisible(true);

		gameCanvas.start();
	}

}
