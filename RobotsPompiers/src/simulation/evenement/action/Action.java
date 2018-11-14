package simulation.evenement.action;

import simulation.robot.Robot;

public abstract class Action {

	private long dateInicial;
	private long dateFinal;

	public Action(long dateInicial, long dateFinal) {
		this.dateInicial = dateInicial;
		if (dateFinal == dateInicial) {
			dateFinal++;
		}
		this.dateFinal = dateFinal;
	}

	public long getDateFinal() {
		return dateFinal;
	}

	public long getDateInicial() {
		return dateInicial;
	}

	/**
	 * Faire les choses qu'il faut executer quand l'action est fini
	 * 
	 * @param robot
	 */
	public abstract void finir(Robot robot);

}
