package simulation.evenement.action;

import simulation.Simulateur;
import simulation.evenement.Direction;

public class Deplacement extends Action {

	private Direction direction;

	public Deplacement(Direction direction, long dateInicial, long dateFinal) {
		super(dateInicial, dateFinal);
		this.direction = direction;
	}

	public int getDeltaX(long dateCourrant) {
		switch (direction) {
		case EST:
			return (int) (Simulateur.PIXELS_PAR_CASE
					* (1 - (getDateFinal() - dateCourrant) * 1.0 / (getDateFinal() - getDateInicial())));
		case OUEST:
			int x = (int) (-1.0 * Simulateur.PIXELS_PAR_CASE
					* (1 - (getDateFinal() - dateCourrant) * 1.0 / (getDateFinal() - getDateInicial())));
			return x;
		default:
			return 0;
		}
	}

	public int getDeltaY(long dateCourrant) {
		switch (direction) {
		case NORD:
			return (int) (-1.0 * Simulateur.PIXELS_PAR_CASE
					* (1 - (getDateFinal() - dateCourrant) * 1.0 / (getDateFinal() - getDateInicial())));
		case SUD:
			return (int) (Simulateur.PIXELS_PAR_CASE
					* (1 - (getDateFinal() - dateCourrant) * 1.0 / (getDateFinal() - getDateInicial())));
		default:
			return 0;
		}
	}

	public Direction getDirection() {
		return direction;
	}

}
