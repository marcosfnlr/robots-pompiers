package simulation.robot;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;
import simulation.evenement.Direction;

public class Roues extends Terrestre{
	
	public Roues (Case position, double vitesse) {
		super(position, 80);
		this.setReservoir(5000);
		this.setTempsRemplissage(10*60);
		this.setVitesseIntervention(20);
	}
	
	public double getVitesse(NatureTerrain terrain) {
		return this.getVitesse();	
	}

	public void deverserEau(int vol) {
    	super.deverserEau(vol);
    }

	public void remplirReservoir(Carte carte) {
		Case position = this.getPosition();

		Case voisin = carte.getVoisin(position, Direction.NORD);
		if (voisin.getNature() == NatureTerrain.EAU)
			this.setReservoir(5000);

		voisin = carte.getVoisin(position, Direction.SUD);
		if (voisin.getNature() == NatureTerrain.EAU)
			this.setReservoir(5000);

		voisin = carte.getVoisin(position, Direction.EST);
		if (voisin.getNature() == NatureTerrain.EAU)
			this.setReservoir(5000);

		voisin = carte.getVoisin(position, Direction.OUEST);
		if (voisin.getNature() == NatureTerrain.EAU)
			this.setReservoir(5000);

	}

}
