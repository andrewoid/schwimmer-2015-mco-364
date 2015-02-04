package schwimmer.smile;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class SmileComponent extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.YELLOW);
		g.fillOval(100, 100, 425, 425);
		
		g.setColor(Color.GREEN);
		g.fillOval(200, 200, 50, 50);
		
		g.fillOval(400, 200, 50, 50);
		
		g.drawArc(200, 250, 250, 200, 0, -180);
		
	}

	
	
	
}
