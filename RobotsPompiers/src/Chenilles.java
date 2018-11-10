
public class Chenilles extends Terrestre {
	
	public Chenilles (Case position, double vitesse) {
		super(position, 60);
		this.setReservoir(2000);
	}
	
	public double getVitesse(NatureTerrain terrain) {
		if(terrain == NatureTerrain.FORET) return 0.5 * this.getVitesse();
		else return this.getVitesse();
	}

    public void deverserEau(int vol) {
    	
    }

    public void remplirReservoir() {
    	
    }

}
