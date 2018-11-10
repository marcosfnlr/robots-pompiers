
public class Roues extends Terrestre{
	
	public Roues (Case position, double vitesse) {
		super(position, 80);
		this.setReservoir(5000);
	}
	
	public double getVitesse(NatureTerrain terrain) {
		return this.getVitesse();	
	}

    public void deverserEau(int vol) {
    	
    }

    public void remplirReservoir() {
    	
    }

}
