package simulation.evenement.action;

public abstract class Action {

	private long dateInicial;
	private long dateFinal;

	public Action(long dateInicial, long dateFinal) {
		this.dateInicial = dateInicial;
		this.dateFinal = dateFinal;
	}

	public long getDateFinal() {
		return dateFinal;
	}

	public long getDateInicial() {
		return dateInicial;
	}

}
