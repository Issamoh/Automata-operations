package sample;

import java.util.Objects;

public class Lettre {
    public Lettre(char lettre) {
        this.lettre = lettre;
    }

    public char getLettre() {
        return lettre;
    }

    public void setLettre(char lettre) {
        this.lettre = lettre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lettre lettre1 = (Lettre) o;
        return Objects.equals(lettre, lettre1.lettre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lettre);
    }

    private char lettre;

}
