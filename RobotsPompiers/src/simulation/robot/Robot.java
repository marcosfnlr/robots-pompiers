package simulation.robot;
import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;
import simulation.evenement.Direction;

public abstract class Robot {
	
	private Case position;
	private double vitesse;
	private int reservoir;
	private int tempsRemplissage; // temps pour remplir tout le reservoir en secondes
	private double vitesseIntervention; // litres per secondes 
	
	public Robot (Case position, double vitesse) {
		this.position = position;
		this.vitesse = vitesse;
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
    	this.reservoir -= vol;
    }

    //public abstract void remplirReservoir();
    public void remplirReservoir(Carte carte) {
    	Case position = this.getPosition();
    	
    	Case voisin = carte.getVoisin(position, Direction.NORD);
    	if(voisin.getNature() == NatureTerrain.EAU) this.setReservoir(5000);
    	
    	voisin = carte.getVoisin(position, Direction.SUD);
    	if(voisin.getNature() == NatureTerrain.EAU) this.setReservoir(5000);
    	
    	voisin = carte.getVoisin(position, Direction.EST);
    	if(voisin.getNature() == NatureTerrain.EAU) this.setReservoir(5000);
    	
    	voisin = carte.getVoisin(position, Direction.OUEST);
    	if(voisin.getNature() == NatureTerrain.EAU) this.setReservoir(5000);
    	
    }
}
