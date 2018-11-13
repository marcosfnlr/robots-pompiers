package simulation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import simulation.evenement.Deplacer;
import simulation.evenement.Intervenir;
import simulation.evenement.Remplir;

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
					new Deplacer(1, simulateur.getRobots().get(1), simulateur.getCarte().getCase(5, 5)));
			simulateur.ajouteEvenement(
					new Deplacer(127, simulateur.getRobots().get(1), simulateur.getCarte().getCase(5, 4)));
			simulateur.ajouteEvenement(
					new Deplacer(253, simulateur.getRobots().get(1), simulateur.getCarte().getCase(6, 4)));
			simulateur.ajouteEvenement(new Deplacer(253 + 126, simulateur.getRobots().get(1),
					simulateur.getCarte().getCase(6, 5)));
			simulateur.ajouteEvenement(
					new Intervenir(505, simulateur.getRobots().get(1), simulateur.getIncendies().get(4)));
			simulateur.ajouteEvenement(
					new Deplacer(756, simulateur.getRobots().get(1), simulateur.getCarte().getCase(6, 4)));
			simulateur.ajouteEvenement(new Remplir(756 + 126, simulateur.getRobots().get(1)));
			simulateur.ajouteEvenement(
					new Deplacer(1383, simulateur.getRobots().get(1), simulateur.getCarte().getCase(6, 5)));
			simulateur.ajouteEvenement(
					new Intervenir(1509, simulateur.getRobots().get(1), simulateur.getIncendies().get(4)));

		} catch (FileNotFoundException e) {
			System.out.println("fichier " + args[0] + " inconnu ou illisible");
		} catch (DataFormatException e) {
			System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
		} catch (RobotsPompiersException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
