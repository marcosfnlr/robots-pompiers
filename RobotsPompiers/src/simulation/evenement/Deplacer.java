package simulation.evenement;

import simulation.carte.Case;
import simulation.evenement.action.Deplacement;
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

	public void execute() {
		robot.setEtat(EtatRobot.DEPLACEMENT);
		robot.addAction(this);
	}
}
