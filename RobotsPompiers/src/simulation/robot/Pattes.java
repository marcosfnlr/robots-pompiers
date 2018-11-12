package simulation.robot;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;

public class Pattes extends Terrestre{
	
	public Pattes (Case position, double vitesse) {
		super(position, vitesse);
		//reservoir infini. ne necessite pas remplir
		this.setVitesseIntervention(10);
	}
	
	public double getVitesse(NatureTerrain terrain) {
		if(terrain == NatureTerrain.ROCHE) return 10;
		else return this.getVitesse();
	}

	public void deverserEau(int vol) {
    	super.deverserEau(vol);
    }

    public void remplirReservoir(Carte carte) {
    	//reservoir infini
    	this.setReservoir(Integer.MAX_VALUE);
    }

}
