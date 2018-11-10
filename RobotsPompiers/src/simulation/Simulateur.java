package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import simulation.carte.Couleur;
import simulation.carte.Incendie;
import simulation.evenement.Evenement;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Simulateur implements Simulable {

	private GUISimulator gui;
	private DonneesSimulation dados;
	public static final int PIXELS_PAR_CASE = 20;
	private int x;
	private int y;
	private Iterator<Integer> xIterator;
	private Iterator<Integer> yIterator;
	private long dateSimulation;
	private List<Evenement> evenements;

	public Simulateur(GUISimulator gui, DonneesSimulation dados) throws Exception {
		this.evenements = new ArrayList<Evenement>();
		this.dateSimulation = 0;
		this.dados = dados;
		this.gui = gui;
		gui.setSimulable(this);

		for (Robot r : dados.getRobots()) {
			r.setSimulateur(this);
		}
		for (Robot r : dados.getRobots()) {
			r.setSimulateur(this);
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < gui.getPanelHeight() || screenSize.getWidth() < gui.getPanelWidth()) {
			throw new Exception("Taille de carte pas valide."); // TODO change type
		}
		draw();
	}

	public long getDateSimulation() {
		return this.dateSimulation;
	}

	public DonneesSimulation getDados() {
		return this.dados;
	}

	private void executerEvenements() {
		for (Evenement e : evenements) {
			if (e.isHappening(dateSimulation)) {
				e.execute();
			}
		}
	}

	@Override
	public void next() {
		incrementeDate();
		if (!simulationTerminee()) {
			executerEvenements();
			draw();
		}
	}

	@Override
	public void restart() {
		draw();
	}

	private void draw() {
		gui.reset();
		for (int lig = 0; lig < dados.getCarte().getNbLignes(); lig++) {
			for (int col = 0; col < dados.getCarte().getNbColonnes(); col++) {
				gui.addGraphicalElement(new Rectangle(PIXELS_PAR_CASE / 2 + col * PIXELS_PAR_CASE,
						PIXELS_PAR_CASE / 2 + lig * PIXELS_PAR_CASE, Color.BLACK,
						dados.getCarte().getCase(lig, col).getNature().getCouleur().getColor(), PIXELS_PAR_CASE));
			}
		}
		for (Incendie i : dados.getIncendies()) {
			gui.addGraphicalElement(new Rectangle(i.getPosition().getColonne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2,
					i.getPosition().getLigne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2, Color.WHITE,
					Couleur.RED.getColor(), PIXELS_PAR_CASE / 2));
		}
		for (Robot r : dados.getRobots()) {
			gui.addGraphicalElement(new Rectangle(r.getPosition().getColonne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2,
					r.getPosition().getLigne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2,
					r.getEtat() == EtatRobot.ARRETE ? Color.WHITE : Color.BLACK, Color.GRAY, PIXELS_PAR_CASE / 4));
		}
	}

	public void ajouteEvenement(Evenement e) {
		evenements.add(e);
	}

	public void incrementeDate() {
		this.dateSimulation++;
	}

	public boolean simulationTerminee() {
		for (Evenement e : evenements) {
			if (e.isHappening(dateSimulation)) {
				return false;
			}
		}
		return true;
	}
}
