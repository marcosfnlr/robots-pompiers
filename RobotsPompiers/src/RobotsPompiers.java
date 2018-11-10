import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class RobotsPompiers {

	private Carte carte;
	private Incendie[] incendies;
	private Robot[] robots;

	public static void main(String[] args) throws FileNotFoundException, DataFormatException {
		RobotsPompiers robotsPompiers = new RobotsPompiers();
		String file = "C:\\Users\\marco\\Desktop\\test.txt";
		LecteurDonnees.lire(file, robotsPompiers);
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public Incendie[] getIncendies() {
		return incendies;
	}

	public void setIncendies(Incendie[] incendies) {
		this.incendies = incendies;
	}

	public Robot[] getRobots() {
		return robots;
	}

	public void setRobots(Robot[] robots) {
		this.robots = robots;
	}

}
