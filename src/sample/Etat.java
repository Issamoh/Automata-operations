package sample;

import java.util.Objects;

public class Etat implements Comparable<Etat> {


    public Etat(String name) {
        this.name = name;
        coaccessible = false ;
        accessible =false ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getAccessible() {
        return accessible;
    }

    public void setAccessible(Boolean accessible) {
        this.accessible = accessible;
    }

    public Boolean getCoaccessible() {
        return coaccessible;
    }

    public void setCoaccessible(Boolean coaccessible) {
        this.coaccessible = coaccessible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etat etat = (Etat) o;
        return name.equals(etat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private String name;

    public boolean EstInitial() {
        return estInitial;
    }

    public void setEstInitial(boolean estInitial) {
        this.estInitial = estInitial;
    }

    public boolean EstFinal() {
        return estFinal;
    }

    public void setEstFinal(boolean estFinal) {
        this.estFinal = estFinal;
    }

    private boolean estInitial ;
   private boolean estFinal ;
    private Boolean accessible ;
    private Boolean coaccessible ;

    @Override
    public int compareTo(Etat o) {
        return this.getName().compareTo(o.getName()) ;
    }
}
