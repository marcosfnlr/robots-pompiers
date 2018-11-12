package simulation.evenement;

import simulation.RobotsPompiersException;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Intervenir extends Evenement{
	private Robot robot;

	Intervenir (long date, Robot robot) {
		super(date);
		this.robot = robot;
	}
	
	public void execute() throws RobotsPompiersException{
		robot.setEtat(EtatRobot.INTERVENTION);
		robot.addAction(this);
	}
}
