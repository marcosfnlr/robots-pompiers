package simulation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import simulation.evenement.Deplacer;

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
					new Deplacer(1L, donneesSimulation.getRobots()[1], donneesSimulation.getCarte().getCase(5, 5)));
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
