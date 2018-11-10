import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Iterator;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public class Simulateur implements Simulable {

	private GUISimulator gui;
	private Color invaderColor;
	private DonneesSimulation dados;
	public static final int PIXELS_PAR_CASE = 20;
	private int x;
	private int y;
	private Iterator<Integer> xIterator;
	private Iterator<Integer> yIterator;
	private long dateSimulation;
	private Evenement[] evenements;

	public Simulateur(GUISimulator gui, DonneesSimulation dados) throws Exception {
		this.gui = gui;
		gui.setSimulable(this); // association a la gui!
		this.dados = dados;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < gui.getPanelHeight() || screenSize.getWidth() < gui.getPanelWidth()) {
			throw new Exception("Taille de carte pas valide."); // TODO change type
		}
		planCoordinates();
		draw();
	}

	private void planCoordinates() {

	}

	@Override
	public void next() {
		draw();
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
					r.getPosition().getLigne() * PIXELS_PAR_CASE + PIXELS_PAR_CASE / 2, Color.WHITE, Color.GRAY,
					PIXELS_PAR_CASE / 4));
		}
	}

	public void ajouteEvenement(Evenement e) {

	}

	public void incrementeDate() {
		this.dateSimulation++;
	}

	public boolean simulationTerminee() {
		return false;
	}
}
