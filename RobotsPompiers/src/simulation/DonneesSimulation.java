package simulation;

import java.util.List;

import simulation.carte.Carte;
import simulation.carte.Incendie;
import simulation.robot.Robot;

public class DonneesSimulation {
	private Carte carte;
	private List<Incendie> incendies;
	private Robot[] robots;

	public DonneesSimulation(Carte carte, List<Incendie> incendies, Robot[] robots) {
		super();
		this.carte = carte;
		this.incendies = incendies;
		this.robots = robots;
	}

	public Carte getCarte() {
		return carte;
	}

	public List<Incendie> getIncendies() {
		return incendies;
	}

	public Robot[] getRobots() {
		return robots;
	}
}
