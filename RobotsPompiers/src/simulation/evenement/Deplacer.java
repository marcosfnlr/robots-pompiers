package simulation.evenement;

import simulation.RobotsPompiersException;
import simulation.carte.Case;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Deplacer extends Evenement {
	private Robot robot;
	private Case destination;

	public Deplacer(long date, Robot robot, Case destination) {
		super(date);
		this.robot = robot;
		this.destination = destination;
	}

	public void execute() throws RobotsPompiersException{
		robot.setEtat(EtatRobot.DEPLACEMENT);
		robot.addAction(this);
	}

	public Case getDestination() {
		return this.destination;
	}

	@Override
	public String toString() {
		return "Deplacement du robot " + robot;
	}
}
