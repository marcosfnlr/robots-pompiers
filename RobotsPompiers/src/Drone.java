
public class Drone extends Robot {
	
	public Drone (Case position, double vitesse) {
		super(position, vitesse);
		this.setReservoir(10000);
	}
	
	public double getVitesse(NatureTerrain terrain) {
		return this.getVitesse();
	}

    public void deverserEau(int vol) {
    	
    }

    public void remplirReservoir() {
    	
    }

}
