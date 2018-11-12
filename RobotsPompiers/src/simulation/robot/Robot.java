package simulation.robot;

import simulation.Simulateur;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.NatureTerrain;
import simulation.evenement.Deplacer;
import simulation.evenement.Direction;
import simulation.evenement.Evenement;
import simulation.evenement.action.Action;
import simulation.evenement.action.Deplacement;

public abstract class Robot {

	private Simulateur simulateur;
	private Case position;
	private double vitesse;
	private int reservoir;
	private int tempsRemplissage; // temps pour remplir tout le reservoir en secondes
	private double vitesseIntervention; // litres per secondes
	private EtatRobot etat;
	private Action actionCourrent;

	public Robot(Case position, double vitesse) {
		this.position = position;
		this.vitesse = vitesse;
		this.simulateur = null;
		this.actionCourrent = null;
		this.etat = EtatRobot.ARRETE;
	}
	
	public void addAction(Evenement evenement) throws Exception {
		if(this.actionCourrent != null) throw new Exception();
		if(evenement instanceof Deplacer) {
			addDeplacement((Deplacer)evenement);
		}
	}
	
	public void addDeplacer(Deplacer deplacer) {
		
	}
	
	private void addDeplacement(Deplacer d) throws Exception {
		if(this.actionCourrent != null) throw new Exception();
		if(d instanceof Deplacer) {
			
		}
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

	public int getTempsRemplissage() {
		return this.tempsRemplissage;
	}

	public void setTempsRemplissage(int tempsRemplissage) {
		this.tempsRemplissage = tempsRemplissage;
	}

	public double getVitesseIntervention() {
		return vitesseIntervention;
	}

	public void setVitesseIntervention(double vitesseIntervention) {
		this.vitesseIntervention = vitesseIntervention;
	}

	public int getReservoir() {
		return this.reservoir;
	}

	public void setReservoir(int reservoir) {
		this.reservoir = reservoir;
	}

	public Case getPosition() {
		return this.position;
	}

	public void setPosition(Case pos) {
		this.position = pos;
	}

	public double getVitesse() {
		return this.vitesse;
	}

	public abstract double getVitesse(NatureTerrain terrain);

	public void deverserEau(int vol) {
		if(vol > this.reservoir) this.reservoir = 0;
		else this.reservoir -= vol;
	}

	public abstract void remplirReservoir();
	
}
