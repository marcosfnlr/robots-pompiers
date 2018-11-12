package simulation.evenement;

import simulation.carte.Case;

public enum Direction {
	NORD, SUD, EST, OUEST;

	public static Direction getDirection(Case src, Case dst) {
		int dif;
		if (src.getLigne() == dst.getLigne()) {
			dif = src.getColonne() - dst.getColonne();
		}
		if (src.getColonne() == dst.getColonne()) {
			dif = src.getLigne() - dst.getLigne();
		}
		return null;
	}
}
