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
        if (puits.getPieceActuelle() == null) {
            return;
        }

        int colonneActuelle = event.getX() / vuePuits.getTaille();

        if (derniereColonne != -1 && colonneActuelle != derniereColonne) {
            int deltaX = colonneActuelle - derniereColonne;
            int direction = deltaX > 0 ? 1 : -1;
            int nbPas = Math.abs(deltaX);

            for (int i = 0; i < nbPas; i++) {
                try {
                    puits.getPieceActuelle().deplacerDe(direction, 0);
                } catch (BloxException | IllegalArgumentException e) {
                    break; // On arrête de déplacer si on rencontre un obstacle ou une limite
                }
            }
            vuePuits.repaint();
        }
        this.derniereColonne = colonneActuelle;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        Puits puits = vuePuits.getPuits();
        if (puits.getPieceActuelle() == null) {
            return;
        }

        if (event.getWheelRotation() > 0) {
            try {
                puits.getPieceActuelle().deplacerDe(0, 1);
                vuePuits.repaint();
            } catch (BloxException | IllegalArgumentException e) {
                if (e instanceof BloxException && ((BloxException)e).getType() == BloxException.BLOX_COLLISION) {
                    puits.gererCollision();
                    vuePuits.repaint();
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        this.derniereColonne = -1;
    }
}
