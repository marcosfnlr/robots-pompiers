package simulation.evenement;

import simulation.RobotsPompiersException;

public abstract class Evenement {
	private long dateDebut;

	Evenement(long dateDebut) {
		this.dateDebut = dateDebut;
	}

	public long getDateDebut() {
		return this.dateDebut;
	}

	public abstract void execute() throws RobotsPompiersException;
}
