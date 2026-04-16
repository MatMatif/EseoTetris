package fr.eseo.e3.poo.projet.blox.modele.pieces;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Element;
import fr.eseo.e3.poo.projet.blox.modele.Puits;

import java.util.List;

public interface Piece {
    public List<Element> getElements();
    public void setPosition(int abscisse, int ordonnee);
    public Puits getPuits();
    public void setPuits(Puits puits);
    public void deplacerDe(int deltaX, int deltaY) throws IllegalArgumentException, BloxException;
    public void tourner(boolean sensHoraire) throws BloxException;
}
