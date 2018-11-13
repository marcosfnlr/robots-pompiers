package simulation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;

public class RobotsPompiers {

	public static void main(String[] args) throws FileNotFoundException, DataFormatException {
		if (args.length < 1) {
			System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
			System.exit(1);
		}

		try {
			String file = args[0];
			DonneesSimulation donneesSimulation = LecteurDonnees.lire(file);
			GUISimulator gui = new GUISimulator(
					donneesSimulation.getCarte().getNbColonnes() * Simulateur.PIXELS_PAR_CASE,
					donneesSimulation.getCarte().getNbLignes() * Simulateur.PIXELS_PAR_CASE, Color.WHITE);
			Simulateur simulateur = new Simulateur(gui, donneesSimulation, file);
			simulateur.start();

		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		} catch (RobotsPompiersException e) {
			System.out.println(e.getMessage());
		}

	}

}
