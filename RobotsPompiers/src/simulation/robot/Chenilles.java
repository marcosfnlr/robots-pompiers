package simulation.robot;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;

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

	public void deverserEau(int vol, Incendie incendie) {
    	super.deverserEau(vol, incendie);
    }

    public void remplirReservoir(Carte carte) {
    	super.remplirReservoir(carte);
    }

}
