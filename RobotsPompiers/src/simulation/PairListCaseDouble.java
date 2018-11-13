package simulation;
import java.util.LinkedList;
import simulation.carte.Case;

public class PairListCaseDouble {
	private LinkedList<Case> lista;
	private Double dist;
	
	public PairListCaseDouble(LinkedList<Case> lista, Double dist) {
		this.lista = lista;
		this.dist = dist;
	}
	public LinkedList<Case> getLista() {
		return lista;
	}
	public Double getDist() {
		return dist;
	}
	
	
}
