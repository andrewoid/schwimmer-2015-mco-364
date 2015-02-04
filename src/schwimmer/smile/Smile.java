package schwimmer.smile;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class Smile extends JFrame {

	
	public Smile() {
		setSize(800, 600);
		setTitle("SMILE :)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		
		contentPane.add(new SmileComponent());
		
		
	}
	
	public static void main(String args[]) {
		new Smile().setVisible(true);
	}
	
	
}