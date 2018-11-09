public class Incendie {

    private Case position;
    private int litres;

    public Incendie(Case position, int litres) {
        this.position = position;
        this.litres = litres;
    }

    public Case getPosition() {
        return position;
    }

    public int getLitres() {
        return litres;
    }
}
