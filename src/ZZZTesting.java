import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class ZZZTesting {
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(new File("pr2392.matrix")));
		int i=0;
		while(reader.readLine() != null) System.out.println(i++);
		reader.close();

		/*
		Graph graph = Graph.random(10);
		Path path = bruteForce(graph, null);
		System.out.println(graph.pathLength(path));
		
		graph.toFile("rand10.csv");
		graph = Graph.fromFile("rand10.csv");
		path = bruteForce(graph, null);
		System.out.println(graph.pathLength(path));
		*/
		
	}
	
	public static Path bruteForce(Graph graph, Path path) {
		
		if(path == null) {
			path = new Path();
			path.add(0);
		}
		
		if(path.size() == graph.size()) {
			path.getLength(graph);
			return path;
		}
		
		Path bestpath = null;
		for(int i=1; i<graph.size(); i++) {
			if(!path.contains(i)) {
				Path pathtosend = path.copy();
				pathtosend.add(i);
				Path newpath = bruteForce(graph, pathtosend);
				if(bestpath == null || newpath.length < bestpath.length)
					bestpath = newpath;
			}
		}
		
		return bestpath;
	}
}
