package simulation.evenement;

import simulation.RobotsPompiersException;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Remplir extends Evenement{
	private Robot robot;

	Remplir (long date, Robot robot) {
		super(date);
		this.robot = robot;
	}
	
	public void execute() throws RobotsPompiersException{
		robot.setEtat(EtatRobot.REMPLISSAGE);
		robot.addAction(this);
	}
}
