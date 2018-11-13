package simulation.robot;

import simulation.carte.Case;
import simulation.carte.NatureTerrain;

public class Chenilles extends Terrestre {

	public Chenilles(Case position) {
		super(position, TypeRobot.CHENILLES);
	}

	public Chenilles(Case position, int vitesse) {
		super(position, TypeRobot.CHENILLES, vitesse);
	}

	public int getVitesse(NatureTerrain terrain) {
		if (terrain == NatureTerrain.FORET)
			return this.getVitesse() / 2;
		else
			return this.getVitesse();
	}

	@Override
	public String toString() {
		return "Chenilles sur " + this.getPosition();
	}

}
