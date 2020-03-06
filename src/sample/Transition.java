package sample;

import java.util.Objects;

public class Transition {
    public Etat getEtatDest() {
        return etatDest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return Objects.equals(etatSrc, that.etatSrc) &&
                Objects.equals(lettreTr, that.lettreTr) &&
                Objects.equals(etatDest, that.etatDest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lettreTr);
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
