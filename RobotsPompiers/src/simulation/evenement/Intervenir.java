package simulation.evenement;

import simulation.RobotsPompiersException;
import simulation.carte.Incendie;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Intervenir extends Evenement {
	private Incendie incendie;

	public Intervenir(long date, Robot robot, Incendie incendie) {
		super(date, robot);
		this.incendie = incendie;
	}

	public void execute() throws RobotsPompiersException {
		getRobot().setEtat(EtatRobot.INTERVENTION);
		getRobot().addAction(this);
	}

	public Incendie getIncendie() {
		return incendie;
	}
}
