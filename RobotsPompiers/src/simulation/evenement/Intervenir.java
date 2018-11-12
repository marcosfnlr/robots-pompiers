package simulation.evenement;

import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Intervenir extends Evenement{
	private Robot robot;

	Intervenir (long date, Robot robot) {
		super(date);
		this.robot = robot;
	}
	
	public void execute() {
		robot.setEtat(EtatRobot.INTERVENTION);
		robot.addAction(this);
	}
}
