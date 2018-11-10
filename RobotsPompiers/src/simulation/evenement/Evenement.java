package simulation.evenement;

public abstract class Evenement {
	private long dateDebut;
	private long dateFinal;

	Evenement(long dateDebut) {
		this.dateDebut = dateDebut;
	}

	public boolean isHappening(long date) {
		return date >= dateDebut && date <= dateFinal;
	}

	public long getDateFinal() {
		return this.dateFinal;
	}

	public void setDateFinal(long dateFinal) {
		this.dateFinal = dateFinal;
	}

	public long getDateDebut() {
		return this.dateDebut;
	}

	public abstract void execute();
}
