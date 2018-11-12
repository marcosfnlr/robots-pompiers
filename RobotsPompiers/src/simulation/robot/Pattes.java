package simulation.robot;

import simulation.carte.Case;
import simulation.carte.NatureTerrain;

public class Pattes extends Terrestre {

	public Pattes(Case position) {
		super(position, TypeRobot.PATTES);
	}
	
	public Pattes(Case position, int vitesse) {
		super(position, TypeRobot.PATTES, vitesse);
	}

	public int getVitesse(NatureTerrain terrain) {
		if (terrain == NatureTerrain.ROCHE)
			return 10;
		else
			return this.getVitesse();
	}

	public void deverserEau(int vol) {
		super.deverserEau(vol);
	}

	public void remplirReservoir() {
		
	}

	@Override
	public String toString() {
		return "Pattes sur " + this.getPosition();
	}

}
