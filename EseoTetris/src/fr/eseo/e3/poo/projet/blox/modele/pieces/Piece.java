package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;

import java.util.List;

public interface Piece {
    public List<Element> getElements();

    /**
     * @param abscisse l'abscisse de la position
     * @param ordonnee l'ordonnée de la position
     */
    public void setPosition(int abscisse, int ordonnee);

    public Puits getPuits();

    /**
     * @param puits le puits à associer
     */
    public void setPuits(Puits puits);

    /**
     * @param deltaX le déplacement en abscisse
     * @param deltaY le déplacement en ordonnée
     */
    public void deplacerDe(int deltaX, int deltaY) throws IllegalArgumentException, BloxException;

    /**
     * @param sensHoraire le sens de rotation
     */
    public void tourner(boolean sensHoraire) throws BloxException;
}
