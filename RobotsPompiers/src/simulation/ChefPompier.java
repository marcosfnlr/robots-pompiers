package simulation;

import java.util.List;

import simulation.DonneesSimulation;
import simulation.carte.Incendie;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.NatureTerrain;
import simulation.evenement.Direction;
import simulation.robot.Robot;
import simulation.robot.Drone;
import simulation.robot.EtatRobot;

public class ChefPompier {
	
	private DonneesSimulation donnees;
	
	public ChefPompier(DonneesSimulation donnees) {
		this.donnees = donnees;
	}
	
	public void assignerRobotsIncendie(DonneesSimulation donnees) {
		
		Carte carte = donnees.getCarte();
		int shortestPath = Integer.MAX_VALUE;
		Robot robotPlusProche;
		
		for(Incendie incendie : donnees.getIncendies()) {
			
			if(incendie.getLitres() <= 0) continue; //incendie deja fini
			
			Case lieuIncendie = incendie.getPosition();
			
			for(Robot robot : donnees.getRobots()) {
				List<Case> liste = PathCalculator.calculate(carte, robot, lieuIncendie);
				if(robot.getEtat() == EtatRobot.ARRETE && robot.getReservoir() != 0) { //trouve robot en attente et non vide
					if (liste.size() < shortestPath) {
						shortestPath = liste.size();
						robotPlusProche = robot;
					}	
				}
				// chama criacao de sequencia de eventos de deplacement para robotPlusProche e por ultimo o evento intevention
			}
		}
	}
	
	public void assignerRobotsRemplissage() {
		Carte carte = donnees.getCarte();
		int shortestPath = Integer.MAX_VALUE;
						
		for(Robot robot : donnees.getRobots()) {
			//type drone
			if(robot.getEtat() == EtatRobot.ARRETE && robot.getReservoir() == 0 && robot instanceof Drone) { //trouve drone en attente et vide
				for(Case position : carte.getCases()) {
					if(position.getNature() == NatureTerrain.EAU) {// trouve position dans l'eau
						List<Case> liste = PathCalculator.calculate(carte, robot, position);
						if(liste.size() < shortestPath) shortestPath = liste.size();
					}
				}
			}
			//autres types
			else if (robot.getEtat() == EtatRobot.ARRETE && robot.getReservoir() == 0) {
				for(Case position : carte.getCases()) {
					if(position.getNature() == NatureTerrain.EAU) {// trouve position dans l'eau
						for(Direction dir : Direction.values()){
							Case voisin = carte.getVoisin(position, dir);
							List<Case> liste = PathCalculator.calculate(carte, robot, voisin);
							if(liste.size() < shortestPath) shortestPath = liste.size();
						}						
					}
				}
			}
			
			// chamar criacao de sequencia de eventos de deplacement e por ultimo o evento de remplissage
		}
	}
	
}
