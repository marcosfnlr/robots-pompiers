package simulation.evenement;

import simulation.RobotsPompiersException;
import simulation.carte.Incendie;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Intervenir extends Evenement {
	private Robot robot;
	private Incendie incendie;

	public Intervenir(long date, Robot robot, Incendie incendie) {
		super(date);
		this.robot = robot;
		this.incendie = incendie;
	}

	public void execute() throws RobotsPompiersException {
		robot.setEtat(EtatRobot.INTERVENTION);
		robot.addAction(this);
	}

	public Incendie getIncendie() {
		return incendie;
	}
}
