import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Draw extends JPanel {
	private static final long serialVersionUID = 3282858454711637286L;
	
	public static int DISPLAY = 1000;
	public static int RADIUS = 10;
	public static double SCALE;
	
	public Path path;
	public Graph graph;
	public PrioritySearch search;
	
	public Draw(Graph graph) {
		
		super();
		this.graph = graph;
		JFrame frame = new JFrame();
		frame.add(this); frame.setVisible(true); frame.setSize(DISPLAY, DISPLAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int max = 0;
		for(Point point: graph)
			if(point.x > max || point.y > max)
				max = (int) Math.max(point.x, point.y);
		SCALE = 800.0 / max; //600
	}
		
	public void add(Path path) {this.path = path; repaint();}
	public void add(PrioritySearch search) {this.search = search; repaint();}
	
protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		
		if(graph != null) {
			for(int i=0; i<graph.size(); i++) {
				Point point = graph.get(i);
				g.drawOval((int)(point.x*SCALE), (int)(point.y*SCALE), 2*RADIUS, 2*RADIUS);
				g.drawString(String.valueOf(i), (int)(point.x*SCALE), (int)(point.y*SCALE));
			}
		}
		
		if(path != null && graph != null) {
			for(int i=0; i<path.size(); i++) {
				Point p1 = graph.get(path.get(i));
				Point p2 = graph.get(path.get((i+1)%graph.size()));
				g.drawLine((int)(p1.x*SCALE)+RADIUS, (int)(p1.y*SCALE)+RADIUS, (int)(p2.x*SCALE)+RADIUS, (int)(p2.y*SCALE)+RADIUS);
			}
		}
		
		Color[] colors = {Color.BLUE, Color.RED, Color.GREEN};
		
		if(search != null) {
			for(int cindex=0; cindex<colors.length; cindex++) {
				g.setColor(colors[cindex]);
				for(int i=0; i<search.matrix.size(); i++) {
					Point p1 = graph.get(i);
					Point p2 = graph.get(search.matrix.get(i).get(cindex));
					g.drawLine((int)(p1.x*SCALE)+RADIUS, (int)(p1.y*SCALE)+RADIUS, (int)(p2.x*SCALE)+RADIUS, (int)(p2.y*SCALE)+RADIUS);
				}
				
			}
		}
	}

}
