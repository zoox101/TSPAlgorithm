
public class Point {
	
	final static double MAX_X = 10;
	final static double MAX_Y = 10;
	
	double x;
	double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double dist(Point that) {
		double xsq = Math.pow(this.x - that.x, 2);
		double ysq = Math.pow(this.y - that.y, 2);
		return Math.pow(xsq + ysq, 0.5);
	}
	
	public static Point random() {
		return new Point(Math.random()*MAX_X, Math.random()*MAX_Y);
	}

}
