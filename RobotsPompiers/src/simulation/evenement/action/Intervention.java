package simulation.evenement.action;

import simulation.carte.Incendie;
import simulation.robot.Pattes;
import simulation.robot.Robot;

public class Intervention extends Action {

	private boolean finIncendie;
	private boolean repositoireVide;
	private Incendie incendie;

	public Intervention(long dateInicial, long dateFinal, boolean finIncendie, boolean repositoireVide,
			Incendie incendie) {
		super(dateInicial, dateFinal);
		this.finIncendie = finIncendie;
		this.repositoireVide = repositoireVide;
		this.incendie = incendie;
	}

	public boolean isFinIncendie() {
		return finIncendie;
	}

	public boolean isRepositoireVide() {
		return repositoireVide;
	}

	public Incendie getIncendie() {
		return incendie;
	}

	public void finir(Robot robot) {
		if (finIncendie) {
			incendie.eteindre();
		} else {
			incendie.setCible(false);
		}
		if (repositoireVide) {
			if (!(robot instanceof Pattes)) {
				robot.vider();
			}
			robot.getSimulateur().getChefPompier().assignerRemplissage(robot);
		} else {
			robot.getSimulateur().getChefPompier().assignerIncendie(robot);
		}
	}

}
