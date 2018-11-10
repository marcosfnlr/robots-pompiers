package simulation.robot;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;

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

	public void deverserEau(int vol, Incendie incendie) {
    	super.deverserEau(vol, incendie);
    }

    public void remplirReservoir(Carte carte) {
    	super.remplirReservoir(carte);
    }

}
