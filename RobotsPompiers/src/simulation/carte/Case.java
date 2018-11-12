package simulation.carte;

public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;

	public Case(int ligne, int colonne, NatureTerrain natureTerrain) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = natureTerrain;
	}

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public NatureTerrain getNature() {
		return nature;
	}

	@Override
	public String toString() {
		return "(" + ligne + "," + colonne + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Case) {
			Case c = (Case) o;
			return c.getLigne() == getLigne() && c.getColonne() == getColonne();
		}
		return super.equals(o);
	}
}
