package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

public class Element {
    private Coordonnees coordonnees;
    private Couleur couleur;

    public Element(int abscisse, int ordonnee) {
        this.coordonnees = new Coordonnees(abscisse, ordonnee);
        this.couleur = Couleur.values()[0];
    }

    public Element(int abscisse, int ordonnee, Couleur couleur) {
        this.coordonnees = new Coordonnees(abscisse, ordonnee);
        this.couleur = couleur;
    }

    public Element(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
        this.couleur = Couleur.values()[0];
    }

    public Element(Coordonnees coordonnees, Couleur couleur) {
        this.coordonnees = coordonnees;
        this.couleur = couleur;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Objects.equals(coordonnees, element.coordonnees) && couleur == element.couleur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordonnees, couleur);
    }

    @Override
    public String toString() {
        return coordonnees.toString() + " - " + couleur;
    }
}
