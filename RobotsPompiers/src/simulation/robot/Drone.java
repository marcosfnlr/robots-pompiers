package simulation.robot;

import simulation.carte.Case;
import simulation.carte.NatureTerrain;

public class Drone extends Robot {

	public Drone(Case position) {
		super(position, TypeRobot.DRONE);
	}
	
	public Drone(Case position, int vitesse) {
		super(position, TypeRobot.DRONE, vitesse);
	}

	public int getVitesse(NatureTerrain terrain) {
		return this.getVitesse();
	}

	public void deverserEau(int vol) {
		super.deverserEau(vol);
	}

	public void remplirReservoir() {
		if (getCarte().isNearEau(getPosition())) {
			remplirReservoir(TypeRobot.DRONE);
		}

	}

	@Override
	public String toString() {
		return "Drone sur " + this.getPosition();
	}

}
