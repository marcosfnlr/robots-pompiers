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
import simulation.robot.Drone;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class ChefPompier {

	private Simulateur simulateur;

	public ChefPompier(Simulateur simulateur) {
		this.simulateur = simulateur;
	}

	public void assignerRobotsIncendie() {

		Carte carte = simulateur.getCarte();

		List<Evenement> evenements = new ArrayList<Evenement>();

		for (Incendie incendie : simulateur.getIncendies()) {
			Robot robotPlusProche = null;
			List<Case> lista = null;
			double shortestPath = Double.MAX_VALUE;
			if (incendie.getLitres() <= 0) {
				continue; // incendie deja fini
			}

			for (Robot robot : simulateur.getRobots()) {
				if (robot.getEtat() == EtatRobot.ARRETE && robot.getReservoir() != 0) {// trouve robot en attente et
					Case lieuIncendie = incendie.getPosition();
					PairListCaseDouble ld = PathCalculator.calculate(carte, robot, lieuIncendie);

					double dist = ld.getDist();
					// non vide
					if (dist < shortestPath) {
						shortestPath = dist;
						if (robotPlusProche != null) {
							robotPlusProche.setEtat(EtatRobot.ARRETE);
						}
						robot.setEtat(EtatRobot.ATTENTE);
						robotPlusProche = robot;
						lista = ld.getLista();
					}
				}
			}
			if (robotPlusProche != null) {
				long date = simulateur.getDateSimulation() + 1;

				for (Case pos : lista) {
					Deplacer dep = new Deplacer(date, robotPlusProche, pos);
					evenements.add(dep);
					date += carte.getTailleCases() / robotPlusProche.getVitesse(pos.getNature()) + 1;
				}
				Intervenir interv = new Intervenir(date, robotPlusProche, incendie);
				evenements.add(interv);
			}
		}
		for (Evenement e : evenements) {
			simulateur.ajouteEvenement(e);
		}
	}

	public void assignerRobotsRemplissage() {
		Carte carte = simulateur.getCarte();
		double shortestPath = Double.MAX_VALUE;
		List<Evenement> evenements = new ArrayList<Evenement>();
		for (Robot robot : simulateur.getRobots()) {
			// type drone
			if (robot.getEtat() == EtatRobot.ARRETE && robot.getReservoir() == 0) {
				List<Case> liste;
				if (robot instanceof Drone) { // trouve drone en attente et vide

					for (Case position : carte.getCases()) {
						if (position.getNature() == NatureTerrain.EAU) {// trouve position dans l'eau
							PairListCaseDouble ld = PathCalculator.calculate(carte, robot, position);

							double dist = ld.getDist();
							if (dist < shortestPath) {
								shortestPath = dist;
								liste = ld.getLista();
							}
						}
					}
				} else {
					for (Case position : carte.getCases()) {
						if (position.getNature() == NatureTerrain.EAU) {// trouve position dans l'eau
							for (Direction dir : Direction.values()) {
								Case voisin = carte.getVoisin(position, dir);
								if (voisin != null) {
									PairListCaseDouble ld = PathCalculator.calculate(carte, robot, voisin);

									Double dist = ld.getDist();
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

			// chamar criacao de sequencia de eventos de deplacement e por ultimo o evento
			// de remplissage
		}
	}

}
