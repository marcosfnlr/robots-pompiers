package simulation.evenement;

import simulation.RobotsPompiersException;
import simulation.robot.Robot;

public abstract class Evenement {
	private long dateDebut;
	private Robot robot;

	Evenement(long dateDebut, Robot robot) {
		this.dateDebut = dateDebut;
		this.robot = robot;
	}

	public Robot getRobot() {
		return robot;
	}

	public long getDateDebut() {
		return this.dateDebut;
	}

	public abstract void execute() throws RobotsPompiersException;
}
