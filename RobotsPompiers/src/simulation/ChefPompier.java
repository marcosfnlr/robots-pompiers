package simulation;

import java.util.List;

import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;
import simulation.evenement.Deplacer;
import simulation.evenement.Direction;
import simulation.evenement.Intervenir;
import simulation.robot.Drone;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class ChefPompier {
	
	private DonneesSimulation donnees;
	
	public ChefPompier(DonneesSimulation donnees) {
		this.donnees = donnees;
	}
	
	public List assignerRobotsIncendie(DonneesSimulation donnees) {
		
		Carte carte = donnees.getCarte();
		double shortestPath = Double.MAX_VALUE;
		Robot robotPlusProche = null;
		
		for(Incendie incendie : donnees.getIncendies()) {
			
			if(incendie.getLitres() <= 0) continue; //incendie deja fini
			
			Case lieuIncendie = incendie.getPosition();
			
			for(Robot robot : donnees.getRobots()) {
				PairListCaseDouble ld = PathCalculator.calculate(carte, robot, lieuIncendie);
				List<Case> lista = ld.getLista();
				Double dist = ld.getDist();
				if(robot.getEtat() == EtatRobot.ARRETE && robot.getReservoir() != 0) { //trouve robot en attente et non vide
					if (dist < shortestPath) {
						shortestPath = dist;
						robotPlusProche = robot;
					}	
				}
				long date = robotPlusProche.getSimulateur().getDateSimulation();
				
				for(Case pos : lista) {
					Deplacer dep = new Deplacer(date, robotPlusProche, pos);
					// chama criacao de sequencia de eventos de deplacement para robotPlusProche e por ultimo o evento intevention
					date += carte.getTailleCases()/robotPlusProche.getVitesse(pos.getNature())+1;
				}
				Intervenir interv = new Intervenir(date, robotPlusProche, incendie);
			}
		}
	}
	
	public void assignerRobotsRemplissage() {
		Carte carte = donnees.getCarte();
		double shortestPath = Double.MAX_VALUE;
						
		for(Robot robot : donnees.getRobots()) {
			//type drone
			if(robot.getEtat() == EtatRobot.ARRETE && robot.getReservoir() == 0 && robot instanceof Drone) { //trouve drone en attente et vide
				for(Case position : carte.getCases()) {
					if(position.getNature() == NatureTerrain.EAU) {// trouve position dans l'eau
						PairListCaseDouble ld = PathCalculator.calculate(carte, robot, position);
						List<Case> liste = ld.getLista();
						Double dist = ld.getDist();
						if(dist < shortestPath) shortestPath = dist;
					}
				}
			}
			//autres types
			else if (robot.getEtat() == EtatRobot.ARRETE && robot.getReservoir() == 0) {
				for(Case position : carte.getCases()) {
					if(position.getNature() == NatureTerrain.EAU) {// trouve position dans l'eau
						for(Direction dir : Direction.values()){
							Case voisin = carte.getVoisin(position, dir);
							PairListCaseDouble ld = PathCalculator.calculate(carte, robot, voisin);
							List<Case> liste = ld.getLista();
							Double dist = ld.getDist();
							if(dist < shortestPath) shortestPath = dist;
						}						
					}
				}
			}
			
			// chamar criacao de sequencia de eventos de deplacement e por ultimo o evento de remplissage
		}
	}
	
}
