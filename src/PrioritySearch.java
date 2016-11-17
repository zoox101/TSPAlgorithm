import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PrioritySearch {
	
	final static int MUTATION_RATE = 3; 
	
	public ArrayList<ArrayList<Integer>> matrix;
	public Graph graph;
	
	public PrioritySearch(Graph graph) {
		
		this.graph = graph;
		//Creating the matrix of point distances
		matrix = new ArrayList<ArrayList<Integer>>();
		for(Point startpoint: graph) {
			//Sorting distances
			ArrayList<Point> points = new ArrayList<Point>();
			for(Point endpoint: graph) points.add(endpoint);
			points.remove(startpoint);
			points = sortDist(startpoint,points);
			//Converting to graph index
			ArrayList<Integer> pointsingraph = new ArrayList<Integer>();
			for(Point point: points) pointsingraph.add(graph.indexOf(point));
			matrix.add(pointsingraph);
		}
	}
	
	private ArrayList<Point> sortDist(Point point, ArrayList<Point> points) {
		
		ArrayList<Point> pointssorted = new ArrayList<Point>();
		while(!points.isEmpty()) {
			Point minpoint = null;
			double mindist = Double.MAX_VALUE;
			for(Point endpoint: points)
				if(point.dist(endpoint) < mindist) {
					minpoint = endpoint; mindist = point.dist(minpoint);
				}
			points.remove(minpoint);
			pointssorted.add(minpoint);
		}
		points = pointssorted;
		return points;
	}
	
	public Path run(int generations, int popmembers) { 
		
		//Initializing populations
		ArrayList<ArrayList<Integer>> population = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<popmembers; i++) population.add(randomMember());
		
		for(int i=0; i<generations; i++) {
			for(int j=0; j<population.size(); j++) {
				ArrayList<Integer> individual = population.get(j);
				ArrayList<Integer> newindividual = mutate(individual);
				if(graph.pathLength(toPath(newindividual)) < graph.pathLength(toPath(individual)))
					population.set(j, newindividual);
			}
				
		}
		
		Path best = toPath(population.get(0));
		for(int i=1; i<population.size(); i++)
			if(graph.pathLength(toPath(population.get(i))) < graph.pathLength(best))
				best = toPath(population.get(i));
		return best;
	}
	
	private ArrayList<Integer> randomMember() {
		ArrayList<Integer> individual = new ArrayList<Integer>();
		for(int i=0; i<graph.size(); i++) {
			individual.add(i);
			individual.add(i);
		}
		Collections.shuffle(individual);
		return individual;
	}
	
	private ArrayList<Integer> mutate(ArrayList<Integer> individual) {
		
		ArrayList<Integer> newindividual = new ArrayList<Integer>();
		for(int i=0; i<individual.size(); i++)
			newindividual.add(individual.get(i));
		
		for(int i=0; i<MUTATION_RATE; i++) {
			int rand1 = (int) (Math.random() * individual.size());
			int rand2 = (int) (Math.random() * individual.size());
			int temp = newindividual.get(rand1);
			newindividual.set(rand1, newindividual.get(rand2));
			newindividual.set(rand2, temp);
		}
		
		return newindividual;
		
	}
	
	public Path toPath(ArrayList<Integer> queue) {
		
		HashMap<Integer,Integer> loopcontrol = new HashMap<Integer,Integer>();
		int[][] array = new int[graph.size()][2];
		for(int i=0; i<graph.size(); i++) {
			array[i][0] = -1;
			array[i][1] = -1;
			loopcontrol.put(i, i);
		}
		
		for(int i=0; i<queue.size(); i++) {
			int nodein = queue.get(i);
			
			//If the node isn't already full...
			if(loopcontrol.get(nodein) != -1) {
				
				//Add the next closest node that isn't full...
				ArrayList<Integer> nodeprox = matrix.get(nodein);
				for(Integer nodeout: nodeprox) {
					
					if(loopcontrol.get(nodeout)!=-1 && loopcontrol.get(nodeout)!=nodein) {
												
						int inout = loopcontrol.get(nodein);
						int outout = loopcontrol.get(nodeout);
						
						if(inout == nodein) loopcontrol.put(nodein, outout);
						else {loopcontrol.put(nodein, -1); loopcontrol.put(inout, outout);}
						
						if(outout == nodeout) loopcontrol.put(nodeout, inout);
						else {loopcontrol.put(nodeout, -1); loopcontrol.put(outout, inout);}
						
						if(array[nodein][0] == -1) array[nodein][0] = nodeout;
						else array[nodein][1] = nodeout;
						if(array[nodeout][0] == -1) array[nodeout][0] = nodein;
						else array[nodeout][1] = nodein;
						
						break;
					}
				}
			}
		}
		
		int start = -1;
		for(int i=0; i<array.length; i++) {
			if(array[i][1] == -1) {
				start = i;
				break;
			}
		}
		
		Path path = new Path();
		path.add(start);
		path.add(array[start][0]);
		for(int i=2; i<graph.size(); i++) {
			if(path.get(i-2) == array[path.get(i-1)][0]) path.add(array[path.get(i-1)][1]);
			else path.add(array[path.get(i-1)][0]);
		}
		return path;
	}
	
	public String toString() {
		String string = "";
		for(int i=0; i<matrix.size(); i++) {
			for(int j=0; j<matrix.get(i).size()-1; j++) 
				string += matrix.get(i).get(j) + ",";
			string += matrix.get(i).get(matrix.get(i).size()-1) + "\n";
		}
		return string;
	}
	
	public void toFile(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
		writer.write(this.toString());
		writer.close();
	}
}


