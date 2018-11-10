package simulation.carte;
public enum NatureTerrain {
	EAU(Couleur.BLUE), FORET(Couleur.GREEN), ROCHE(Couleur.BROWN), TERRAIN_LIBRE(Couleur.WHITE), HABITAT(
			Couleur.PURPLE);

	private Couleur couleur;

	private NatureTerrain(Couleur couleur) {
		this.couleur = couleur;
	}

	public Couleur getCouleur() {
		return couleur;
	}

}
