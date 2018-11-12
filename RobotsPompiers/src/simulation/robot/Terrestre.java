package simulation.robot;

import simulation.carte.Case;
import simulation.carte.NatureTerrain;

public abstract class Terrestre extends Robot {

	public Terrestre(Case position, TypeRobot type) {
		super(position, type);
	}

	public Terrestre(Case position, TypeRobot type, int vitesse) {
		super(position, type, vitesse);
	}

	public abstract int getVitesse(NatureTerrain terrain);

}
