package simulation.evenement.action;

import simulation.evenement.Direction;

public class Deplacement implements Action{

	private long dateFinal;
	private Direction direction;
	
	public Deplacement(Direction direction, long dateFinal) {
		this.direction = direction;
		this.dateFinal = dateFinal;
	}

	public long getDateFinal() {
		return dateFinal;
	}

	public Direction getDirection() {
		return direction;
	}

}
