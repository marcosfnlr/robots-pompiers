
public abstract class Terrestre extends Robot{
	
	public Terrestre (Case position, double vitesse) {
		super(position, vitesse);
	}
	
	public abstract double getVitesse(NatureTerrain terrain);

    public void deverserEau(int vol) {
    	
    }

    public void remplirReservoir() {
    	
    }

}
