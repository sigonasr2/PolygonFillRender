package sig;

import javax.swing.JFrame;

public class PolygonFill {
	public static final String PROGRAM_NAME="Polygon Fill Renderer";
	public static void main(String[] args) {
		JFrame f = new JFrame(PROGRAM_NAME);
		Panel p = new Panel(f);
		
		p.init();
		
		f.add(p);
		f.setSize(1280,720);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		p.render();
	}
}
