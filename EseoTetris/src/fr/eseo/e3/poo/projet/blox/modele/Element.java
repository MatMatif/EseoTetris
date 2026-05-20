package fr.eseo.e3.poo.projet.blox.modele;

import java.util.Objects;

public class Element {
    private Coordonnees coordonnees;
    private Couleur couleur;

    /**
     * @param abscisse l'abscisse
     * @param ordonnee l'ordonnée
     */
    public Element(int abscisse, int ordonnee) {
        this.coordonnees = new Coordonnees(abscisse, ordonnee);
        this.couleur = Couleur.values()[0];
    }

    /**
     * @param abscisse l'abscisse
     * @param ordonnee l'ordonnée
     * @param couleur la couleur
     */
    public Element(int abscisse, int ordonnee, Couleur couleur) {
        this.coordonnees = new Coordonnees(abscisse, ordonnee);
        this.couleur = couleur;
    }

    /**
     * @param coordonnees les coordonnées
     */
    public Element(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
        this.couleur = Couleur.values()[0];
    }

    /**
     * @param coordonnees les coordonnées
     * @param couleur la couleur
     */
    public Element(Coordonnees coordonnees, Couleur couleur) {
        this.coordonnees = coordonnees;
        this.couleur = couleur;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    /**
     * @param coordonnees les nouvelles coordonnées
     */
    public void setCoordonnnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * @param couleur la nouvelle couleur
     */
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * @param o l'objet à comparer
     */
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

    /**
     * @param deltaX déplacement horizontal
     * @param deltaY déplacement vertical
     */
    public void deplacerDe(int deltaX, int deltaY) {
        this.coordonnees.setAbscisse(this.coordonnees.getAbscisse() + deltaX);
        this.coordonnees.setOrdonnee(this.coordonnees.getOrdonnee() + deltaY);
    }
}
