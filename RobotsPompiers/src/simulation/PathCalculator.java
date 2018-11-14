package simulation;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.NatureTerrain;
import simulation.evenement.Direction;
import simulation.robot.Chenilles;
import simulation.robot.Pattes;
import simulation.robot.Robot;
import simulation.robot.Roues;

/**
 * That class has a single public method that 
 * calculates the shortest path between a robot and
 * a given case.
 */
public class PathCalculator {

	/**
	 * Calcule le plus court chemin entre le robot et une case donnée, avec
	 * l'algorithme de Dijkstra. Ceci est méthode de classe; utilisation:
	 * PathCalculator.calculate(Carte carte, Robot robot, Case destination);
	 * 
	 * @param robot
	 *            Robot qui se déplace
	 * @param destination
	 *            Case où va le robot
	 * @return LinkedList avec des cases dans l'ordre à suivre, ou null si aucun
	 *         chemin n'existe
	 */
	public static PairListCaseDouble calculate(Carte carte, Robot robot, Case dest) {

		PathCalculator pathCalculator = new PathCalculator(carte, robot, dest);

		HashMap<Case, Double> dist = new HashMap<Case, Double>();

		for (Case x : carte.getCases()) {
			dist.put(x, Double.MAX_VALUE);
		}

		Comparator<PairDoubleCase> PairDoubleCaseComparator = new Comparator<PairDoubleCase>() {
			@Override
			public int compare(PairDoubleCase p1, PairDoubleCase p2) {
				return Double.compare(p1.getKey(), p2.getKey());
			}
		};
		Case src = robot.getPosition();

		PriorityQueue<PairDoubleCase> pq = new PriorityQueue<>(PairDoubleCaseComparator);
		dist.put(src, 0.0);
		pq.add(new PairDoubleCase(0.0, src));

		while (!pq.isEmpty()) {
			PairDoubleCase par = pq.poll();
			Case u = par.getValue();
			Double d = par.getKey();

			if (d > dist.get(u))
				continue;

			for (Direction dir : Direction.values()) {
				Case v = carte.getVoisin(u, dir);
				if (v == null || 
						robot instanceof Roues && v.getNature() != NatureTerrain.TERRAIN_LIBRE && v.getNature() != NatureTerrain.HABITAT ||
						robot instanceof Chenilles && (v.getNature() == NatureTerrain.EAU || v.getNature() == NatureTerrain.ROCHE) ||
						robot instanceof Pattes && v.getNature() == NatureTerrain.EAU)
					continue;
				Double peso = pathCalculator.WeightCalculator(u);
				if (dist.get(u) + peso < dist.get(v)) {
					dist.put(v, dist.get(u) + peso);
					pq.add(new PairDoubleCase(dist.get(v), v));
				}
			}
		}

		if (dist.get(dest) == Double.MAX_VALUE)
			return null;
		LinkedList<Case> lista = new LinkedList<Case>();
		Case currentCase = dest;
		while (!currentCase.equals(src)) {
			lista.add(currentCase);
			for (Direction dir : Direction.values()) {
				Case v = carte.getVoisin(currentCase, dir);
				if (v == null)
					continue;
				Double peso = pathCalculator.WeightCalculator(v);
				if (Math.abs(dist.get(currentCase) - peso - dist.get(v)) < 0.000000000001) {
					currentCase = v;
					break;
				}
			}
		}
		Collections.reverse(lista);
		PairListCaseDouble ld = new PairListCaseDouble(lista, dist.get(dest));
		return ld;
	}

	// Tout le reste de la classe est prive

	private Carte carte;
	private Robot robot;
	private Case dest;

	private PathCalculator(Carte carte, Robot robot, Case dest) {
		this.carte = carte;
		this.robot = robot;
		this.dest = dest;
	}

	private double WeightCalculator(Case position) {
		return (double) this.robot.getVitesse(position.getNature()) / this.carte.getTailleCases();
	}
}
