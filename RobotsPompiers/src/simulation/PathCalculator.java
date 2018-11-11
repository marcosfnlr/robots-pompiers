import java.util.Comparator;
import java.util.PriorityQueue;

public class PathCalculator {
    
	public static List<Node> calculate(Carte carte, Robot robot, Case src){

		PathCalculator pc = new PathCalculator(carte, robot, src);

		Node source = new Node(src);
		source.distance = 0;

		Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();
	 
	    unsettledNodes.add(source);
	 
	    while (unsettledNodes.size() != 0) {
	        Node currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);

	        Double edgeWeight = pc.WeightCalculator(currentNode.position);

	        Node norte = carte.getVoisin(currentNode.position, NORD);
	        Node sul = carte.getVoisin(currentNode.position, SUD);
	        Node leste = carte.getVoisin(currentNode.position, EST);
	        Node oeste = carte.getVoisin(currentNode.position, OUEST);

	        if (norte != null && !settledNodes.contains(norte)){
	        	calculateMinimumDistance(norte, edgeWeight, currentNode);
                unsettledNodes.add(norte);
	        }
	        if (sul != null && !settledNodes.contains(sul)){
	        	calculateMinimumDistance(sul, edgeWeight, currentNode);
                unsettledNodes.add(sul);
	        }
	        if (leste != null && !settledNodes.contains(leste)){
	        	calculateMinimumDistance(leste, edgeWeight, currentNode);
                unsettledNodes.add(leste);
	        }
	        if (oeste != null && !settledNodes.contains(oeste)){
	        	calculateMinimumDistance(oeste, edgeWeight, currentNode);
                unsettledNodes.add(oeste);
	        }
	        settledNodes.add(currentNode);

	}


	private class Node {
     
	    private Case position;
	     
	    private List<Node> shortestPath = new LinkedList<>();
	     
	    private Double distance = Double.MAX_VALUE;
	     
	    private Node(Case position) {
	        this.position = position;
	    }
	}



	private Carte carte;
	private Robot robot;
	private Case source;

    private PathCalculator(Carte carte, Robot robot, Case source){
    	this.carte = carte;
    	this.robot = robot;
    	this.source = source;
    }

    private double WeightCalculator(Case position){
    	return this.robot.getVitesse(position.getNature())/this.carte.getTailleCases();
    }

}
