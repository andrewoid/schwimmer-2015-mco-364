package schwimmer.snake;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class WorldComponent extends JComponent {

	private World world;
	
	public WorldComponent(World world) {
		this.world = world;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		
		world.draw(g);
		
		
		
		Snake snake = world.getSnake();
		g.fillRect(arg0, arg1, arg2, arg3);
		
		
	}

	
	
	
}
