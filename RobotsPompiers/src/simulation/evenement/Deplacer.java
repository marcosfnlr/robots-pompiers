package simulation.evenement;

import simulation.RobotsPompiersException;
import simulation.carte.Case;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Deplacer extends Evenement {
	private Case destination;

	public Deplacer(long date, Robot robot, Case destination) {
		super(date, robot);
		this.destination = destination;
	}

	public void execute() throws RobotsPompiersException {
		getRobot().setEtat(EtatRobot.DEPLACEMENT);
		getRobot().addAction(this);
	}

	public Case getDestination() {
		return this.destination;
	}

	@Override
	public String toString() {
		return "Deplacement du robot " + getRobot();
	}
}
