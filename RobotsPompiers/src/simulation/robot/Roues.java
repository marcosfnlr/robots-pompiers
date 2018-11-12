package simulation.robot;

import simulation.carte.Case;
import simulation.carte.NatureTerrain;

public class Roues extends Terrestre {

	public Roues(Case position) {
		super(position, TypeRobot.ROUES);
	}

	public Roues(Case position, int vitesse) {
		super(position, TypeRobot.ROUES, vitesse);
	}

	public int getVitesse(NatureTerrain terrain) {
		return this.getVitesse();
	}

	public void deverserEau(int vol) {
		super.deverserEau(vol);
	}

	public void remplirReservoir() {
		if (getCarte().isNearEau(getPosition())) {
			remplirReservoir(TypeRobot.ROUES);
		}
	}

	@Override
	public String toString() {
		return "Roues sur " + this.getPosition();
	}

}
