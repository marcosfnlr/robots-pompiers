package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import simulation.carte.Carte;
import simulation.carte.Couleur;
import simulation.carte.Incendie;
import simulation.evenement.Evenement;
import simulation.robot.EtatRobot;
import simulation.robot.Robot;

public class Simulateur implements Simulable {

	private GUISimulator gui;
	private DonneesSimulation dados;
	public static final int PIXELS_PAR_CASE = 40;
	private List<Incendie> incendies;
	private List<Robot> robots;
	private long dateSimulation;
	private List<Evenement> evenements;

	public Simulateur(GUISimulator gui, DonneesSimulation dados) throws RobotsPompiersException {

		this.dateSimulation = 0;
		this.dados = dados;
		this.gui = gui;
		gui.setSimulable(this);
		for (Robot r : dados.getRobots()) {
			r.setSimulateur(this);
		}
		setDados();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < gui.getPanelHeight() || screenSize.getWidth() < gui.getPanelWidth()) {
			throw new RobotsPompiersException("Taille de carte pas valide."); // TODO change type
		}
		draw();
	}

	private void setDados() {
		this.evenements = new ArrayList<Evenement>();
		robots = new ArrayList<Robot>(Arrays.asList(dados.getRobots()));
		incendies = new ArrayList<Incendie>(Arrays.asList(dados.getIncendies()));
	}

	public Carte getCarte() {
		return dados.getCarte();
	}

	public List<Incendie> getIncendies() {
		return incendies;
	}

	public List<Robot> getRobots() {
		return robots;
	}

	public long getDateSimulation() {
		return this.dateSimulation;
	}

	private void executerEvenements() {
		for (Evenement e : evenements) {
			if (e.getDateDebut() == dateSimulation) {
				try {
					e.execute();
				} catch (RobotsPompiersException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private void checkIncendies() {
		if (!incendies.isEmpty()) {
			ArrayList<Integer> inds = new ArrayList<Integer>();
			for (int i = 0; i < incendies.size(); i++) {
				if (incendies.get(i).getLitres() == 0) {
					inds.add(i);
				}
			}
			for (int i = inds.size() - 1; i >= 0; i--) {
				incendies.remove(inds.get(i).intValue());
			}
		}
	}

	@Override
	public void next() {
		checkIncendies();
		if (!simulationTerminee()) {
			incrementeDate();
			for (Robot r : dados.getRobots()) {
				r.traiterAction();
			}
			executerEvenements();
			draw();
		}
	}

	@Override
	public void restart() {
		setDados();
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
		for (Incendie i : incendies) {
			gui.addGraphicalElement(new Rectangle(i.getPosition().getColonne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2,
					i.getPosition().getLigne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2, Color.WHITE,
					Couleur.RED.getColor(), PIXELS_PAR_CASE / 2));
		}
		for (Robot r : robots) {
			gui.addGraphicalElement(new Rectangle(r.getX(), r.getY(),
					r.getEtat() == EtatRobot.ARRETE ? Color.WHITE : Color.BLACK, Color.GRAY, PIXELS_PAR_CASE / 4));
		}
	}

	public void removeIncendie(Incendie incendie) {
		incendies.remove(incendie);
	}

	public void ajouteEvenement(Evenement e) {
		evenements.add(e);
	}

	public void incrementeDate() {
		this.dateSimulation++;
	}

	public boolean simulationTerminee() {
		return incendies.isEmpty();
	}
}
