package sig;

import javax.swing.JFrame;

public class PolygonFill {
	public static void main(String[] args) {
		Panel p = new Panel();
		JFrame f = new JFrame("Polygon Fill Renderer");
		
		p.init();
		
		f.add(p);
		f.setSize(1280,720);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		while (true) {
			p.render();
            try {Thread.sleep(20);} catch (InterruptedException e) {}
		}
	}
}
