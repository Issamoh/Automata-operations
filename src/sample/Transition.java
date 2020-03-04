package sample;

public class Transition {
    public Etat getEtatDest() {
        return etatDest;
    }

    public Transition(Etat etatSrc, String lettreTr, Etat etatDest) {
        this.etatSrc = etatSrc;
        this.lettreTr = lettreTr;
        this.etatDest = etatDest;
    }

    private Etat etatSrc ;
    private String lettreTr ;
    private Etat etatDest ;
}
