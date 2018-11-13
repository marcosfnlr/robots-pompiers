package simulation.evenement;

import simulation.RobotsPompiersException;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Remplir extends Evenement {

	public Remplir(long date, Robot robot) {
		super(date, robot);
	}

	public void execute() throws RobotsPompiersException {
		getRobot().setEtat(EtatRobot.REMPLISSAGE);
		getRobot().addAction(this);
	}
}
