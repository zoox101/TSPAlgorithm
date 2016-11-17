import java.io.IOException;

public class AAADriver {
	
	public final static double millisecondsperpath = 0.0001854608;
	public final static String file = "Data/berlin52";
	
	public static void main(String[] args) throws IOException {
		
		Graph graph = Graph.fromFile(file + ".tsp");
		Path path = Path.fromFile(file + ".opt.tour");
		PrioritySearch search = new PrioritySearch(graph);
		//search.toFile(file + ".matrix");

		Draw draw = new Draw(graph);
		draw.add(path);
		System.out.println("Best: " + graph.pathLength(path));
		//draw.add(search);		
		
		Clock.in();
		Path mypath = search.run(1000, 100);
		long time = Clock.out();
		Draw mygraph = new Draw(graph);
		mygraph.add(mypath);
		System.out.println("Mine: " + graph.pathLength(mypath));
		System.out.println("Time: " + time);
		//mygraph.add(search);
		
	}
	

	
	private static int fact(int input) {
		if(input == 1) return 1;
		return input*fact(input-1);
	}
	
	public static String expectedtime(int nodes) {
		return "Expected time: " + fact(nodes)*millisecondsperpath/60000 + " minutes";
	}
	
}
