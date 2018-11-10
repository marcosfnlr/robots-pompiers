public class Carte {

	private Case[] cases;
	private int nbLignes;
	private int nbColonnes;
	private int tailleCases;

	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCases = tailleCases;
		this.cases = new Case[nbLignes * nbColonnes];
	}

	public int getNbLignes() {
		return nbLignes;
	}

	public int getNbColonnes() {
		return nbColonnes;
	}

<<<<<<< HEAD
    public int getTailleCases() {
        return tailleCases;
    }
    
    public Case getCase(int lin, int col){
        return cases[lin * this.getNbColonnes() + col];
    }
=======
	public int getTailleCases() {
		return tailleCases;
	}
>>>>>>> branch 'master' of https://github.com/marcosfnlr/robots-pompiers.git

	public Case getVoisin(Case src, Direction dir){
    	int lin = src.getLigne();
        int col = src.getColonne();
    	if(this.voisinExiste(src, dir)) {
    		switch (dir) {
        		case NORD :
        			return this.getCase(lin-1, col);
        		case SUD:
        			return this.getCase(lin+1, col);
        		case EST:
        			return this.getCase(lin, col+1);
        		case OUEST:
        			return this.getCase(lin, col-1);
        		default : return null;
    		}
    	}
    	else return null;
    }

<<<<<<< HEAD
    public boolean voisinExiste(Case src, Direction dir){
=======
	public Case getCase(int lin, int col) {
		return cases[lin * nbColonnes + col];
	}

	public void setCase(Case c) {
		cases[c.getLigne() * nbColonnes + c.getColonne()] = c;
	}

	public boolean voisinExiste(Case src, Direction dir){
>>>>>>> branch 'master' of https://github.com/marcosfnlr/robots-pompiers.git
        int ligne = src.getLigne();
        int colonne = src.getColonne();
        switch (dir) {
        	case NORD :
        		if(ligne == 0) return false;
        		return true;
        	case SUD:
        		if(ligne == this.getNbLignes() - 1) return false;
        		return true;
        	case EST:
        		if(colonne == this.getNbColonnes() - 1) return false;
        		return true;
        	case OUEST:
        		if(colonne == 0) return false;
        		return true;
        	default : return false;
        }
    }
}
