package simulation.robot;

public enum TypeRobot {
	DRONE(100, 10000, 30, 10000 / 30), ROUES(80, 5000, 10, 100 / 5), CHENILLES(60, 2000, 5, 100 / 8), PATTES(30, -1, -1,
			10);

	private int vitesse;
	private int sizeReservoir;
	private int remplissage;
	private int intervention;

	private TypeRobot(int vitesse, int sizeReservoir, int remplissage, int intervention) {
		this.vitesse = vitesse;
		this.sizeReservoir = sizeReservoir;
		this.remplissage = remplissage;
		this.intervention = intervention;
	}

	public int getVitesse() {
		return vitesse;
	}

	public int getSizeReservoir() {
		return sizeReservoir;
	}

	public int getRemplissage() {
		return remplissage;
	}

	public int getIntervention() {
		return intervention;
	}

}
