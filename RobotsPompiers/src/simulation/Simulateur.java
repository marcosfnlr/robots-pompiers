package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;

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
	public static final int PIXELS_PAR_CASE = 50;
	private List<Incendie> incendies;
	private List<Robot> robots;
	private long dateSimulation;
	private List<Evenement> evenements;
	private ChefPompier chefPompier;
	private int maxSizeIncendie;
	private String file;

	public Simulateur(GUISimulator gui, DonneesSimulation dados, String file) throws RobotsPompiersException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < gui.getPanelHeight() || screenSize.getWidth() < gui.getPanelWidth()) {
			throw new RobotsPompiersException("Taille de carte pas valide."); // TODO change type
		}
		this.gui = gui;
		this.dados = dados;
		this.file = file;
		this.chefPompier = new ChefPompier(this);
		gui.setSimulable(this);
		setDados();
	}

	private void setDados() {
		this.dateSimulation = 0;
		this.maxSizeIncendie = 0;
		this.evenements = new ArrayList<Evenement>();
		robots = new ArrayList<Robot>(Arrays.asList(dados.getRobots()));
		incendies = new ArrayList<Incendie>(Arrays.asList(dados.getIncendies()));
	}

	public void start() {
		for (Robot r : robots) {
			r.setSimulateur(this);
			chefPompier.assignerIncendie(r);
		}
		for (Incendie i : incendies) {
			if (i.getLitres() > maxSizeIncendie) {
				maxSizeIncendie = i.getLitres();
			}
		}
		draw();
	}

	public ChefPompier getChefPompier() {
		return chefPompier;
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
			for (Robot r : robots) {
				r.traiterAction();
			}
			executerEvenements();
			checkRobotsAuRepos();
			draw();
		}
	}

	private void checkRobotsAuRepos() {
		for (Robot r : robots) {
			if (r.getEtat() == EtatRobot.ARRETE && !evenementSuivant(r)) {
				if (r.getReservoir() == 0) {
					chefPompier.assignerRemplissage(r);
				} else {
					chefPompier.assignerIncendie(r);
				}
			}
		}

	}

	private boolean evenementSuivant(Robot r) {
		for (Evenement e : evenements) {
			if (e.getRobot().equals(r) && e.getDateDebut() == dateSimulation + 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void restart() {
		try {
			dados = LecteurDonnees.lire(file);
			setDados();
			start();
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + file + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + file + " invalide: " + e.getMessage());
		}
	}

	private void draw() {
		gui.reset();
		for (int lig = 0; lig < getCarte().getNbLignes(); lig++) {
			for (int col = 0; col < getCarte().getNbColonnes(); col++) {
				gui.addGraphicalElement(new Rectangle(PIXELS_PAR_CASE / 2 + col * PIXELS_PAR_CASE,
						PIXELS_PAR_CASE / 2 + lig * PIXELS_PAR_CASE, Color.BLACK,
						dados.getCarte().getCase(lig, col).getNature().getCouleur().getColor(), PIXELS_PAR_CASE));
			}
		}
		for (Incendie i : incendies) {
			int size = (int) ((PIXELS_PAR_CASE * 0.8) * (1.0 * i.getLitres() / maxSizeIncendie));
			gui.addGraphicalElement(new Rectangle(i.getPosition().getColonne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2,
					i.getPosition().getLigne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2, Color.ORANGE,
					Couleur.RED.getColor(), size > 0 ? size : 1));
		}
		for (Robot r : robots) {
			gui.addGraphicalElement(new Rectangle(r.getX(), r.getY(),
					r.getEtat() == EtatRobot.ARRETE ? Color.WHITE : Color.BLACK, Color.GRAY, PIXELS_PAR_CASE / 5));
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
