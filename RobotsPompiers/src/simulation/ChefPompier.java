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

	public void assignerIncendie(Robot robot) {

		Carte carte = simulateur.getCarte();

		List<Evenement> evenements = new ArrayList<Evenement>();
		List<Case> lista = null;
		Incendie incendiePlusProche = null;

		for (Incendie incendie : simulateur.getIncendies()) {
			if (!incendie.isCible()) {
				double shortestPath = Double.MAX_VALUE;
				if (incendie.getLitres() <= 0) {
					continue; // incendie deja fini
				}

				Case lieuIncendie = incendie.getPosition();
				PairListCaseDouble ld = PathCalculator.calculate(carte, robot, lieuIncendie);

				double dist = ld.getDist();
				// non vide
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
			for (Case pos : lista) {
				Deplacer dep = new Deplacer(date, robot, pos);
				evenements.add(dep);
				date += carte.getTailleCases() / robot.getVitesse(pos.getNature()) + 1;
			}
			Intervenir interv = new Intervenir(date, robot, incendiePlusProche);
			evenements.add(interv);

			for (Evenement e : evenements) {
				simulateur.ajouteEvenement(e);
			}
		}
	}

	public void assignerRemplissage(Robot robot) {
		Carte carte = simulateur.getCarte();

		List<Evenement> evenements = new ArrayList<Evenement>();

		double shortestPath = Double.MAX_VALUE;
		List<Case> liste = null;
		for (Case position : carte.getCases()) {
			if (position.getNature() == NatureTerrain.EAU) {// trouve position dans l'eau
				if (robot instanceof Drone) {
					PairListCaseDouble ld = PathCalculator.calculate(carte, robot, position);
					double dist = ld.getDist();
					if (dist < shortestPath) {
						shortestPath = dist;
						liste = ld.getLista();
					}
				} else {
					for (Direction dir : Direction.values()) {
						Case voisin = carte.getVoisin(position, dir);
						if (voisin != null) {
							PairListCaseDouble ld = PathCalculator.calculate(carte, robot, voisin);
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
		if (liste != null) {
			long date = simulateur.getDateSimulation() + 1;

			for (Case pos : liste) {
				Deplacer dep = new Deplacer(date, robot, pos);
				evenements.add(dep);
				date += carte.getTailleCases() / robot.getVitesse(pos.getNature()) + 1;
			}
			Remplir remplir = new Remplir(date, robot);
			evenements.add(remplir);
		}

		for (Evenement e : evenements) {
			simulateur.ajouteEvenement(e);
		}

	}

}
