package schwimmer.snake;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class SnakeFrame extends JFrame implements KeyListener {

	public SnakeFrame() {
		setSize(800, 600);
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		WorldComponent comp = new WorldComponent();
		comp.addKeyListener(this);
		comp.setFocusable(true);
		
		contentPane.add(comp);
		
		new GameLoopThread(comp).start();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// 4 or Left Arrow then turn to the left
		// 6 or Right Arrow then turn to the right
		System.out.println("keyPressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
}
