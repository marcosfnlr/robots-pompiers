package simulation;

import java.util.ArrayList;
import java.util.List;

import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;
import simulation.evenement.Deplacer;
import simulation.evenement.Direction;
import simulation.evenement.Evenement;
import simulation.evenement.Intervenir;
import simulation.evenement.Remplir;
import simulation.robot.Drone;
import simulation.robot.Robot;

public class ChefPompier {

	private Simulateur simulateur;

	public ChefPompier(Simulateur simulateur) {
		this.simulateur = simulateur;
	}

	/**
	 * Assigne aux robot le incendie plus proche que n'est pas ciblé par d'autre
	 * robot
	 * 
	 * @param robot
	 */
	public void assignerIncendie(Robot robot) {

		Carte carte = simulateur.getCarte();

		List<Evenement> evenements = new ArrayList<Evenement>();
		List<Case> lista = null;
		Incendie incendiePlusProche = null;

		for (Incendie incendie : simulateur.getIncendies()) {
			if (incendie.isCible())
				continue;

			if (incendie.getLitres() <= 0)
				continue; // incendie deja fini

			double shortestPath = Double.MAX_VALUE;
			Case lieuIncendie = incendie.getPosition();
			PairListCaseDouble ld = PathCalculator.calculate(carte, robot, lieuIncendie);
			if (ld != null) { // reachable
				double dist = ld.getDist();
				if (dist < shortestPath) {
					shortestPath = dist;
					lista = ld.getLista();
					incendiePlusProche = incendie;
				}
			}
		}
		if (lista != null) {
			incendiePlusProche.setCible(true);
			long date = simulateur.getDateSimulation() + 1;
			Case marcherSur = robot.getPosition();
			for (Case pos : lista) {
				Deplacer dep = new Deplacer(date, robot, pos);
				evenements.add(dep);
				date += carte.getTailleCases() / robot.getVitesse(marcherSur.getNature()) + 1;
				marcherSur = pos;
			}
			Intervenir interv = new Intervenir(date, robot, incendiePlusProche);
			evenements.add(interv);

			for (Evenement e : evenements) {
				simulateur.ajouteEvenement(e);
			}
		}
	}

	/**
	 * Assigne aux robot le terrain plus proche que où il peut remplir son reservoir
	 * 
	 * @param robot
	 */
	public void assignerRemplissage(Robot robot) {
		Carte carte = simulateur.getCarte();

		List<Evenement> evenements = new ArrayList<Evenement>();

		double shortestPath = Double.MAX_VALUE;
		List<Case> liste = null;
		for (Case position : carte.getCases()) {
			if (position.getNature() == NatureTerrain.EAU) {// trouve position dans l'eau
				if (robot instanceof Drone) {
					PairListCaseDouble ld = PathCalculator.calculate(carte, robot, position);
					if (ld != null) {
						double dist = ld.getDist();
						if (dist < shortestPath) {
							shortestPath = dist;
							liste = ld.getLista();
						}
					}
				} else {
					for (Direction dir : Direction.values()) {
						Case voisin = carte.getVoisin(position, dir);
						if (voisin != null) {
							PairListCaseDouble ld = PathCalculator.calculate(carte, robot, voisin);
							if (ld != null) {
								double dist = ld.getDist();
								if (dist < shortestPath) {
									shortestPath = dist;
									liste = ld.getLista();
								}
							}
						}
					}
				}
			}
		}
		if (liste != null) {
			long date = simulateur.getDateSimulation() + 1;
			Case marcherSur = robot.getPosition();
			for (Case pos : liste) {
				Deplacer dep = new Deplacer(date, robot, pos);
				evenements.add(dep);
				date += carte.getTailleCases() / robot.getVitesse(marcherSur.getNature()) + 1;
				marcherSur = pos;
			}
			Remplir remplir = new Remplir(date, robot);
			evenements.add(remplir);
		}

		for (Evenement e : evenements) {
			simulateur.ajouteEvenement(e);
		}

	}

}
