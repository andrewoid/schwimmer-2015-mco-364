package schwimmer.smile;

import java.awt.Graphics;

import javax.swing.JComponent;

public class SmileComponent extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawOval(200, 100, 50, 400);
		
		
	}

	
	
	
}
