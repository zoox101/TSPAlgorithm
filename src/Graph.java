import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Graph extends ArrayList<Point> {
	private static final long serialVersionUID = -6048491240008123569L;
	
	public boolean validPath(ArrayList<Integer> path) {
		if(path.size() != this.size()) return false;
		for(int i=0; i<this.size(); i++)
			if(!path.contains(i)) return false;
		return true;
	}
	
	//12345
	public double pathLength(ArrayList<Integer> path) {
		double dist = 0;
		Point currentpoint; Point targetpoint;
		for(int i=0; i<path.size(); i++) {
			currentpoint = this.get(path.get(i));
			targetpoint = this.get(path.get((i+1)%path.size()));
			dist += currentpoint.dist(targetpoint);
		}
		return dist;
	}
	
	public String toString() {
		String string = "";
		for(int i=0; i<this.size(); i++)
			string += (i+1) + " " + this.get(i).x + " " + this.get(i).y + "\n";
		return string;
	}
	
	public static Graph fromString(String string) {
		Graph graph = new Graph();
		String[] points = string.split("\n");
		
		int counter=0;
		for(counter=0; counter<points.length; counter++) {
			String[] point = points[counter].trim().split(" ");
			if(point[0].equals("1")) break;
		}
		
		for(int i=counter; i<points.length; i++) {
			if(points[i].trim().equals("EOF")) break;
			String[] point = points[i].trim().split(" ");
			ArrayList<Double> ixy = new ArrayList<Double>();
			for(int j=0; j<point.length; j++) {
				if(point[j].equals(""));
				else ixy.add(Double.parseDouble(point[j]));
			}
			graph.add(new Point(ixy.get(1),ixy.get(2)));
		}
		return graph;
	}
	
	public static Graph random(int size) {
		Graph graph = new Graph();
		for(int i=0; i<size; i++)
			graph.add(Point.random());
		return graph;
	}
	
	public void toFile(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
		writer.write(this.toString());
		writer.close();
	}
	
	public static Graph fromFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
		String string = ""; String line;
		while((line=reader.readLine()) != null)
			string += line + "\n";
		reader.close();
		return Graph.fromString(string.trim());
	}
	
}
