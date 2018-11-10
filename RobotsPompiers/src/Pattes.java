
public class Pattes extends Terrestre{
	
	public Pattes (Case position, double vitesse) {
		super(position, vitesse);
		//reservoir infini
	}
	
	public double getVitesse(NatureTerrain terrain) {
		if(terrain == NatureTerrain.ROCHE) return 10;
		else return this.getVitesse();
	}

    public void deverserEau(int vol) {
    	
    }

    public void remplirReservoir() {
    	
    }

}
