package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.Coordonnees;
import fr.eseo.e3.poo.projet.blox.modele.Couleur;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

public abstract class Tetromino implements Piece {
    private Element[] elements;
    private Puits puits;

    public Tetromino(Coordonnees coordonnees, Couleur couleur) {
        this.elements = new Element[4];
        this.setElements(coordonnees, couleur);
    }

    @Override
    public Element[] getElements() {
        return elements;
    }

    protected abstract void setElements(Coordonnees coordonnees, Couleur couleur);

    @Override
    public void setPosition(int abscisse, int ordonnee) {
        this.setElements(new Coordonnees(abscisse, ordonnee), this.elements[0].getCouleur());
    }

    @Override
    public Puits getPuits() {
        return puits;
    }

    @Override
    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    @Override
    public void deplacerDe(int deltaX, int deltaY) throws IllegalArgumentException {
        if (deltaY < 0) {
            throw new IllegalArgumentException("Déplacement vers le haut interdit.");
        }
        if (Math.abs(deltaX) > 1 || deltaY > 1) {
            throw new IllegalArgumentException("Déplacement trop important (> 1).");
        }
        if (Math.abs(deltaX) == 1 && deltaY == 1) {
            throw new IllegalArgumentException("Déplacement diagonal interdit.");
        }

        for (Element element : this.elements) {
            element.deplacerDe(deltaX, deltaY);
        }
    }

    @Override
    public void tourner(boolean sensHoraire) {
        Element refElement = this.elements[0];
        int xRef = refElement.getCoordonnees().getAbscisse();
        int yRef = refElement.getCoordonnees().getOrdonnee();

        for (int i = 1; i < this.elements.length; i++) {
            int xRel = this.elements[i].getCoordonnees().getAbscisse() - xRef;
            int yRel = this.elements[i].getCoordonnees().getOrdonnee() - yRef;
            int newX, newY;

            if (sensHoraire) {
                newX = -yRel;
                newY = xRel;
            } else {
                newX = yRel;
                newY = -xRel;
            }

            this.elements[i].getCoordonnees().setAbscisse(xRef + newX);
            this.elements[i].getCoordonnees().setOrdonnee(yRef + newY);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(" :\n");
        for (Element element : this.elements) {
            sb.append("\t").append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
