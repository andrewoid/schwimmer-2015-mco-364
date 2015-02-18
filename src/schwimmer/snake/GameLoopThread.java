package schwimmer.snake;

public class GameLoopThread extends Thread {

	private WorldComponent component;
	
	public GameLoopThread(WorldComponent component) {
		this.component = component;
	}

	public void run() {
		while (true) {
			// check input from user
			
			// updates game objects
			
			// see if the game is over
			
			// redraw screen
			component.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
