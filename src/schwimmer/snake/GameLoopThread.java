package schwimmer.snake;

public class GameLoopThread extends Thread {

	private SnakeComponent component;
	
	public GameLoopThread(SnakeComponent component) {
		this.component = component;
	}

	public void run() {
		while (true) {
			component.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
