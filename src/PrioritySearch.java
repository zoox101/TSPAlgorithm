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
	
	//Creates a priority search object -- Initializes graph distances
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
	
	//Sorts the array of points by their distance to another point
	//Used by the constructor to decide what everyone's first pick is
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
	
	//Runs a (REALLY BAD) GA and returns the optimal population member
	public Path run(int generations, int popmembers) { 
		
		//Initializing populations
		ArrayList<ArrayList<Integer>> population = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<popmembers; i++) population.add(randomMember());
		
		//For every year...
		for(int i=0; i<generations; i++) {
			///For every member of the population...
			for(int j=0; j<population.size(); j++) {
				//Get the member of the population
				ArrayList<Integer> individual = population.get(j);
				//Mutate that individual
				ArrayList<Integer> newindividual = mutate(individual);
				//If the new individual is better than the old one, replace the old one
				if(graph.pathLength(toPath(newindividual)) < graph.pathLength(toPath(individual)))
					population.set(j, newindividual);
			}
		}
		
		//Look for the best member of the population
		Path best = toPath(population.get(0));
		for(int i=1; i<population.size(); i++)
			if(graph.pathLength(toPath(population.get(i))) < graph.pathLength(best))
				best = toPath(population.get(i));
		//Return the best member of the population
		return best;
	}
	
	//Generate a random population member
	//[1,5,3,2,4]
	private ArrayList<Integer> randomMember() {
		ArrayList<Integer> individual = new ArrayList<Integer>();
		for(int i=0; i<graph.size(); i++) {
			individual.add(i);
			individual.add(i);
		}
		Collections.shuffle(individual);
		return individual;
	}
	
	//Mutates the population member
	//[1,2,3,4,5] -> [1,4,3,2,5]
	private ArrayList<Integer> mutate(ArrayList<Integer> individual) {
		
		//Deep copying the individual
		ArrayList<Integer> newindividual = new ArrayList<Integer>();
		for(int i=0; i<individual.size(); i++)
			newindividual.add(individual.get(i));
		
		//Randomly swaps two of the nodes
		for(int i=0; i<MUTATION_RATE; i++) {
			int rand1 = (int) (Math.random() * individual.size());
			int rand2 = (int) (Math.random() * individual.size());
			int temp = newindividual.get(rand1);
			newindividual.set(rand1, newindividual.get(rand2));
			newindividual.set(rand2, temp);
		}
		
		//Return the mutated individual
		return newindividual;
	}
	
	//Generates a path from the priority list of points
	public Path toPath(ArrayList<Integer> queue) {
		
		//Generating an array to store the in/out for the path
		//Generating a hash-map to remember which node has been hit 
		HashMap<Integer,Integer> loopcontrol = new HashMap<Integer,Integer>();
		int[][] array = new int[graph.size()][2];
		for(int i=0; i<graph.size(); i++) {
			array[i][0] = -1;
			array[i][1] = -1;
			loopcontrol.put(i, i);
		}
		
		//For every node in the queue
		for(int i=0; i<queue.size(); i++) {
			int nodein = queue.get(i);
			
			//If the node isn't already full...
			if(loopcontrol.get(nodein) != -1) {
				
				//Get the list of favorites from the selected node
				ArrayList<Integer> nodeprox = matrix.get(nodein);
				//For every node in favorite...
				for(Integer nodeout: nodeprox) {
					
					//If the preferred node isn't full and the nodes aren't already connected
					if(loopcontrol.get(nodeout)!=-1 && loopcontrol.get(nodeout)!=nodein) {
												
						//Getting the values from the hash-map corresponding to the two selected nodes
						int inout = loopcontrol.get(nodein);
						int outout = loopcontrol.get(nodeout);
						
						//Creating chains... I think...
						if(inout == nodein) loopcontrol.put(nodein, outout);
						else {loopcontrol.put(nodein, -1); loopcontrol.put(inout, outout);}
						
						//Creating chains... I think
						if(outout == nodeout) loopcontrol.put(nodeout, inout);
						else {loopcontrol.put(nodeout, -1); loopcontrol.put(outout, inout);}
						
						//Connecting nodes
						if(array[nodein][0] == -1) array[nodein][0] = nodeout;
						else array[nodein][1] = nodeout;
						if(array[nodeout][0] == -1) array[nodeout][0] = nodein;
						else array[nodeout][1] = nodein;
						
						//Stop searching for a connection
						break;
					}
				}
			}
		}
		
		//Finding the beginning of the chain
		int start = -1;
		for(int i=0; i<array.length; i++) {
			if(array[i][1] == -1) {
				start = i;
				break;
			}
		}
		
		//Adding all the nodes to the path
		Path path = new Path();
		path.add(start);
		path.add(array[start][0]);
		for(int i=2; i<graph.size(); i++) {
			if(path.get(i-2) == array[path.get(i-1)][0]) path.add(array[path.get(i-1)][1]);
			else path.add(array[path.get(i-1)][0]);
		}
		
		//Returning the path
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


