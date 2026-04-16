package fr.eseo.e3.poo.projet.blox.modele.pieces.tetrominos;

import fr.eseo.e3.poo.projet.blox.modele.*;
import fr.eseo.e3.poo.projet.blox.modele.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public abstract class Tetromino implements Piece {
    private List<Element> elements;
    private Puits puits;

    public Tetromino(Coordonnees coordonnees, Couleur couleur) {
        this.elements = new ArrayList<>();
        this.setElements(coordonnees, couleur);
    }

    @Override
    public List<Element> getElements() {
        return elements;
    }

    protected abstract void setElements(Coordonnees coordonnees, Couleur couleur);

    @Override
    public void setPosition(int abscisse, int ordonnee) {
        this.setElements(new Coordonnees(abscisse, ordonnee), this.elements.get(0).getCouleur());
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
    public void deplacerDe(int deltaX, int deltaY) throws IllegalArgumentException, BloxException {
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
            int newX = element.getCoordonnees().getAbscisse() + deltaX;
            int newY = element.getCoordonnees().getOrdonnee() + deltaY;

            if (this.puits != null) {
                if (newX < 0 || newX >= this.puits.getLargeur()) {
                    throw new BloxException("Sortie de puits horizontale", BloxException.BLOX_SORTIE_PUITS);
                }
                if (newY >= this.puits.getProfondeur()) {
                    throw new BloxException("Collision avec le fond", BloxException.BLOX_COLLISION);
                }
                if (this.puits.getTas().elementExists(newX, newY)) {
                    throw new BloxException("Collision avec le tas", BloxException.BLOX_COLLISION);
                }
            }
        }

        for (Element element : this.elements) {
            element.deplacerDe(deltaX, deltaY);
        }
    }

    @Override
    public void tourner(boolean sensHoraire) throws BloxException {
        Element refElement = this.elements.get(0);
        int xRef = refElement.getCoordonnees().getAbscisse();
        int yRef = refElement.getCoordonnees().getOrdonnee();

        int[] nextX = new int[this.elements.size()];
        int[] nextY = new int[this.elements.size()];

        for (int i = 0; i < this.elements.size(); i++) {
            if (i == 0) {
                nextX[i] = xRef;
                nextY[i] = yRef;
            } else {
                int xRel = this.elements.get(i).getCoordonnees().getAbscisse() - xRef;
                int yRel = this.elements.get(i).getCoordonnees().getOrdonnee() - yRef;
                int rotatedX, rotatedY;

                if (sensHoraire) {
                    rotatedX = -yRel;
                    rotatedY = xRel;
                } else {
                    rotatedX = yRel;
                    rotatedY = -xRel;
                }
                nextX[i] = xRef + rotatedX;
                nextY[i] = yRef + rotatedY;
            }

            if (this.puits != null) {
                if (nextX[i] < 0 || nextX[i] >= this.puits.getLargeur()) {
                    throw new BloxException("Rotation sort du puits", BloxException.BLOX_SORTIE_PUITS);
                }
                if (nextY[i] >= this.puits.getProfondeur()) {
                    throw new BloxException("Rotation collision fond", BloxException.BLOX_COLLISION);
                }
                if (this.puits.getTas().elementExists(nextX[i], nextY[i])) {
                    throw new BloxException("Rotation collision tas", BloxException.BLOX_COLLISION);
                }
            }
        }

        for (int i = 1; i < this.elements.size(); i++) {
            this.elements.get(i).getCoordonnees().setAbscisse(nextX[i]);
            this.elements.get(i).getCoordonnees().setOrdonnee(nextY[i]);
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
