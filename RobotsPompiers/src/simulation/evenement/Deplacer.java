package simulation.evenement;

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
		calculerDateFinal();
	}

	private void calculerDateFinal() {
		this.setDateFinal(getDateDebut()
				+ (long) (robot.getCarte().getTailleCases() / robot.getVitesse(robot.getPosition().getNature())));
	}

	public void execute() {
		robot.setEtat(EtatRobot.DEPLACEMENT);
		if (robot.getSimulateur().getDateSimulation() == this.getDateFinal()) {
			robot.setPosition(destination);
			robot.setEtat(EtatRobot.ARRETE);
		}
	}
}
