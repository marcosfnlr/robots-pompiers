package simulation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import simulation.evenement.Deplacer;
import simulation.evenement.Intervenir;

public class RobotsPompiers {

	public static void main(String[] args) throws FileNotFoundException, DataFormatException {
		if (args.length < 1) {
			System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
			System.exit(1);
		}

		try {

			DonneesSimulation donneesSimulation = LecteurDonnees.lire(args[0]);
			GUISimulator gui = new GUISimulator(
					donneesSimulation.getCarte().getNbColonnes() * Simulateur.PIXELS_PAR_CASE,
					donneesSimulation.getCarte().getNbLignes() * Simulateur.PIXELS_PAR_CASE, Color.WHITE);
			Simulateur simulateur = new Simulateur(gui, donneesSimulation);
			simulateur.ajouteEvenement(
					new Deplacer(1, donneesSimulation.getRobots()[1], donneesSimulation.getCarte().getCase(5, 5)));
			simulateur.ajouteEvenement(
					new Deplacer(127, donneesSimulation.getRobots()[1], donneesSimulation.getCarte().getCase(5, 4)));
			simulateur.ajouteEvenement(
					new Deplacer(253, donneesSimulation.getRobots()[1], donneesSimulation.getCarte().getCase(6, 4)));
			simulateur.ajouteEvenement(new Deplacer(253 + 126, donneesSimulation.getRobots()[1],
					donneesSimulation.getCarte().getCase(6, 5)));
			simulateur.ajouteEvenement(new Intervenir(253 + 252, donneesSimulation.getRobots()[1],
					donneesSimulation.getIncendies().get(4)));
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
