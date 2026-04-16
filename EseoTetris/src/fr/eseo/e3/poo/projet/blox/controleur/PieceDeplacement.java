package fr.eseo.e3.poo.projet.blox.controleur;

import fr.eseo.e3.poo.projet.blox.modele.BloxException;
import fr.eseo.e3.poo.projet.blox.modele.Puits;
import fr.eseo.e3.poo.projet.blox.vue.VuePuits;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class PieceDeplacement extends MouseAdapter {

    private VuePuits vuePuits;
    private Puits puits;
    private int derniereColonne;

    public PieceDeplacement(VuePuits vuePuits) {
        this.setVuePuits(vuePuits);
        this.setPuits(vuePuits.getPuits());
        this.derniereColonne = -1;
    }

    public void setVuePuits(VuePuits vuePuits) {
        this.vuePuits = vuePuits;
    }

    public void setPuits(Puits puits) {
        this.puits = puits;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.puits != null && this.puits.getPieceActuelle() != null) {
            int colonneActuelle = e.getX() / this.vuePuits.getTaille();
            if (this.derniereColonne == -1) {
                this.derniereColonne = colonneActuelle;
            } else if (colonneActuelle != this.derniereColonne) {
                int deltaX = colonneActuelle - this.derniereColonne;
                try {
                    this.puits.getPieceActuelle().deplacerDe(deltaX, 0);
                    this.vuePuits.repaint();
                } catch (IllegalArgumentException | BloxException ex) {
                    // Déplacement invalide ou hors limites
                }
                this.derniereColonne = colonneActuelle;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.derniereColonne = e.getX() / this.vuePuits.getTaille();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (this.puits != null && this.puits.getPieceActuelle() != null) {
            if (e.getWheelRotation() > 0) {
                try {
                    this.puits.getPieceActuelle().deplacerDe(0, 1);
                    this.vuePuits.repaint();
                } catch (IllegalArgumentException | BloxException ex) {
                    // Déplacement invalide (ex: bas du puits)
                }
            }
        }
    }
}
