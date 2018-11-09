public class Carte {

    private Case[] cases;
    private int nbLignes;
    private int nbColonnes;
    private int tailleCases;

    public Carte(int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public int getTailleCases() {
        return tailleCases;
    }

    public Case getVoisin(Case src, Direction dir){
        return null;
    }

    public Case getCase(int lin, int col){
        return null;
    }

    public boolean voisinExiste(Case src, Direction dir){
        return false;
    }
}
