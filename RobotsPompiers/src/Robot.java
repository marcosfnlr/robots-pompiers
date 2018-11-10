public abstract class Robot {
	
	private Case position;
	private double vitesse;
	private int reservoir;
	private int minRemplissage;
	private int secIntervention;
	
	public int getMinRemplissage() {
		return minRemplissage;
	}

	public void setMinRemplissage(int minRemplissage) {
		this.minRemplissage = minRemplissage;
	}

	public Robot (Case position, double vitesse) {
		this.position = position;
		this.vitesse = vitesse;
	}

	public int getReservoir() {
		return reservoir;
	}

	public void setReservoir(int reservoir) {
		this.reservoir = reservoir;
	}

    public Case getPosition() {
    	return this.position;
    }

    public void setPosition(Case pos) {
    	this.position = pos;
    }
    
    public double getVitesse() {
		return this.vitesse;
	}

    public abstract double getVitesse(NatureTerrain terrain);

    public abstract void deverserEau(int vol);

    public abstract void remplirReservoir();
}
