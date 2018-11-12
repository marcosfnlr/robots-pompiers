package simulation.robot;

import simulation.RobotsPompiersException;
import simulation.Simulateur;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.NatureTerrain;
import simulation.evenement.Deplacer;
import simulation.evenement.Direction;
import simulation.evenement.Evenement;
import simulation.evenement.Intervenir;
import simulation.evenement.action.Action;
import simulation.evenement.action.Deplacement;

public abstract class Robot {

	private Simulateur simulateur;
	private Case position;
	private int vitesse;
	private int reservoir;
	private int vitesseRemplissage; // temps pour remplir tout le reservoir en secondes
	private int vitesseIntervention;
	private EtatRobot etat;
	private Action actionCourrent;

	public Robot(Case position, TypeRobot type) {
		setRobot(position, type);
		this.vitesse = type.getVitesse();
	}

	public Robot(Case position, TypeRobot type, int vitesse) {
		setRobot(position, type);
		this.vitesse = vitesse;
	}

	private void setRobot(Case position, TypeRobot type) {
		this.position = position;
		this.reservoir = type.getSizeReservoir();
		this.vitesseRemplissage = type.getRemplissage();
		this.vitesseIntervention = type.getIntervention();
		this.simulateur = null;
		this.actionCourrent = null;
		this.etat = EtatRobot.ARRETE;
	}

	public void addAction(Evenement evenement) throws RobotsPompiersException {
		if (this.actionCourrent != null)
			throw new RobotsPompiersException("Robot a deja une action. Pas possible de faire:" + evenement);
		if (evenement instanceof Deplacer) {
			addDeplacement((Deplacer) evenement);
		}
		if (evenement instanceof Intervenir) {
			addIntervention((Intervenir) evenement);
		}
	}

	private void addDeplacement(Deplacer deplacer) {
		Direction direction = Direction.getDirection(position, deplacer.getDestination());
		long dateFinal = deplacer.getDateDebut() + getCarte().getTailleCases() / getVitesse(this.position.getNature());
		this.actionCourrent = new Deplacement(direction, deplacer.getDateDebut(), dateFinal);
	}
	
	private void addIntervention(Intervenir intervenir) {
		
	}

	public EtatRobot getEtat() {
		return etat;
	}

	public void setEtat(EtatRobot etat) {
		this.etat = etat;
	}

	public Carte getCarte() {
		return simulateur.getDados().getCarte();
	}

	public Simulateur getSimulateur() {
		return simulateur;
	}

	public void setSimulateur(Simulateur simulateur) {
		this.simulateur = simulateur;
	}

	public int getVitesseRemplissage() {
		return this.vitesseRemplissage;
	}

	public int getVitesseIntervention() {
		return vitesseIntervention;
	}

	public int getReservoir() {
		return this.reservoir;
	}

	public Case getPosition() {
		return this.position;
	}

	public void setPosition(Case pos) {
		this.position = pos;
	}

	public int getVitesse() {
		return this.vitesse;
	}

	public int getX() {
		checkFinAction();
		int x = getPosition().getColonne() * Simulateur.PIXELS_PAR_CASE + Simulateur.PIXELS_PAR_CASE / 2;
		if (actionCourrent != null && actionCourrent instanceof Deplacement) {
			Deplacement deplacement = (Deplacement) actionCourrent;
			x += deplacement.getDeltaX(getSimulateur().getDateSimulation());
		}
		return x;
	}

	public int getY() {
		checkFinAction();
		int y = getPosition().getLigne() * Simulateur.PIXELS_PAR_CASE + Simulateur.PIXELS_PAR_CASE / 2;
		if (actionCourrent != null && actionCourrent instanceof Deplacement) {
			Deplacement deplacement = (Deplacement) actionCourrent;
			y += deplacement.getDeltaY(getSimulateur().getDateSimulation());
		}
		return y;
	}

	private void checkFinAction() {
		if (actionCourrent != null && getSimulateur().getDateSimulation() == actionCourrent.getDateFinal()) {
			if (actionCourrent instanceof Deplacement) {
				Deplacement deplacement = (Deplacement) actionCourrent;
				this.position = getCarte().getVoisin(position, deplacement.getDirection());
			}
			actionCourrent = null;
			etat = EtatRobot.ARRETE;
		}
	}

	public abstract int getVitesse(NatureTerrain terrain);

	public void deverserEau(int vol) {
		if (vol > this.reservoir)
			this.reservoir = 0;
		else
			this.reservoir -= vol;
	}

	public void remplirReservoir(TypeRobot type) {
		if (reservoir + type.getRemplissage() >= type.getSizeReservoir()) {
			reservoir = type.getSizeReservoir();
		} else {
			reservoir += type.getRemplissage();
		}

	}

	public abstract void remplirReservoir();

}
