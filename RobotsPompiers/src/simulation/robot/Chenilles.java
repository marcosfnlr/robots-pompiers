package simulation.robot;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;
import simulation.evenement.Direction;

public class Chenilles extends Terrestre {
	
	public Chenilles (Case position, double vitesse) {
		super(position, 60);
		this.setReservoir(2000);
		this.setTempsRemplissage(5*60);
		this.setVitesseIntervention(12.5);
	}

	public double getVitesse(NatureTerrain terrain) {
		if(terrain == NatureTerrain.FORET) return 0.5 * this.getVitesse();
		else return this.getVitesse();
	}

	public void deverserEau(int vol) {
    	super.deverserEau(vol);
    }

	public void remplirReservoir(Carte carte) {
		Case position = this.getPosition();

		Case voisin = carte.getVoisin(position, Direction.NORD);
		if (voisin.getNature() == NatureTerrain.EAU)
			this.setReservoir(2000);

		voisin = carte.getVoisin(position, Direction.SUD);
		if (voisin.getNature() == NatureTerrain.EAU)
			this.setReservoir(2000);

		voisin = carte.getVoisin(position, Direction.EST);
		if (voisin.getNature() == NatureTerrain.EAU)
			this.setReservoir(2000);

		voisin = carte.getVoisin(position, Direction.OUEST);
		if (voisin.getNature() == NatureTerrain.EAU)
			this.setReservoir(2000);

	}

}
