package simulation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import simulation.carte.Carte;
import simulation.carte.Case;
import simulation.carte.Incendie;
import simulation.carte.NatureTerrain;
import simulation.robot.Chenilles;
import simulation.robot.Drone;
import simulation.robot.Pattes;
import simulation.robot.Robot;
import simulation.robot.Roues;
import simulation.robot.TypeRobot;

/**
 * Lecteur de cartes au format spectifié dans le sujet. Les données sur les
 * cases, robots puis incendies sont lues dans le fichier, puis simplement
 * affichées. A noter: pas de vérification sémantique sur les valeurs
 * numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher. A vous de modifier
 * ou d'ajouter des méthodes, inspirées de celles présentes (ou non), qui
 * CREENT les objets au moment adéquat pour construire une instance de la
 * classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues: public static DonneesSimulation
 * creeDonnees(String fichierDonnees); Et faire des méthode creeCase(),
 * creeRobot(), ... qui lisent les données, créent les objets adéquats et les
 * ajoutent ds l'instance de DonneesSimulation.
 */
public class LecteurDonnees {

	/**
	 * Lit et affiche le contenu d'un fichier de donnees (cases, robots et
	 * incendies). Ceci est méthode de classe; utilisation:
	 * LecteurDonnees.lire(fichierDonnees)
	 * 
	 * @param fichierDonnees
	 *            nom du fichier à lire
	 */
	public static DonneesSimulation lire(String fichierDonnees) throws FileNotFoundException, DataFormatException {
		LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
		Carte carte = lecteur.lireCarte();
		Incendie[] incendies = lecteur.lireIncendies(carte);
		Robot[] robots = lecteur.lireRobots(carte);
		scanner.close();
		return new DonneesSimulation(carte, incendies, robots);
	}

	// Tout le reste de la classe est prive!

	private static Scanner scanner;

	/**
	 * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
	 * 
	 * @param fichierDonnees
	 *            nom du fichier a lire
	 */
	private LecteurDonnees(String fichierDonnees) throws FileNotFoundException {
		scanner = new Scanner(new File(fichierDonnees));
		scanner.useLocale(Locale.US);
	}

	/**
	 * Lit et affiche les donnees de la carte.
	 * 
	 * @throws ExceptionFormatDonnees
	 */
	private Carte lireCarte() throws DataFormatException {
		ignorerCommentaires();
		Carte carte;
		try {
			int nbLignes = scanner.nextInt();
			int nbColonnes = scanner.nextInt();
			int tailleCases = scanner.nextInt(); // en m
			carte = new Carte(nbLignes, nbColonnes, tailleCases);

			for (int lig = 0; lig < nbLignes; lig++) {
				for (int col = 0; col < nbColonnes; col++) {
					carte.setCase(lireCase(lig, col));
				}
			}

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. " + "Attendu: nbLignes nbColonnes tailleCases");
		}
		return carte;
	}

	/**
	 * Lit et affiche les donnees d'une case.
	 */
	private Case lireCase(int lig, int col) throws DataFormatException {
		ignorerCommentaires();
		Case c;
		String chaineNature = new String();

		try {
			chaineNature = scanner.next();
			verifieLigneTerminee();
			c = new Case(lig, col, NatureTerrain.valueOf(chaineNature));

		} catch (NoSuchElementException e) {
			throw new DataFormatException("format de case invalide. " + "Attendu: nature altitude [valeur_specifique]");
		}
		return c;
	}

	/**
	 * Lit et affiche les donnees des incendies.
	 */
	private Incendie[] lireIncendies(Carte carte) throws DataFormatException {
		ignorerCommentaires();
		Incendie[] incendies;
		try {
			int nbIncendies = scanner.nextInt();
			incendies = new Incendie[nbIncendies];
			for (int i = 0; i < nbIncendies; i++) {
				incendies[i] = lireIncendie(i, carte);
			}

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. " + "Attendu: nbIncendies");
		}
		return incendies;
	}

	/**
	 * Lit et affiche les donnees du i-eme incendie.
	 * 
	 * @param i
	 */
	private Incendie lireIncendie(int i, Carte c) throws DataFormatException {
		ignorerCommentaires();
		Incendie incendie;
		try {
			int lig = scanner.nextInt();
			int col = scanner.nextInt();
			int intensite = scanner.nextInt();
			if (intensite <= 0) {
				throw new DataFormatException("incendie " + i + "nb litres pour eteindre doit etre > 0");
			}
			verifieLigneTerminee();
			incendie = new Incendie(c.getCase(lig, col), intensite);

		} catch (NoSuchElementException e) {
			throw new DataFormatException("format d'incendie invalide. " + "Attendu: ligne colonne intensite");
		}
		return incendie;
	}

	/**
	 * Lit et affiche les donnees des robots.
	 */
	private Robot[] lireRobots(Carte carte) throws DataFormatException {
		ignorerCommentaires();
		Robot[] robots;
		try {
			int nbRobots = scanner.nextInt();
			robots = new Robot[nbRobots];
			for (int i = 0; i < nbRobots; i++) {
				robots[i] = lireRobot(i, carte);
			}

		} catch (NoSuchElementException e) {
			throw new DataFormatException("Format invalide. " + "Attendu: nbRobots");
		}
		return robots;
	}

	/**
	 * Lit et affiche les donnees du i-eme robot.
	 * 
	 * @param i
	 */
	private Robot lireRobot(int i, Carte carte) throws DataFormatException {
		ignorerCommentaires();
		Robot robot;

		try {
			int lig = scanner.nextInt();
			int col = scanner.nextInt();
			String type = scanner.next();
			TypeRobot typeRobot = TypeRobot.valueOf(type);

			String s = scanner.findInLine("(\\d+)"); // double:(\\d+(\\.\\d+)?)
			Case c = carte.getCase(lig, col);
			if (s == null) {
				switch (typeRobot) {
				case DRONE:
					robot = new Drone(c);
					break;
				case CHENILLES:
					robot = new Chenilles(c);
					break;
				case PATTES:
					robot = new Pattes(c);
					break;
				case ROUES:
					robot = new Roues(c);
					break;
				default:
					throw new DataFormatException("Type robot invalide. ");
				}
			} else {
				int vitesse = Integer.parseInt(s);
				switch (typeRobot) {
				case DRONE:
					robot = new Drone(c, vitesse);
					break;
				case CHENILLES:
					robot = new Chenilles(c, vitesse);
					break;
				case PATTES:
					robot = new Pattes(c, vitesse);
					break;
				case ROUES:
					robot = new Roues(c, vitesse);
					break;
				default:
					throw new DataFormatException("Type robot invalide. ");
				}
			}
			verifieLigneTerminee();

		} catch (NoSuchElementException e) {
			throw new DataFormatException(
					"format de robot invalide. " + "Attendu: ligne colonne type [valeur_specifique]");
		}
		return robot;
	}

	/** Ignore toute (fin de) ligne commencant par '#' */
	private void ignorerCommentaires() {
		while (scanner.hasNext("#.*")) {
			scanner.nextLine();
		}
	}

	/**
	 * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
	 * 
	 * @throws ExceptionFormatDonnees
	 */
	private void verifieLigneTerminee() throws DataFormatException {
		if (scanner.findInLine("(\\d+)") != null) {
			throw new DataFormatException("format invalide, donnees en trop.");
		}
	}
}
