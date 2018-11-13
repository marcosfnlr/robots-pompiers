package simulation.evenement.action;

import simulation.robot.Robot;

public class Remplissage extends Action {

	public Remplissage(long dateInicial, long dateFinal) {
		super(dateInicial, dateFinal);
	}

	public void finir(Robot robot) {
		robot.filler();
		robot.getSimulateur().getChefPompier().assignerIncendie(robot);
	}

}
