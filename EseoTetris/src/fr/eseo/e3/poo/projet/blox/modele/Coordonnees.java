package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

public class Coordonnees {
    private int abscisse;
    private int ordonnee;

    /**
     * @param abscisse l'abscisse
     * @param ordonnee l'ordonnée
     */
    public Coordonnees(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    public int getAbscisse() {
        return abscisse;
    }

    /**
     * @param abscisse la nouvelle abscisse
     */
    public void setAbscisse(int abscisse) {
        this.abscisse = abscisse;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    /**
     * @param ordonnee la nouvelle ordonnée
     */
    public void setOrdonnee(int ordonnee) {
        this.ordonnee = ordonnee;
    }

    /**
     * @param o l'objet à comparer
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordonnees that = (Coordonnees) o;
        return abscisse == that.abscisse && ordonnee == that.ordonnee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(abscisse, ordonnee);
    }

    @Override
    public String toString() {
        return "("  + abscisse + ", " + ordonnee + ")";
    }
}
