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
import simulation.evenement.Remplir;
import simulation.evenement.action.Action;
import simulation.evenement.action.Deplacement;
import simulation.evenement.action.Intervention;
import simulation.evenement.action.Remplissage;

public abstract class Robot {

	private Simulateur simulateur;
	private Case position;
	private int vitesse;
	private int reservoir;
	private int sizeReservoir;
	private int vitesseRemplissage; // temps pour remplir tout le reservoir en secondes
	private int vitesseIntervention;
	private EtatRobot etat;
	private Action actionCourrent;

	public Robot(Case position, TypeRobot type) {
		setRobot(position, type);
		this.vitesse = type.getVitesse();
	}

	/**
	 * Cr�e un robot avec une vitesse different de la normale
	 * 
	 * @param position
	 *            case dans lequel se trouve le robot
	 * @param type
	 *            type du robot
	 * @param vitesse
	 *            vitesse standard du robot
	 */
	public Robot(Case position, TypeRobot type, int vitesse) {
		setRobot(position, type);
		this.vitesse = vitesse;
	}

	private void setRobot(Case position, TypeRobot type) {
		this.position = position;
		this.reservoir = type.getSizeReservoir();
		this.sizeReservoir = type.getSizeReservoir();
		this.vitesseRemplissage = type.getRemplissage();
		this.vitesseIntervention = type.getIntervention();
		this.simulateur = null;
		this.actionCourrent = null;
		this.etat = EtatRobot.ARRETE;
	}

	/**
	 * Gives an action to the robot.
	 * 
	 * @param evenement
	 * @throws RobotsPompiersException
	 *             If the robot is already doing something
	 */
	public void addAction(Evenement evenement) throws RobotsPompiersException {
		if (this.actionCourrent != null) {
			throw new RobotsPompiersException("Robot a deja une action. Pas possible de faire:" + evenement);
		}
		if (evenement instanceof Deplacer) {
			addDeplacement((Deplacer) evenement);
			return;
		}
		if (evenement instanceof Intervenir) {
			addIntervention((Intervenir) evenement);
			return;
		}
		if (evenement instanceof Remplir) {
			addRemplissage((Remplir) evenement);
			return;
		}
		throw new RobotsPompiersException("Action unconnu pour les robots.");
	}

	private void addDeplacement(Deplacer deplacer) {
		Direction direction = Direction.getDirection(position, deplacer.getDestination());
		long dateFinal = deplacer.getDateDebut() + getCarte().getTailleCases() / getVitesse(this.position.getNature());
		this.actionCourrent = new Deplacement(direction, deplacer.getDateDebut(), dateFinal);
	}

	private void addIntervention(Intervenir intervenir) {
		long dateFinal;
		boolean finIncendie = intervenir.getIncendie().getLitres() <= getReservoir();
		boolean repVide = intervenir.getIncendie().getLitres() >= getReservoir();
		if (intervenir.getIncendie().getLitres() > getReservoir()) {
			dateFinal = intervenir.getDateDebut() + getReservoir() / getVitesseIntervention();
		} else {
			dateFinal = intervenir.getDateDebut() + intervenir.getIncendie().getLitres() / getVitesseIntervention();
		}
		this.actionCourrent = new Intervention(intervenir.getDateDebut(), dateFinal, finIncendie, repVide,
				intervenir.getIncendie());
	}

	private void addRemplissage(Remplir remplir) {
		long dateFinal = remplir.getDateDebut() + (sizeReservoir - reservoir) / vitesseRemplissage;
		this.actionCourrent = new Remplissage(remplir.getDateDebut(), dateFinal);
	}

	public EtatRobot getEtat() {
		return etat;
	}

	public void setEtat(EtatRobot etat) {
		this.etat = etat;
	}

	public Carte getCarte() {
		return simulateur.getCarte();
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

	/**
	 * Gets the current maps horizontal position
	 * 
	 * @return x map coordinate
	 */
	public int getX() {
		int x = getPosition().getColonne() * Simulateur.PIXELS_PAR_CASE + Simulateur.PIXELS_PAR_CASE / 2;
		if (actionCourrent != null && actionCourrent instanceof Deplacement) {
			Deplacement deplacement = (Deplacement) actionCourrent;
			x += deplacement.getDeltaX(getSimulateur().getDateSimulation());
		}
		return x;
	}

	/**
	 * Gets the current maps vertical position
	 * 
	 * @return y map coordinate
	 */
	public int getY() {
		int y = getPosition().getLigne() * Simulateur.PIXELS_PAR_CASE + Simulateur.PIXELS_PAR_CASE / 2;
		if (actionCourrent != null && actionCourrent instanceof Deplacement) {
			Deplacement deplacement = (Deplacement) actionCourrent;
			y += deplacement.getDeltaY(getSimulateur().getDateSimulation());
		}
		return y;
	}

	/**
	 * Execute les bonnes t�ches d'apr�s la action courrant
	 */
	public void traiterAction() {
		if (actionCourrent != null) {
			boolean fini = getSimulateur().getDateSimulation() == actionCourrent.getDateFinal();
			if (actionCourrent instanceof Intervention) {
				Intervention intervention = (Intervention) actionCourrent;
				intervention.getIncendie()
						.eteindreIncendie(reservoir > vitesseIntervention ? vitesseIntervention : reservoir);
				deverserEau();
			}
			if (actionCourrent instanceof Remplissage) {
				remplirReservoir();
			}
			if (fini) {
				etat = EtatRobot.ARRETE;
				actionCourrent.finir(this);
				actionCourrent = null;
			}
		}
	}

	private void deverserEau() {
		if (!(this instanceof Pattes)) {
			if (reservoir > vitesseIntervention) {
				reservoir -= vitesseIntervention;
			} else {
				reservoir = 0;
			}
		}
	}

	public void vider() {
		reservoir = 0;
	}

	public void filler() {
		reservoir = sizeReservoir;
	}

	public abstract int getVitesse(NatureTerrain terrain);

	private void remplirReservoir() {
		if (!(this instanceof Pattes)) {
			if (reservoir + vitesseRemplissage >= sizeReservoir) {
				reservoir = sizeReservoir;
			} else {
				reservoir += vitesseRemplissage;
			}
		}

	}

}
