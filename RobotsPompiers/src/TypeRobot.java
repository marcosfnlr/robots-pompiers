
public enum TypeRobot {
	DRONE(100), CHENILLES(60), PATTES(30), ROUES(80);
	
	private double vitesse;

	public double getVitesse() {
		return vitesse;
	}

	private TypeRobot(double vitesse) {
		this.vitesse = vitesse;
	}
}
