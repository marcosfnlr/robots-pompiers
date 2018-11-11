package simulation;
import java.util.PriorityQueue;
import java.util.Collections;
import simulation.carte.Carte;
import simulation.robot.Robot;
import simulation.carte.Case;
import simulation.evenement.Direction;

public class PathCalculator {
    
	public static List<Case> calculate(Carte carte, Robot robot, Case dest){

		PathCalculator pathCalculator = new PathCalculator(carte, robot, dest);

		Map<Case, Double> dist = new HashMap<Case, Double>();

		for(Case x : carte.getCases()){
			dist.put(x, Double.MAX_VALUE);
		}	

		Comparator<PairDoubleCase> PairDoubleCaseComparator = new Comparator<PairDoubleCase>(){
			@Override
			public int compare(PairDoubleCase p1, PairDoubleCase p2){
				return Double.compare(p1.getKey(), p2.getKey());
			}
		};
		Case src = robot.getPosition();

		PriorityQueue<PairDoubleCase> pq = new PriorityQueue<>(PairDoubleCaseComparator);
		dist.put(src, 0);
		pq.add(new Pair(0, src));

		while (!pq.isEmpty()){
			PairDoubleCase par = pq.poll();
			Case u = par.getValue();
			Double d = par.getKey();

			if(d > dist[u]) continue;

			for(Direction dir : Direction.values()){
				Case v = carte.getVoisin(u, dir);
				if(v == null) continue;
				Double peso = pathCalculator.WeightCalculator(u);
				if(dist[u] + peso < dist[v]){
                    dist[v] = dist[u] + peso;
                    pq.add(new Pair(dist[v], v));
                }
			}
		}

		if(dist[dest] == Double.MAX_VALUE) return null;
		LinkedList<Case> lista = new LinkedList<Case>();
		Case currentCase = dest;
		while(currentCase != src){
			lista.add(currentCase);
			for(Direction dir : Direction.values()){
				Case v = carte.getVoisin(currentCase, dir);
				if(v == null) continue;
				Double peso = pathCalculator.WeightCalculator(currentCase);
				if(dist[currentCase] - peso == dist[v]){
					currentCase = v;
					break;
				}
			}
		}
		Collections.reverse(lista);
		return lista;
	}


	private Carte carte;
	private Robot robot;
	private Case dest;

    private PathCalculator(Carte carte, Robot robot, Case dest){
    	this.carte = carte;
    	this.robot = robot;
    	this.dest = dest;
    }

    private double WeightCalculator(Case position){
    	return this.robot.getVitesse(position.getNature())/this.carte.getTailleCases();
    }
}
