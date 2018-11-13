package simulation.carte;

public class Incendie {

	private Case position;
	private int litres;
	private boolean isCible;

	public Incendie(Case position, int litres) {
		this.position = position;
		this.litres = litres;
		this.isCible = false;
	}

	public Case getPosition() {
		return position;
	}

	public int getLitres() {
		return litres;
	}

	public void setLitres(int litres) {
		this.litres = litres;
	}

	public boolean isCible() {
		return isCible;
	}

	public void setCible(boolean isCible) {
		this.isCible = isCible;
	}

	public void setPosition(Case position) {
		this.position = position;
	}

	public void eteindreIncendie(int vol) {
		this.litres -= vol;
	}

	public void eteindre() {
		litres = 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Incendie) {
			return (((Incendie) o).getPosition().equals(position));
		}
		return super.equals(o);
	}
}
