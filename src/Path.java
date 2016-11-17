import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Path class - Maps out the route between points
public class Path extends ArrayList<Integer> {
	private static final long serialVersionUID = -6985652228774998974L;
	
	double length;
	
	public double getLength(Graph graph) { 
		this.length = graph.pathLength(this);
		return this.length;
	}
	
	public Path copy() {
		Path pathcopy = new Path();
		pathcopy.addAll(this);
		return pathcopy;
	}
	
	public static Path fromString(String string) {
		String[] points = string.split("\n");
		
		int counter = 0;
		for(counter = 0; counter<points.length; counter++)
			if(points[counter].equals("1")) break;
		
		Path path = new Path();
		for(int i=counter; i<points.length; i++) {
			if(points[i].equals("-1")) break;
			path.add(Integer.parseInt(points[i])-1);
		}
		return path;
	}
	
	public static Path fromFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
		String string = ""; String line;
		while((line=reader.readLine()) != null)
			string += line + "\n";
		reader.close();
		return Path.fromString(string.trim());
	}
	

}
