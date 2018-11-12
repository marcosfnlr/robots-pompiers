package simulation;

import java.util.List;

import simulation.DonneesSimulation;
import simulation.carte.Incendie;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.robot.Robot;
import simulation.robot.EtatRobot;

public class ChefPompier {
	
	private DonneesSimulation donnees;
	
	public ChefPompier(DonneesSimulation donnees) {
		this.donnees = donnees;
	}
	
	public void assignerRobotsIncendies(DonneesSimulation donnees) {
		
		Carte c = donnees.getCarte();
		int shortestPath = Integer.MAX_VALUE;
		Robot robotPlusProche;
		
		for(Incendie i : donnees.getIncendies()) {
			
			if(i.getLitres() <= 0) continue; //incendie deja fini
			
			Case lieuIncendie = i.getPosition();
			
			for(Robot r : donnees.getRobots()) {
				List<Case> liste = PathCalculator.calculate(c, r, lieuIncendie);
				if(r.getEtat() == EtatRobot.ARRETE && r.getReservoir() != 0) { //trouve robot en attente et non vide
					if (liste.size() < shortestPath) {
						shortestPath = liste.size();
						robotPlusProche = r;
					}
				}
				// chama criacao de sequencia de eventos
			}
		}
	}
	
}
