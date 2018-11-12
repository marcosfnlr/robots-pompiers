package simulation.robot;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;

public abstract class Terrestre extends Robot{
	
	public Terrestre (Case position, double vitesse) {
		super(position, vitesse);
	}
	
	public abstract double getVitesse(NatureTerrain terrain);

	public void deverserEau(int vol) {
    	super.deverserEau(vol);
    }

    public abstract void remplirReservoir(Carte carte);

}
