package simulation.robot;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;

public class Drone extends Robot {
	
	public Drone (Case position, double vitesse) {
		super(position, vitesse);
		this.setReservoir(10000);
		this.setTempsRemplissage(30*60);
		this.setVitesseIntervention(10000/3);
	}
	
	public double getVitesse(NatureTerrain terrain) {
		return this.getVitesse();
	}

	public void deverserEau(int vol) {
    	super.deverserEau(vol);
    }

    public void remplirReservoir(Carte carte) {
    	super.remplirReservoir(carte);
    }

}
