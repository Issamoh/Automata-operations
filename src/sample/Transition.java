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

    public Etat getEtatSrc() {
        return etatSrc;
    }

    public String getLettreTr() {
        return lettreTr;
    }

    private String lettreTr ;
    private Etat etatDest ;
}
