package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class PieceDeplacement extends MouseAdapter {

    private final VuePuits vuePuits;
    private int derniereColonne;

    public PieceDeplacement(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
        this.derniereColonne = -1; // Initialiser à une valeur sentinelle
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        Puits puits = vuePuits.getPuits();
        if (puits == null || puits.getPieceActuelle() == null) {
            return;
        }

        int colonneActuelle = event.getX() / vuePuits.getTaille();

        if (derniereColonne != -1 && colonneActuelle != derniereColonne) {
            int direction = Integer.signum(colonneActuelle - derniereColonne);
            try {
                puits.getPieceActuelle().deplacerDe(direction, 0);
                vuePuits.repaint();
            } catch (BloxException e) {
                // La pièce ne peut pas se déplacer (sortie du puits ou collision)
            }
        }
        this.derniereColonne = colonneActuelle;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        Puits puits = vuePuits.getPuits();
        if (puits == null || puits.getPieceActuelle() == null) {
            return;
        }

        if (event.getWheelRotation() > 0) {
            puits.gravite();
            vuePuits.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        this.derniereColonne = -1;
    }
}
