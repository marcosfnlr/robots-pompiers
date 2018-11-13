package tests;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import simulation.DonneesSimulation;
import simulation.LecteurDonnees;
import simulation.PathCalculator;
import simulation.carte.Carte;
import simulation.carte.Incendie;
import simulation.robot.Robot;

public class ShortestPathTester {
	public static void main(String[] args) throws FileNotFoundException, DataFormatException {
		if (args.length < 1) {
			System.out.println("Syntaxe: java ShortestPathTester <nomDeFichier>");
			System.exit(1);
		}
		try {
			DonneesSimulation donneesSimulation = LecteurDonnees.lire(args[0]);
			Carte carte = donneesSimulation.getCarte();
			Incendie[] incendie = donneesSimulation.getIncendies();
			Robot[] robot = donneesSimulation.getRobots();

			System.out.println(PathCalculator.calculate(carte, robot[0], incendie[0].getPosition()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}