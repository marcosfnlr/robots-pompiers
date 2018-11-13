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

	@Override
	public String toString() {
		return "Roues sur " + this.getPosition();
	}

}
